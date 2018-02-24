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

import com.netlink.pangu.ask.dao.AnswerEvaluateDAO;
import com.netlink.pangu.ask.model.AnswerEvaluateDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 回答评价服务实现类
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Service("answerEvaluateService")
public class AnswerEvaluateServiceImpl implements AnswerEvaluateService {

	@Autowired
	private AnswerEvaluateDAO answerEvaluateDAO;

	@Override
	public void saveEavluate(AnswerEvaluateDO evaluate) {
		evaluate.setGmtCreated(new Date());
		answerEvaluateDAO.insertSelective(evaluate);
	}

	@Override
	public AnswerEvaluateDO findByUserIdAndAnswerIdAndEvaluate(String userId, Long answerId, Integer evaluate) {
		AnswerEvaluateDO cond = new AnswerEvaluateDO();
		cond.setUserId(userId);
		cond.setAnswerId(answerId);
		cond.setEvaluate(evaluate);
		return answerEvaluateDAO.selectOne(cond);
	}

}
