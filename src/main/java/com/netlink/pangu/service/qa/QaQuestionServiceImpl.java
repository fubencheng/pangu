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
package com.netlink.pangu.service.qa;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.netlink.pangu.dao.QaQuestionEvaluateMapper;
import com.netlink.pangu.dao.QaQuestionMapper;
import com.netlink.pangu.domain.QaQuestion;
import com.netlink.pangu.domain.QaQuestionEvaluate;
import com.netlink.pangu.request.qa.QuestionOpsDTO;
import com.netlink.pangu.request.qa.QuestionPageDTO;
import com.netlink.pangu.util.qa.EventTypeEnum;
import com.netlink.pangu.response.RespCodeEnum;
import com.netlink.pangu.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * QaQuestionServiceImpl
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Slf4j
@Service
public class QaQuestionServiceImpl implements QaQuestionService {

	private QaQuestionMapper questionMapper;
	private QaQuestionEvaluateMapper questionEvaluateMapper;

	@Autowired
	public QaQuestionServiceImpl(QaQuestionMapper questionMapper,
								 QaQuestionEvaluateMapper questionEvaluateMapper){
		this.questionMapper = questionMapper;
		this.questionEvaluateMapper = questionEvaluateMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public void save(QaQuestion question) {
		question.setGmtCreated(new Date());
		try {
			questionMapper.insertSelective(question);
		} catch (Exception e){
			throw new BizException(RespCodeEnum.FAIL.getCode(), "failed to save question");
		}
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public void signQuestion(String userId, String userName, QuestionOpsDTO opsDTO) {
		if (EventTypeEnum.THUMB_UP.getEventCode() == opsDTO.getEventType()) {
			saveQuestionEvaluate(userId, userName, opsDTO);
			questionMapper.updateThumbUp(opsDTO.getQuestionId());
		}
		if (EventTypeEnum.THUMB_DOWN.getEventCode() == opsDTO.getEventType()) {
			saveQuestionEvaluate(userId, userName, opsDTO);
			questionMapper.updateThumbDown(opsDTO.getQuestionId());
		}
		if (EventTypeEnum.READ.getEventCode() == opsDTO.getEventType()) {
			questionMapper.updateViews(opsDTO.getQuestionId());
		}
	}

	private void saveQuestionEvaluate(String userId, String userName, QuestionOpsDTO opsDTO){
		QaQuestionEvaluate evaluate = new QaQuestionEvaluate();
		evaluate.setUserId(userId);
		evaluate.setUserName(userName);
		evaluate.setQuestionId(opsDTO.getQuestionId());
		evaluate.setEvaluate(opsDTO.getEventType());
		questionEvaluateMapper.insertSelective(evaluate);
	}

//	@Override
//	public Page<QaQuestionDO> pageQuestionByCondition(QuestionPageDTO questionPageDTO) {
//		Map<String, Object> paramMap = new HashMap<>(16);
//		if (questionPageDTO.getCategoryId() != null && questionPageDTO.getCategoryId() != 0){
//			paramMap.put("categoryId", questionPageDTO.getCategoryId());
//		}
//		if (questionPageDTO.getStartDate() != null) {
//			paramMap.put("startTime", questionPageDTO.getStartDate());
//		}
//		if (questionPageDTO.getEndDate() != null) {
//			paramMap.put("endTime", questionPageDTO.getEndDate());
//		}
//		String orderBy = generateOrderByClause(questionPageDTO.getOrderBy());
//		paramMap.put("orderBy", orderBy);
//
//		PageHelper.startPage(questionPageDTO.getPageNum(), questionPageDTO.getPageSize(), true);
//
//		return (Page<QaQuestionDO>) questionDAO.findByCondition(paramMap);
//	}
//
//	private String generateOrderByClause(String orderBy){
//		StringBuilder orderBuilder = new StringBuilder();
//		if (StringUtils.isNotBlank(orderBy)) {
//			switch (orderBy) {
//				case "qtime":
//					orderBuilder.append("gmt_created desc").append(" ");
//					break;
//				case "ats":
//					orderBuilder.append("answers desc").append(" ");
//					break;
//				case "vts":
//					orderBuilder.append("views desc").append(" ");
//					break;
//				case "thumb":
//					orderBuilder.append("thumb_up desc").append(" ");
//					break;
//				default:
//					orderBuilder.append("gmt_created desc").append(" ");
//			}
//		} else {
//			/*orderBuilder.append(" views desc,").append(" ");
//			orderBuilder.append(" answers desc,").append(" ");*/
//			orderBuilder.append(" gmt_created desc ").append(" ");
//		}
//
//		return orderBuilder.toString();
//	}
//
//	@Override
//	public QaQuestionDO findById(Long id) {
//		return questionDAO.selectByPrimaryKey(id);
//	}

}
