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
package com.netlink.pangu.ask.response.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netlink.pangu.common.DateUtil;
import lombok.Data;

/**
 * 问题DTO
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Data
public class QuestionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long categoryId;

	private String categoryName;

	private String title;

	private String question;

	private String userId;

	private String userName;

	private Long views;

	private Long answers;

	private Long thumbUp;

	private Long thumbDown;

	@JsonFormat(timezone = "GMT+8", pattern = DateUtil.DEFAULT_DATE_TIME_FORMAT)
	private Date gmtCreated;

}
