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
package com.netlink.pangu.ask.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.netlink.pangu.ask.model.AskCategoryDO;
import com.netlink.pangu.ask.model.QuestionDO;
import com.netlink.pangu.ask.model.QuestionEvaluateDO;
import com.netlink.pangu.ask.request.QuestionOpsDTO;
import com.netlink.pangu.ask.response.QuestionPageResponse;
import com.netlink.pangu.ask.response.dto.QuestionDTO;
import com.netlink.pangu.ask.service.AskCategoryService;
import com.netlink.pangu.ask.service.QuestionEvaluateService;
import com.netlink.pangu.ask.service.QuestionService;
import com.netlink.pangu.ask.util.EventTypeEnum;
import com.netlink.pangu.user.SessionUser;
import com.netlink.pangu.common.BaseResponse;
import com.netlink.pangu.common.RespCodeEnum;
import com.netlink.pangu.common.exception.SystemException;
import com.netlink.pangu.user.service.UserService;
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
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Slf4j
@RestController
@RequestMapping("/ask/question")
public class QuestionController {

	private QuestionService questionService;
	private AskCategoryService categoryService;
	private QuestionEvaluateService questionEvaluateService;
	private UserService userService;

	@Autowired
	public QuestionController(QuestionService questionService, AskCategoryService categoryService,
							  QuestionEvaluateService questionEvaluateService, UserService userService){
		this.questionService = questionService;
		this.categoryService = categoryService;
		this.questionEvaluateService = questionEvaluateService;
		this.userService = userService;
	}

	@PostMapping("/save")
	public BaseResponse save(HttpServletRequest request, @Valid @RequestBody com.netlink.pangu.ask.request.QuestionDTO questionDTO) {
		SessionUser sessionUser= userService.getUser(request);
		if (sessionUser == null){
			throw new SystemException(RespCodeEnum.SYS_ERROR.getCode(), "login user cannot be null");
		}
		String userNo = sessionUser.getStaffNo();
		String userName = sessionUser.getName();

		AskCategoryDO category = categoryService.findById(questionDTO.getCategoryId());
		QuestionDO questionDO = new QuestionDO();
		questionDO.setCategoryId(category.getId());
		questionDO.setCategoryName(category.getCategoryName());
		questionDO.setUserId(userNo);
		questionDO.setUserName(userName);
		questionDO.setTitle(questionDTO.getTitle());
		// 过滤掉emoji表情编码
		String questionEmoji = questionDTO.getQuestion()
				.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
		questionDO.setQuestion(questionEmoji);
		questionService.save(questionDO);

		return new BaseResponse();
	}

	/**
	 * 顶踩浏览次数计数
	 *
	 * @param request
	 * @param opsDTO
	 * @return
	 */
	@PostMapping("/sign")
	public BaseResponse signQuestion(HttpServletRequest request, @Valid @RequestBody QuestionOpsDTO opsDTO) {
		if (EventTypeEnum.THUMB_UP.getEventCode() == opsDTO.getEventType() || EventTypeEnum.THUMB_DOWN.getEventCode() == opsDTO.getEventType()) {
			checkThumbUpOrThumbDown(request, opsDTO);
		}else{
			questionService.signQuestion(opsDTO.getQuestionId(), 0);
		}
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
		QuestionEvaluateDO evaluate = questionEvaluateService.findByUserIdAndQuestionIdAndEvaluate(userNo, opsDTO.getQuestionId(), opsDTO.getEventType());
		if (evaluate == null) {
			evaluate = new QuestionEvaluateDO();
			evaluate.setUserId(userNo);
			evaluate.setUserName(userName);
			evaluate.setQuestionId(opsDTO.getQuestionId());
			evaluate.setEvaluate(opsDTO.getEventType());
			questionEvaluateService.save(evaluate);
			questionService.signQuestion(opsDTO.getQuestionId(), opsDTO.getEventType());
		}else{
			//questionService.signQuestion(opsDTO.getQuestionId(), 0);
		}
	}

	@GetMapping("/list")
	public QuestionPageResponse getQuestionList(@NotNull com.netlink.pangu.ask.request.QuestionPageDTO pageDTO) {
		Page<QuestionDO> questionList = questionService.pageQuestionByCondition(pageDTO);
		QuestionPageResponse pageResponse = new QuestionPageResponse();
		pageResponse.setPageNum(questionList.getPageNum());
		pageResponse.setPageSize(questionList.getPageSize());
		pageResponse.setTotal(questionList.getTotal());
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		for (QuestionDO question : questionList) {
			QuestionDTO questionDTO = mapperFacade.map(question, QuestionDTO.class);
			questionDTOList.add(questionDTO);
		}
		pageResponse.setQuestionDTOList(questionDTOList);
		return pageResponse;
	}

	@GetMapping("/{id}")
	public BaseResponse getQuestionDetail(@NotNull @PathVariable("id") Long id) {
		QuestionDO question = questionService.findById(id);
		QuestionDTO questionDTO = mapperFacade.map(question, QuestionDTO.class);

		return new QuestionResponse(questionDTO);
	}

}
