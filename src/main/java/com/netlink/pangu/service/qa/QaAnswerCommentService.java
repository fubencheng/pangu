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
import com.netlink.pangu.domain.QaAnswerComment;
import com.netlink.pangu.dto.request.qa.AnswerCommentPageDTO;

/**
 * QaAnswerCommentService
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
public interface QaAnswerCommentService {

	/**
	 * 保存回答评论
	 * @param answerComment answerComment
	 */
	void save(QaAnswerComment answerComment);

	/**
	 * 分页查询回答评论
	 * @param pageDTO pageDTO
	 * @return Page<QaAnswerComment>
	 */
	Page<QaAnswerComment> pageByCondition(AnswerCommentPageDTO pageDTO);

	/**
	 * 主键查询
	 * @param answerCommentId answerCommentId
	 * @return QaAnswerComment
	 */
	QaAnswerComment findById(Long answerCommentId);
}
