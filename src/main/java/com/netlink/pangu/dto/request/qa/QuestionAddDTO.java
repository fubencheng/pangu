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

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * QuestionAddDTO
 *
 * @author fubencheng
 * @version 0.0.1 2018-01-18 15:26 fubencheng
 */
@Data
@ToString
public class QuestionAddDTO implements Serializable {

    private static final long serialVersionUID = -6264877046106262834L;

    /**
     * 分类ID
     */
    @NotNull
    private Long categoryId;

    /**
     * 问题标题
     */
    @NotBlank
    private String title;

    /**
     * 问题内容
     */
    @NotBlank
    private String question;

}