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

import com.github.pagehelper.Page;
import com.netlink.pangu.domain.QaAnswer;
import com.netlink.pangu.dto.request.qa.AnswerPageDTO;
import com.netlink.pangu.dto.response.PageResult;
import com.netlink.pangu.dto.response.qa.QaAnswerDTO;
import com.netlink.pangu.service.qa.QaAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * QaAnswerController
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Slf4j
@RestController
@RequestMapping("/qa/answer")
public class QaAnswerController extends BaseController {

	private QaAnswerService answerService;

    @Autowired
	public QaAnswerController(QaAnswerService answerService){
	    this.answerService = answerService;
    }

//	@PostMapping("/save")
//	public BaseResponse saveAnswer(HttpServletRequest request, @RequestBody AnswerDTO answer) {
//        SessionUser sessionUser = userService.getUser(request);
//		String userNo = sessionUser.getStaffNo();
//		String userName = sessionUser.getName();
//
//		QaAnswerDO answerModel = new QaAnswerDO();
//		answerModel.setUserId(userNo);
//		answerModel.setUserName(userName);
//		answerModel.setQuestionId(answer.getQuestionId());
//		// 过滤掉emoji表情编码
//		String answerEmoji = answer.getAnswer()
//				.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
//		answerModel.setAnswer(answerEmoji);
//		try {
//			answerService.saveAnswer(answerModel);
//			return new BaseResponse(RespCodeEnum.SUCCESS.getCode(), RespCodeEnum.SUCCESS.getMessage());
//		} catch (Exception e) {
//			log.error("Failed to save answer, userName={}, questionId={}, e={}", userName, answer.getQuestionId(), e);
//			return new BaseResponse(RespCodeEnum.FAIL.getCode(), RespCodeEnum.FAIL.getMessage());
//		}
//	}
//
//	@PostMapping("/sign")
//	public BaseResponse signAnswer(HttpServletRequest request, @RequestBody Map<String, Object> params) {
//		Long answerId = Long.parseLong(params.get("answerId").toString());
//		Integer eventType = Integer.parseInt(params.get("eventType").toString());
//		if (EventTypeEnum.THUMB_UP.getEventCode() == eventType || EventTypeEnum.THUMB_DOWN.getEventCode() == eventType) {
//			SessionUser sessionUser = SessionUserUtil.getCurrentUser(request);
//			if(sessionUser == null){
//				sessionUser= userService.getUser(request);
//			}
//			String userNo = sessionUser.getStaffNo();
//			String userName = sessionUser.getName();
//			QaAnswerEvaluateDO evaluate = answerEvaluateService.findByUserIdAndAnswerIdAndEvaluate(userNo, answerId, eventType);
//			if (evaluate == null) {
//				evaluate = new QaAnswerEvaluateDO();
//				evaluate.setUserId(userNo);
//				evaluate.setUserName(userName);
//				evaluate.setAnswerId(answerId);
//				evaluate.setEvaluate(eventType);
//                answerEvaluateService.saveEavluate(evaluate);
//				answerService.signAnswer(answerId, eventType);
//				return new BaseResponse(RespCodeEnum.SUCCESS.getCode(), RespCodeEnum.SUCCESS.getMessage());
//			} else {
//				return new BaseResponse(RespCodeEnum.FAIL.getCode(), RespCodeEnum.FAIL.getMessage());
//			}
//		}
//
//		return new BaseResponse(RespCodeEnum.SUCCESS.getCode(), RespCodeEnum.SUCCESS.getMessage());
//	}

	@GetMapping("/list")
	public PageResult<QaAnswerDTO> getAnswerList(@Valid AnswerPageDTO pageDTO) {
		Page<QaAnswer> page = answerService.pageByCondition(pageDTO);
		List<QaAnswerDTO> answerDTOList = new ArrayList<>();
		for (QaAnswer answer : page) {
			QaAnswerDTO answerDTO = new QaAnswerDTO();
			BeanUtils.copyProperties(answer, answerDTO);
			answerDTOList.add(answerDTO);
		}
		PageResult<QaAnswerDTO> result = new PageResult<>(answerDTOList);
		result.setTotal(page.getTotal());
		result.setPageNum(page.getPageNum());
		result.setPageSize(page.getPageSize());
		return result;
	}

}
