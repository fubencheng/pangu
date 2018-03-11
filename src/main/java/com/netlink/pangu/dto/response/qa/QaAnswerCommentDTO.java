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
package com.netlink.pangu.dto.response.qa;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netlink.pangu.util.DateUtil;
import lombok.Data;

/**
 * QaAnswerCommentDTO
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Data
public class QaAnswerCommentDTO implements Serializable {

	private static final long serialVersionUID = 2447753504517921798L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 评论人ID
	 */
	private String userId;

	/**
	 * 评论人姓名
	 */
	private String userName;

	/**
	 * 回答ID
	 */
	private Long answerId;

	/**
	 * 评论内容
	 */
	private String comment;

	/**
	 * 回复评论ID
	 */
	private Long replyToCommentId;

	/**
	 * 回复评论评论人ID
	 */
	private String replyToUserId;

	/**
	 * 回复评论评论人姓名
	 */
	private String replyToUserName;

	/**
	 * 评论时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = DateUtil.DEFAULT_DATE_TIME_FORMAT)
	private Date gmtCreated;
}
