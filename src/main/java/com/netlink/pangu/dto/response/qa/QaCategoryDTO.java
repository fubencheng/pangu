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

import lombok.Data;

import java.io.Serializable;

/**
 * 问题分类DTO
 *
 * @author fubencheng
 * @version 0.0.1 2018-01-18 15:09 fubencheng Exp $
 */
@Data
public class QaCategoryDTO implements Serializable {

    private static final long serialVersionUID = -3486061872709170373L;

    /**
     * 分类主键ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

}