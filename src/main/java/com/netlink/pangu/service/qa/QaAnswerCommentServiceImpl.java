/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless requireduired by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netlink.pangu.service.qa;

import com.netlink.pangu.consts.RespCodeEnum;
import com.netlink.pangu.dao.QaAnswerCommentMapper;
import com.netlink.pangu.dao.QaAnswerMapper;
import com.netlink.pangu.domain.QaAnswerComment;
import com.netlink.pangu.dto.request.qa.AnswerCommentPageDTO;
import com.netlink.pangu.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

/**
 * 评论服务实现类
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Service("commentService")
public class QaAnswerCommentServiceImpl implements QaAnswerCommentService {

	private QaAnswerCommentMapper answerCommentMapper;
	private QaAnswerMapper answerMapper;

	@Autowired
	public QaAnswerCommentServiceImpl(QaAnswerCommentMapper answerCommentMapper,
									  QaAnswerMapper answerMapper){
		this.answerCommentMapper = answerCommentMapper;
		this.answerMapper = answerMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public void save(QaAnswerComment answerComment) {
		try{
		answerCommentMapper.insertSelective(answerComment);
		answerMapper.updateComments(answerComment.getAnswerId());
		} catch	(Exception e){
			throw new BizException(RespCodeEnum.FAIL.getCode(), "failed to save answer comment");
		}
	}

	@Override
	public Page<QaAnswerComment> pageByCondition(AnswerCommentPageDTO pageDTO) {
		Condition cond = new Condition(QaAnswerComment.class);
		cond.createCriteria().andEqualTo("answerId", pageDTO.getAnswerId());
		cond.setOrderByClause("gmt_created desc");

		PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize(), true);

		return (Page<QaAnswerComment>) answerCommentMapper.selectByCondition(cond);
	}

	@Override
	public QaAnswerComment findById(Long answerCommentId) {
		return answerCommentMapper.selectByPrimaryKey(answerCommentId);
	}

}
