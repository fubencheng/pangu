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

import java.util.List;

import com.netlink.pangu.consts.RespCodeEnum;
import com.netlink.pangu.dao.QaAnswerEvaluateMapper;
import com.netlink.pangu.domain.QaAnswerEvaluate;
import com.netlink.pangu.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * QaAnswerEvaluateServiceImpl
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Service
public class QaAnswerEvaluateServiceImpl implements QaAnswerEvaluateService {

	private QaAnswerEvaluateMapper answerEvaluateMapper;

	@Autowired
	public QaAnswerEvaluateServiceImpl(QaAnswerEvaluateMapper answerEvaluateMapper){
		this.answerEvaluateMapper = answerEvaluateMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public void save(QaAnswerEvaluate answerEvaluate) {
		try {
			answerEvaluateMapper.insertSelective(answerEvaluate);
		} catch (Exception e) {
			throw new BizException(RespCodeEnum.FAIL.getCode(), "failed to save answer evaluate");
		}
	}

	@Override
	public List<QaAnswerEvaluate> findByCondition(QaAnswerEvaluate answerEvaluate) {
		return answerEvaluateMapper.select(answerEvaluate);
	}

}
