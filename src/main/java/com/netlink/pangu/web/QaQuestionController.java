/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netlink.pangu.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.pagehelper.Page;
import com.netlink.pangu.dto.request.qa.QuestionAddDTO;
import com.netlink.pangu.domain.QaCategory;
import com.netlink.pangu.domain.QaQuestion;
import com.netlink.pangu.domain.QaQuestionEvaluate;
import com.netlink.pangu.dto.request.qa.QuestionOpsDTO;
import com.netlink.pangu.dto.request.qa.QuestionPageDTO;
import com.netlink.pangu.dto.response.BaseResult;
import com.netlink.pangu.dto.response.PageResult;
import com.netlink.pangu.dto.response.qa.QaQuestionDTO;
import com.netlink.pangu.service.qa.QaCategoryService;
import com.netlink.pangu.service.qa.QaQuestionEvaluateService;
import com.netlink.pangu.service.qa.QaQuestionService;
import com.netlink.pangu.consts.RespCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * QaQuestionController
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Slf4j
@RestController
@RequestMapping("/qa/question")
public class QaQuestionController extends BaseController {

	/**
	 * 赞
	 */
	private static final byte THUMBS_UP = 1;

	/**
	 * 踩
	 */
	private static final byte THUMBS_DOWN = -1;

	/**
	 * 读
	 */
	private static final byte READ = 0;

	private QaQuestionService questionService;
	private QaCategoryService categoryService;
	private QaQuestionEvaluateService questionEvaluateService;

	@Autowired
	public QaQuestionController(QaQuestionService questionService,
                                QaCategoryService categoryService,
								QaQuestionEvaluateService questionEvaluateService){
		this.questionService = questionService;
		this.categoryService = categoryService;
		this.questionEvaluateService = questionEvaluateService;
	}

    /**
     * 保存问题
     * @param questionAddDTO questionAddDTO
     * @return BaseResult
     */
	@PostMapping("/save")
	public BaseResult save(@Valid @RequestBody QuestionAddDTO questionAddDTO) {

		QaCategory category = categoryService.findById(questionAddDTO.getCategoryId());
		QaQuestion questionDO = new QaQuestion();
		questionDO.setCategoryId(category.getId());
		questionDO.setCategoryName(category.getCategoryName());
		questionDO.setUserId(USER_NO);
		questionDO.setUserName(USER);
		questionDO.setTitle(questionAddDTO.getTitle());
		// 过滤掉emoji表情编码
		String questionEmoji = questionAddDTO.getQuestion()
				.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
		questionDO.setQuestion(questionEmoji);
		questionService.save(questionDO);

		return new BaseResult(RespCodeEnum.SUCCESS);
	}

	/**
	 * 问题顶踩浏览次数计数
	 * @param opsDTO opsDTO
	 * @return BaseResult
	 */
	@PostMapping("/sign")
	public BaseResult signQuestion(@Valid @RequestBody QuestionOpsDTO opsDTO) {
		if (opsDTO.getOps() == THUMBS_UP){
			QaQuestionEvaluate condition = new QaQuestionEvaluate();
			condition.setUserId(USER_NO);
			condition.setQuestionId(opsDTO.getQuestionId());
			condition.setEvaluate(THUMBS_UP);
			List<QaQuestionEvaluate> evaluateList = questionEvaluateService.findByCondition(condition);
			if (!evaluateList.isEmpty()){
				// 已经点过赞
				return new BaseResult(RespCodeEnum.ALREADY_THUMBSUP);
			}
			// 保存点赞记录
			saveQuestionEvaluate(opsDTO);
			questionService.increaseThumbUp(opsDTO.getQuestionId());
		}
		if (opsDTO.getOps() == THUMBS_DOWN){
			QaQuestionEvaluate condition = new QaQuestionEvaluate();
			condition.setUserId(USER_NO);
			condition.setQuestionId(opsDTO.getQuestionId());
			condition.setEvaluate(THUMBS_DOWN);
			List<QaQuestionEvaluate> evaluateList = questionEvaluateService.findByCondition(condition);
			if (!evaluateList.isEmpty()){
				// 已经点过踩
				return new BaseResult(RespCodeEnum.ALREADY_THUMBSDOWN);
			}
			// 保存点踩记录
			saveQuestionEvaluate(opsDTO);
			questionService.increaseThumbDown(opsDTO.getQuestionId());
		}
		if (opsDTO.getOps() == READ){
			questionService.increaseViews(opsDTO.getQuestionId());
		}
		return new BaseResult(RespCodeEnum.SUCCESS);
	}

	private void saveQuestionEvaluate(QuestionOpsDTO opsDTO){
		QaQuestionEvaluate evaluate = new QaQuestionEvaluate();
		evaluate.setUserId(USER_NO);
		evaluate.setUserName(USER);
		evaluate.setQuestionId(opsDTO.getQuestionId());
		evaluate.setEvaluate(opsDTO.getOps());
		questionEvaluateService.save(evaluate);
	}

	/**
	 * 分页查询问题
	 * @param pageDTO pageDTO
	 * @return PageResult<QaQuestionDTO>
	 */
	@GetMapping("/list")
	public PageResult<QaQuestionDTO> getQuestionList(@NotNull QuestionPageDTO pageDTO) {
		Page<QaQuestion> questionList = questionService.pageByCondition(pageDTO);
		List<QaQuestionDTO> questionDTOList = new ArrayList<>();
		for (QaQuestion question : questionList) {
			QaQuestionDTO questionDTO = new QaQuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTOList.add(questionDTO);
		}
		PageResult<QaQuestionDTO> pageResult = new PageResult<>(questionDTOList);
		pageResult.setPageNum(questionList.getPageNum());
		pageResult.setPageSize(questionList.getPageSize());
		pageResult.setTotal(questionList.getTotal());
		return pageResult;
	}

	/**
	 * 查询问题详细
	 * @param questionId questionId
	 * @return BaseResult<QaQuestionDTO>
	 */
	@GetMapping("/detail")
	public BaseResult<QaQuestionDTO> getQuestionDetail(@NotNull @RequestParam("questionId") Long questionId) {
		QaQuestion question = questionService.findById(questionId);
		QaQuestionDTO questionDTO = new QaQuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		return new BaseResult<>(questionDTO);
	}

// /**
//	 * 顶踩浏览次数计数
//	 *
//	 * @param request
//	 * @param opsDTO
//	 * @return
//	 */
//	@PostMapping("/sign")
//	public BaseResponse signQuestion(HttpServletRequest request, @Valid @RequestBody QuestionOpsDTO opsDTO) {
//		if (EventTypeEnum.THUMB_UP.getEventCode() == opsDTO.getEventType() || EventTypeEnum.THUMB_DOWN.getEventCode() == opsDTO.getEventType()) {
//			checkThumbUpOrThumbDown(request, opsDTO);
//		}else{
//			questionService.signQuestion(opsDTO.getQuestionId(), 0);
//		}
//		return new BaseResponse();
//	}
//
//	/**
//	 * 顶踩浏览次数计数
//	 * @param request
//	 * @param opsDTO
//	 */
//	private void checkThumbUpOrThumbDown(HttpServletRequest request, QuestionOpsDTO opsDTO){
//		SessionUser sessionUser= userService.getUser(request);
//		if (sessionUser == null){
//			throw new SystemException(RespCodeEnum.SYS_ERROR.getCode(), "login user cannot be null");
//		}
//		String userNo = sessionUser.getStaffNo();
//		String userName = sessionUser.getName();
//		QaQuestionEvaluateDO evaluate = questionEvaluateService.findByUserIdAndQuestionIdAndEvaluate(userNo, opsDTO.getQuestionId(), opsDTO.getEventType());
//		if (evaluate == null) {
//			evaluate = new QaQuestionEvaluateDO();
//			evaluate.setUserId(userNo);
//			evaluate.setUserName(userName);
//			evaluate.setQuestionId(opsDTO.getQuestionId());
//			evaluate.setEvaluate(opsDTO.getEventType());
//			questionEvaluateService.save(evaluate);
//			questionService.signQuestion(opsDTO.getQuestionId(), opsDTO.getEventType());
//		}else{
//			//questionService.signQuestion(opsDTO.getQuestionId(), 0);
//		}
//	}
//
//	@GetMapping("/list")
//	public BasePageResponse getQuestionList(@NotNull QuestionPageDTO pageDTO) {
//		Page<QaQuestionDO> questionList = questionService.pageQuestionByCondition(pageDTO);
//		QuestionPageResponse pageResponse = new QuestionPageResponse();
//		pageResponse.setPageNum(questionList.getPageNum());
//		pageResponse.setPageSize(questionList.getPageSize());
//		pageResponse.setTotal(questionList.getTotal());
//		List<QuestionDTO> questionDTOList = new ArrayList<>();
//		for (QaQuestionDO question : questionList) {
//			QuestionDTO questionDTO = mapperFacade.map(question, QuestionDTO.class);
//			questionDTOList.add(questionDTO);
//		}
//		pageResponse.setQuestionDTOList(questionDTOList);
//		return pageResponse;
//	}
//
//	@GetMapping("/{id}")
//	public BaseResponse getQuestionDetail(@NotNull @PathVariable("id") Long id) {
//		QaQuestionDO question = questionService.findById(id);
//		QuestionDTO questionDTO = mapperFacade.map(question, QuestionDTO.class);
//
//		return new QuestionResponse(questionDTO);
//	}

}
