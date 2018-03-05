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

import com.netlink.pangu.dao.qa.QaQuestionDAO;
import com.netlink.pangu.entity.qa.QaQuestionDO;
import com.netlink.pangu.entity.qa.QaQuestionEvaluateDO;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * 问题服务实现类
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Slf4j
@Service
public class QaQuestionServiceImpl implements QaQuestionService {

	private QaQuestionDAO qaQuestionDAO;

	@Autowired
	public QaQuestionServiceImpl(QaQuestionDAO qaQuestionDAO){

		this.qaQuestionDAO = qaQuestionDAO;
	}

	@Override
	public void save(QaQuestionDO question) {
		question.setGmtCreated(new Date());
		try {
			qaQuestionDAO.insertSelective(question);
		} catch (Exception e){
			throw new BizException(RespCodeEnum.FAIL.getCode(), "failed to save question");
		}
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public void signQuestion(String userId, Long questionId, Integer eventType) {
		if (EventTypeEnum.THUMB_UP.getEventCode() == eventType) {
			qaQuestionDAO.updateThumbUp(questionId);
		}
		if (EventTypeEnum.THUMB_DOWN.getEventCode() == eventType) {
			qaQuestionDAO.updateThumbDown(questionId);
		}
		if (EventTypeEnum.READ.getEventCode() == eventType) {
			qaQuestionDAO.updateViews(questionId);
		}
	}

//	private void saveQuestionEvaluate(){
//		QaQuestionEvaluateDO evaluate = new QaQuestionEvaluateDO();
//		evaluate.setUserId(userNo);
//		evaluate.setUserName(userName);
//		evaluate.setQuestionId(opsDTO.getQuestionId());
//		evaluate.setEvaluate(opsDTO.getEventType());
//	}

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
