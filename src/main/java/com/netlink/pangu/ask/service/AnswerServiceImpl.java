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
package com.netlink.pangu.ask.service;

import java.util.Date;
import java.util.Map;

import com.github.pagehelper.Page;
import com.netlink.pangu.ask.dao.AnswerDAO;
import com.netlink.pangu.ask.dao.QuestionDAO;
import com.netlink.pangu.ask.model.AnswerDO;
import com.netlink.pangu.ask.util.EventTypeEnum;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

/**
 * 回答服务实现类
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerDAO answerDAO;

	@Autowired
	private QuestionDAO questionDAO;

	@Override
	public void saveAnswer(AnswerDO answer) {
		answer.setGmtCreated(new Date());
		answerDAO.insertSelective(answer);
		questionDAO.updateAnswers(answer.getQuestionId());
	}

	@Override
	public void signAnswer(Long id, Integer eventType) {
		if (EventTypeEnum.THUMB_UP.getEventCode() == eventType) {
			answerDAO.updateLikes(id);
		}
		if (EventTypeEnum.THUMB_DOWN.getEventCode() == eventType) {
			answerDAO.updateDislikes(id);
		}
	}

	@Override
	public Page<AnswerDO> pageAnswerByCondition(Map<String, Object> condition) {
		Example cond = new Example(AnswerDO.class);
		cond.createCriteria().andEqualTo("questionId", (Long) condition.get("questionId"));
		String orderBy = condition.get("orderBy").toString();
		StringBuilder orderBuilder = new StringBuilder();
		if (StringUtils.isNotBlank(orderBy)) {
			if ("atime".equalsIgnoreCase(orderBy)) {
				orderBuilder.append("gmt_created desc").append(" ");
			} else if ("rts".equalsIgnoreCase(orderBy)) {
				orderBuilder.append("comments desc").append(" ");
			} else if ("ats".equalsIgnoreCase(orderBy)) {
				orderBuilder.append("likes desc").append(" ");
			} else {
				orderBuilder.append("likes desc,dislikes asc,gmt_created desc").append(" ");
			}
		} else {
			orderBuilder.append("likes desc,dislikes asc,gmt_created desc").append(" ");
		}
		cond.setOrderByClause(orderBuilder.toString());

		int pageNum = (int) condition.get("pageNum");
		int pageSize = (int) condition.get("pageSize");
		PageHelper.startPage(pageNum, pageSize, true);

		return (Page<AnswerDO>) answerDAO.selectByExample(cond);
	}

}
