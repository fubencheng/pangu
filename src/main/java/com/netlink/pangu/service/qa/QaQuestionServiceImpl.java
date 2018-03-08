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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.netlink.pangu.dao.QaQuestionMapper;
import com.netlink.pangu.domain.QaQuestion;
import com.netlink.pangu.consts.RespCodeEnum;
import com.netlink.pangu.dto.request.qa.QuestionPageDTO;
import com.netlink.pangu.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

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

	@Autowired
	public QaQuestionServiceImpl(QaQuestionMapper questionMapper){
		this.questionMapper = questionMapper;
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
	public int increaseThumbUp(Long questionId) {
		return questionMapper.updateThumbUp(questionId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public int increaseThumbDown(Long questionId) {
		return questionMapper.updateThumbDown(questionId);
	}

	@Override

	public int increaseViews(Long questionId) {
		return questionMapper.updateViews(questionId);
	}

	@Override
	public Page<QaQuestion> pageByCondition(QuestionPageDTO pageDTO) {
		Condition condition = new Condition(QaQuestion.class);
		Condition.Criteria criteria = condition.createCriteria();
		if (pageDTO.getCategoryId() != null){
			criteria.andEqualTo("categoryId", pageDTO.getCategoryId());
		}
		if (pageDTO.getStartDate() != null) {
			criteria.andGreaterThanOrEqualTo("gmtCreated", pageDTO.getStartDate());
		}
		if (pageDTO.getEndDate() != null) {
			criteria.andLessThan("gmtCreated", pageDTO.getEndDate());
		}
		String orderByClause = generateOrderByClause(pageDTO.getOrderIndex());
		condition.setOrderByClause(orderByClause);

		PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize(), true);

		return (Page<QaQuestion>) questionMapper.selectByCondition(condition);
	}

	private String generateOrderByClause(Short orderIndex){
		StringBuilder orderBuilder = new StringBuilder();
		if (orderIndex != null) {
			switch (orderIndex) {
				case 1:
					orderBuilder.append(" answers desc ").append(" ");
					break;
				case 2:
					orderBuilder.append(" views desc ").append(" ");
					break;
				case 3:
					orderBuilder.append(" thumb_up desc ").append(" ");
					break;
				case 4:
					orderBuilder.append(" gmt_created desc ").append(" ");
					break;
				default:
					orderBuilder.append(" gmt_created desc ").append(" ");
			}
		} else {
			orderBuilder.append(" gmt_created desc ").append(" ");
		}
		return orderBuilder.toString();
	}

	@Override
	public QaQuestion findById(Long id) {
		return questionMapper.selectByPrimaryKey(id);
	}

}
