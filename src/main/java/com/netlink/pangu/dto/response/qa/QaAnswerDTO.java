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
 * QaAnswerDTO
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Data
public class QaAnswerDTO implements Serializable {

	private static final long serialVersionUID = 519332054802339689L;

	/**
	 * 回答主键ID
	 */
	private Long id;

	/**
	 * 问题主键ID
	 */
	private Long questionId;

	/**
	 * 回答内容
	 */
	private String answer;

	/**
	 * 回答评论数
	 */
	private Long comments;

	/**
	 * 回答认可数
	 */
	private Long likes;

	/**
	 * 回答不认可数
	 */
	private Long dislikes;

	/**
	 * 回答人ID
	 */
	private String userId;

	/**
	 * 回答人姓名
	 */
	private String userName;

	/**
	 * 回答时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = DateUtil.DEFAULT_DATE_TIME_FORMAT)
	private Date gmtCreated;

}
