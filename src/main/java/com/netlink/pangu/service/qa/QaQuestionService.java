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

import com.github.pagehelper.Page;
import com.netlink.pangu.entity.qa.QaQuestionDO;
import com.netlink.pangu.request.qa.QuestionPageDTO;

/**
 * 问题服务.
 *
 * @author fubencheng.
 * @version 0.0.1 2017-11-30 20:45 fubencheng.
 */
public interface QaQuestionService {

	/**
	 * 保存问题
	 * @param question 问题信息
	 */
	void save(QaQuestionDO question);

//	void signQuestion(Long id, Integer eventType);
//
//	Page<QaQuestionDO> pageQuestionByCondition(QuestionPageDTO questionPageDTO);
//
//	QaQuestionDO findById(Long id);

}
