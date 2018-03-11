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
import com.netlink.pangu.consts.RespCodeEnum;
import com.netlink.pangu.domain.QaAnswer;
import com.netlink.pangu.domain.QaAnswerComment;
import com.netlink.pangu.domain.QaAnswerEvaluate;
import com.netlink.pangu.dto.request.qa.*;
import com.netlink.pangu.dto.response.BaseResult;
import com.netlink.pangu.dto.response.PageResult;
import com.netlink.pangu.dto.response.qa.QaAnswerCommentDTO;
import com.netlink.pangu.dto.response.qa.QaAnswerDTO;
import com.netlink.pangu.service.qa.QaAnswerCommentService;
import com.netlink.pangu.service.qa.QaAnswerEvaluateService;
import com.netlink.pangu.service.qa.QaAnswerService;
import com.netlink.pangu.service.qa.QaQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	/**
	 * 赞
	 */
	private static final byte ZAN = 1;

	/**
	 * 踩
	 */
	private static final byte CAI = -1;


	private QaAnswerService answerService;
	private QaQuestionService questionService;
	private QaAnswerEvaluateService answerEvaluateService;
	private QaAnswerCommentService answerCommentService;

    @Autowired
	public QaAnswerController(QaAnswerService answerService,
							  QaQuestionService questionService,
							  QaAnswerEvaluateService answerEvaluateService,
							  QaAnswerCommentService answerCommentService){
	    this.answerService = answerService;
	    this.questionService = questionService;
	    this.answerEvaluateService = answerEvaluateService;
		this.answerCommentService = answerCommentService;
    }

	/**
	 * 保存回答
	 * @param answerAddDTO answerAddDTO
	 * @return BaseResult
	 */
	@PostMapping("/save")
	public BaseResult save(@Valid @RequestBody AnswerAddDTO answerAddDTO) {
		log.info("save answer, answer={}", answerAddDTO);
		if (questionService.findById(answerAddDTO.getQuestionId()) == null){
			return new BaseResult(RespCodeEnum.ILLEGAL_ARG);
		}

		QaAnswer answer = new QaAnswer();
		answer.setUserId(USER_NO);
		answer.setUserName(USER);
		answer.setQuestionId(answerAddDTO.getQuestionId());
		// 过滤掉emoji表情编码
		String answerEmoji = answerAddDTO.getAnswer()
				.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
		answer.setAnswer(answerEmoji);
		answerService.save(answer);
		return new BaseResult(RespCodeEnum.SUCCESS);
	}

	/**
	 * 回答赞踩记录计数
	 * @param opsDTO opsDTO
	 * @return BaseResult
	 */
	@PostMapping("/sign")
	public BaseResult signAnswer(@Valid @RequestBody AnswerOpsDTO opsDTO) {
		log.info("sign answer, ops={}", opsDTO);
		if (answerService.findById(opsDTO.getAnswerId()) == null){
			return new BaseResult(RespCodeEnum.ILLEGAL_ARG);
		}
		if (ZAN == opsDTO.getOps()) {
			QaAnswerEvaluate condition = new QaAnswerEvaluate();
			condition.setAnswerId(opsDTO.getAnswerId());
			condition.setUserId(USER_NO);
			condition.setEvaluate(ZAN);
			List<QaAnswerEvaluate> evaluateList = answerEvaluateService.findByCondition(condition);
			if (evaluateList.isEmpty()) {
				saveAnswerEavluate(opsDTO);
				answerService.increaseLikes(opsDTO);
				return new BaseResult(RespCodeEnum.SUCCESS);
			} else {
				return new BaseResult(RespCodeEnum.ALREADY_LIKE);
			}
		}
		if (CAI == opsDTO.getOps()){
			QaAnswerEvaluate condition = new QaAnswerEvaluate();
			condition.setAnswerId(opsDTO.getAnswerId());
			condition.setUserId(USER_NO);
			condition.setEvaluate(CAI);
			List<QaAnswerEvaluate> evaluateList = answerEvaluateService.findByCondition(condition);
			if (evaluateList.isEmpty()) {
				saveAnswerEavluate(opsDTO);
				answerService.increaseDislikes(opsDTO);
				return new BaseResult(RespCodeEnum.SUCCESS);
			} else {
				return new BaseResult(RespCodeEnum.ALREADY_DISLIKE);
			}
		}
		return new BaseResult(RespCodeEnum.SUCCESS);
	}

	private void saveAnswerEavluate(AnswerOpsDTO opsDTO){
		QaAnswerEvaluate evaluate = new QaAnswerEvaluate();
		evaluate.setUserId(USER_NO);
		evaluate.setUserName(USER);
		evaluate.setAnswerId(opsDTO.getAnswerId());
		evaluate.setEvaluate(opsDTO.getOps());
		answerEvaluateService.save(evaluate);
	}

	/**
	 * 分页查询回答列表
	 * @param pageDTO pageDTO
	 * @return PageResult<QaAnswerDTO>
	 */
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

	@PostMapping("/comment/save")
	public BaseResult saveAnswerComment(@Valid @RequestBody AnswerCommentAddDTO commentAddDTO) {
		log.info("save answer comment, params={}", commentAddDTO);
		if (answerService.findById(commentAddDTO.getAnswerId()) == null){
			return new BaseResult(RespCodeEnum.ILLEGAL_ARG);
		}

		QaAnswerComment answerComment = new QaAnswerComment();
		answerComment.setUserId(USER_NO);
		answerComment.setUserName(USER);
		answerComment.setAnswerId(commentAddDTO.getAnswerId());
		answerComment.setComment(commentAddDTO.getComment());
		answerComment.setReplyToCommentId(commentAddDTO.getReplyToCommentId());
		if (commentAddDTO.getReplyToCommentId() != null){
			QaAnswerComment originComment =answerCommentService.findById(commentAddDTO.getReplyToCommentId());
			answerComment.setReplyToUserId(originComment.getUserId());
			answerComment.setReplyToUserName(originComment.getUserName());

		}
		answerCommentService.save(answerComment);
		return new BaseResult(RespCodeEnum.SUCCESS);
	}

	@GetMapping("/comment/list")
	public PageResult getAnswerCommentList(@Valid AnswerCommentPageDTO pageDTO) {
		Page<QaAnswerComment> commentList = answerCommentService.pageByCondition(pageDTO);
		List<QaAnswerCommentDTO> commentDTOList = new ArrayList<>();
		for (QaAnswerComment comment : commentList) {
			QaAnswerCommentDTO commentDTO = new QaAnswerCommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTOList.add(commentDTO);
		}
		PageResult result = new PageResult<>(commentDTOList);
		result.setPageNum(commentList.getPageNum());
		result.setPageSize(commentList.getPageSize());
		result.setTotal(commentList.getTotal());
		return result;
	}

}
