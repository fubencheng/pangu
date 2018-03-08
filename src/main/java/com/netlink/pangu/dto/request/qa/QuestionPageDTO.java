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
package com.netlink.pangu.dto.request.qa;

import com.netlink.pangu.dto.request.PageDTO;
import com.netlink.pangu.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * QuestionPageDTO.
 *
 * @author fubencheng.
 * @version 0.0.1 2018-01-19 19:04 fubencheng.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionPageDTO extends PageDTO {

    private static final long serialVersionUID = 7783836978968902064L;

    /**
     * 问题分类ID
     */
    private Long categoryId;

    /**
     * 提问查询起始时间
     */
    @DateTimeFormat(pattern = DateUtil.DEFAULT_DATE_FORMAT)
    private Date startDate;

    /**
     * 提问查询截至时间
     */
    @DateTimeFormat(pattern = DateUtil.DEFAULT_DATE_FORMAT)
    private Date endDate;

    /**
     * 排序索引值，1-回答数，2-阅览数，3-提问时间
     */
    private Short orderIndex;

}