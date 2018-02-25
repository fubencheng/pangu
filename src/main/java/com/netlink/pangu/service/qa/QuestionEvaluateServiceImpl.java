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
//package com.netlink.pangu.service.qa;
//
//import java.util.Date;
//
//import com.netlink.pangu.dao.qa.QaQuestionEvaluateDAO;
//import com.netlink.pangu.entity.qa.QaQuestionEvaluateDO;
//import com.netlink.pangu.response.RespCodeEnum;
//import com.netlink.pangu.exception.BizException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 问题评价服务实现类
// *
// * @author fubencheng
// * @version v0.1 2017-11-30 20:45 fubencheng Exp $
// */
//@Service("questionEvaluateService")
//public class QuestionEvaluateServiceImpl implements QuestionEvaluateService {
//
//	private QaQuestionEvaluateDAO questionEvaluateDAO;
//
//	@Autowired
//	public QuestionEvaluateServiceImpl(QaQuestionEvaluateDAO questionEvaluateDAO){
//		this.questionEvaluateDAO = questionEvaluateDAO;
//	}
//
//	@Override
//	public void save(QaQuestionEvaluateDO evaluate) {
//		evaluate.setGmtCreated(new Date());
//		try {
//			questionEvaluateDAO.insertSelective(evaluate);
//		}catch (Exception e){
//			throw new BizException(RespCodeEnum.FAIL.getCode(), "failed to save question evaluate");
//		}
//	}
//
//	@Override
//	public QaQuestionEvaluateDO findByUserIdAndQuestionIdAndEvaluate(String userId, Long questionId, Integer evaluate) {
//		QaQuestionEvaluateDO cond=new QaQuestionEvaluateDO();
//		cond.setUserId(userId);
//		cond.setQuestionId(questionId);
//		cond.setEvaluate(evaluate);
//		return questionEvaluateDAO.selectOne(cond);
//		/*return questionEvaluateDAO.selectByUserIdAndQuestionIdAndEvaluate(userId, questionId, evaluate);*/
//	}
//
//}
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
//package com.netlink.pangu.service.qa;
//
//import java.util.Date;
//
//import com.netlink.pangu.dao.qa.QaQuestionEvaluateDAO;
//import com.netlink.pangu.entity.qa.QaQuestionEvaluateDO;
//import com.netlink.pangu.response.RespCodeEnum;
//import com.netlink.pangu.exception.BizException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 问题评价服务实现类
// *
// * @author fubencheng
// * @version v0.1 2017-11-30 20:45 fubencheng Exp $
// */
//@Service("questionEvaluateService")
//public class QuestionEvaluateServiceImpl implements QuestionEvaluateService {
//
//	private QaQuestionEvaluateDAO questionEvaluateDAO;
//
//	@Autowired
//	public QuestionEvaluateServiceImpl(QaQuestionEvaluateDAO questionEvaluateDAO){
//		this.questionEvaluateDAO = questionEvaluateDAO;
//	}
//
//	@Override
//	public void save(QaQuestionEvaluateDO evaluate) {
//		evaluate.setGmtCreated(new Date());
//		try {
//			questionEvaluateDAO.insertSelective(evaluate);
//		}catch (Exception e){
//			throw new BizException(RespCodeEnum.FAIL.getCode(), "failed to save question evaluate");
//		}
//	}
//
//	@Override
//	public QaQuestionEvaluateDO findByUserIdAndQuestionIdAndEvaluate(String userId, Long questionId, Integer evaluate) {
//		QaQuestionEvaluateDO cond=new QaQuestionEvaluateDO();
//		cond.setUserId(userId);
//		cond.setQuestionId(questionId);
//		cond.setEvaluate(evaluate);
//		return questionEvaluateDAO.selectOne(cond);
//		/*return questionEvaluateDAO.selectByUserIdAndQuestionIdAndEvaluate(userId, questionId, evaluate);*/
//	}
//
//}
