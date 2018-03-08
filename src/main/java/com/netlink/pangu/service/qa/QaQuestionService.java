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
import com.netlink.pangu.domain.QaQuestion;
import com.netlink.pangu.dto.request.qa.QuestionPageDTO;

/**
 * QaQuestionService.
 *
 * @author fubencheng.
 * @version 0.0.1 2017-11-30 20:45 fubencheng.
 */
public interface QaQuestionService {

	/**
	 * 保存问题
	 * @param question question
	 */
	void save(QaQuestion question);

	/**
	 * 累加点赞数
	 * @param questionId questionId
	 * @return int
	 */
	int increaseThumbUp(Long questionId);

	/**
	 * 累加点踩数
	 * @param questionId questionId
	 * @return int
	 */
	int increaseThumbDown(Long questionId);

	/**
	 * 累加阅读数
	 * @param questionId questionId
	 * @return int
	 */
	int increaseViews(Long questionId);

	/**
	 * 分页查询问题记录
	 * @param pageDTO pageDTO
	 * @return Page<QaQuestion>
	 */
	Page<QaQuestion> pageByCondition(QuestionPageDTO pageDTO);

	/**
	 * 主键查询
	 * @param id id
	 * @return QaQuestion
	 */
	QaQuestion findById(Long id);

}
