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
package com.netlink.pangu.web.qa;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.netlink.pangu.request.qa.QuestionAddDTO;
import com.netlink.pangu.response.BasePageResponse;
import com.netlink.pangu.response.BaseResponse;
import com.netlink.pangu.entity.qa.QaCategoryDO;
import com.netlink.pangu.entity.qa.QaQuestionDO;
import com.netlink.pangu.entity.qa.QaQuestionEvaluateDO;
import com.netlink.pangu.request.qa.QuestionOpsDTO;
import com.netlink.pangu.response.dto.qa.QuestionPageResponse;
import com.netlink.pangu.response.dto.qa.QuestionDTO;
import com.netlink.pangu.service.qa.QaCategoryService;
import com.netlink.pangu.service.qa.QaQuestionEvaluateService;
import com.netlink.pangu.service.qa.QaQuestionService;
import com.netlink.pangu.util.qa.EventTypeEnum;
import com.netlink.pangu.request.qa.QuestionPageDTO;
import com.netlink.pangu.response.RespCodeEnum;
import com.netlink.pangu.exception.SystemException;
import com.netlink.pangu.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

/**
 * 问题控制器
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Slf4j
@RestController
@RequestMapping("/qa/question")
public class QaQuestionController extends BaseController {

	private QaQuestionService qaQuestionService;
	private QaCategoryService qaCategoryService;
	private QaQuestionEvaluateService qaQuestionEvaluateService;

	@Autowired
	public QaQuestionController(QaQuestionService qaQuestionService,
                                QaCategoryService qaCategoryService,
								QaQuestionEvaluateService qaQuestionEvaluateService){
		this.qaQuestionService = qaQuestionService;
		this.qaCategoryService = qaCategoryService;
		this.qaQuestionEvaluateService = qaQuestionEvaluateService;
	}

    /**
     * 保存问题
     * @param questionAddDTO 问题信息
     * @return BaseResponse
     */
	@PostMapping("/save")
	public BaseResponse save(@Valid @RequestBody QuestionAddDTO questionAddDTO) {

		QaCategoryDO category = qaCategoryService.findById(questionAddDTO.getCategoryId());
		QaQuestionDO questionDO = new QaQuestionDO();
		questionDO.setCategoryId(category.getId());
		questionDO.setCategoryName(category.getCategoryName());
		questionDO.setUserId(USER_NO);
		questionDO.setUserName(USER);
		questionDO.setTitle(questionAddDTO.getTitle());
		// 过滤掉emoji表情编码
		String questionEmoji = questionAddDTO.getQuestion()
				.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
		questionDO.setQuestion(questionEmoji);
		qaQuestionService.save(questionDO);

		return new BaseResponse();
	}

	/**
	 * 问题顶踩浏览次数计数
	 * @param opsDTO 操作信息
	 * @return BaseResponse
	 */
	@PostMapping("/sign")
	public BaseResponse signQuestion(@Valid @RequestBody QuestionOpsDTO opsDTO) {
		List<QaQuestionEvaluateDO> evaluateDOList = qaQuestionEvaluateService.findByUserIdAndQuestionId(USER_NO, opsDTO.getQuestionId());
		for (QaQuestionEvaluateDO evaluateDO : evaluateDOList){
			if (evaluateDO.getEvaluate() == EventTypeEnum.THUMB_UP.getEventCode()
					&& opsDTO.getEventType() == EventTypeEnum.THUMB_UP.getEventCode()){
				// 已经点过赞, 继续点赞
				return new BaseResponse(RespCodeEnum.DUPLICATE.getCode(), RespCodeEnum.DUPLICATE.getMessage(), null);
			}
			if (evaluateDO.getEvaluate() == EventTypeEnum.THUMB_DOWN.getEventCode()
					&& opsDTO.getEventType() == EventTypeEnum.THUMB_DOWN.getEventCode()){
				// 已经点过踩, 继续点踩
				return new BaseResponse(RespCodeEnum.DUPLICATE.getCode(), RespCodeEnum.DUPLICATE.getMessage(), null);
			}
		}

		qaQuestionService.signQuestion(USER_NO, opsDTO.getQuestionId(), opsDTO.getEventType());

		return new BaseResponse();
	}

	/**
	 * 顶踩浏览次数计数
	 * @param request
	 * @param opsDTO
	 */
	private void checkThumbUpOrThumbDown(HttpServletRequest request, QuestionOpsDTO opsDTO){
		SessionUser sessionUser= userService.getUser(request);
		if (sessionUser == null){
			throw new SystemException(RespCodeEnum.SYS_ERROR.getCode(), "login user cannot be null");
		}
		String userNo = sessionUser.getStaffNo();
		String userName = sessionUser.getName();
		QaQuestionEvaluateDO evaluate = questionEvaluateService.findByUserIdAndQuestionIdAndEvaluate(userNo, opsDTO.getQuestionId(), opsDTO.getEventType());
		if (evaluate == null) {

			questionEvaluateService.save(evaluate);
			questionService.signQuestion(opsDTO.getQuestionId(), opsDTO.getEventType());
		}else{
			//questionService.signQuestion(opsDTO.getQuestionId(), 0);
		}
	}

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
//	}/**
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
