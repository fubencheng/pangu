///*
// * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
// * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
// * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
// * License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
// * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
// * specific language governing permissions and limitations under the License.
// */
//package com.netlink.pangu.web.qa;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//import com.netlink.pangu.entity.qa.QaAnswerDO;
//import com.netlink.pangu.entity.qa.QaAnswerEvaluateDO;
//import com.netlink.pangu.response.dto.qa.AnswerListResponse;
//import com.netlink.pangu.response.dto.qa.AnswerPageResponse;
//import com.netlink.pangu.response.dto.qa.AnswerDTO;
//import com.netlink.pangu.service.qa.AnswerEvaluateService;
//import com.netlink.pangu.service.qa.AnswerService;
//import com.netlink.pangu.util.qa.EventTypeEnum;
//import com.netlink.pangu.response.dto.user.SessionUser;
//import com.netlink.pangu.auth.util.SessionUserUtil;
//import com.netlink.pangu.response.dto.BaseResponse;
//import com.netlink.pangu.response.RespCodeEnum;
//import com.netlink.pangu.service.user.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.github.pagehelper.Page;
//
///**
// * 问题回答控制器
// *
// * @author fubencheng
// * @version v0.1 2017-11-30 20:45 fubencheng Exp $
// */
//@Slf4j
//@RestController
//@RequestMapping("/ask/answer")
//public class AnswerController {
//
//	private AnswerService answerService;
//	private AnswerEvaluateService answerEvaluateService;
//	private UserService userService;
//
//    @Autowired
//	public AnswerController(AnswerService answerService, AnswerEvaluateService answerEvaluateService,
//                            UserService userService){
//	    this.answerService = answerService;
//	    this.answerEvaluateService = answerEvaluateService;
//	    this.userService = userService;
//    }
//
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
//
//	@GetMapping("/list")
//	public BaseResponse getAnswerList(@RequestParam("questionId") Long questionId,
//                                      @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
//                                      @RequestParam("orderBy") String orderBy) {
//		Map<String, Object> condition = new HashMap<>(8);
//		condition.put("questionId", questionId);
//		condition.put("pageNum", pageNum);
//		condition.put("pageSize", pageSize);
//		condition.put("orderBy", orderBy);
//		Page<QaAnswerDO> answerList = answerService.pageAnswerByCondition(condition);
//		AnswerPageResponse pageDTO = new AnswerPageResponse();
//		pageDTO.setPageNum(pageNum);
//		pageDTO.setPageSize(answerList.getPageSize());
//		pageDTO.setTotal(answerList.getTotal());
//		List<AnswerDTO> answerDTOList = new ArrayList<>();
//		for (QaAnswerDO answer : answerList) {
//			AnswerDTO answerDTO = mapperFacade.map(answer, AnswerDTO.class);
//			answerDTOList.add(answerDTO);
//		}
//		pageDTO.setAnswerList(answerDTOList);
//		return new AnswerListResponse(pageDTO);
//	}
//
//}
