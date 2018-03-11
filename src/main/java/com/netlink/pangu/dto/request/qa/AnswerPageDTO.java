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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * AnswerPageDTO
 *
 * @author fubencheng
 * @version 0.0.1 2018-03-08 20:40 fubencheng
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class AnswerPageDTO extends PageDTO {

    private static final long serialVersionUID = -3058127972232977054L;

    /**
     * 问题主键ID
     */
    @NotNull
    private Long questionId;

    /**
     * 排序索引值, 1-回答时间，2-回复数，3-认可数
     */
    private Short orderIndex;

}