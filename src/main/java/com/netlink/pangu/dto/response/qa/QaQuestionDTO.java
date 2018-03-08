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
 * QaQuestionDTO.
 *
 * @author fubencheng.
 * @version 0.0.1 2017-11-30 20:45 fubencheng.
 */
@Data
public class QaQuestionDTO implements Serializable {

	private static final long serialVersionUID = -7103657543450771946L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 问题分类主键ID
	 */
	private Long categoryId;

	/**
	 * 问题分类名
	 */
	private String categoryName;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 问题内容
	 */
	private String question;

	/**
	 * 提问人ID
	 */
	private String userId;

	/**
	 * 提问人姓名
	 */
	private String userName;

	/**
	 * 浏览数
	 */
	private Long views;

	/**
	 * 回答数
	 */
	private Long answers;

	/**
	 * 点赞数
	 */
	private Long thumbUp;

	/**
	 * 点踩数
	 */
	private Long thumbDown;

	/**
	 * 提问时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = DateUtil.DEFAULT_DATE_TIME_FORMAT)
	private Date gmtCreated;

}
