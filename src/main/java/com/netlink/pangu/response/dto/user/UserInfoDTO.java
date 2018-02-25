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
package com.netlink.pangu.response.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author fubencheng
 * @version 0.0.1 2018-01-08 11:23 fubencheng
 */
@Data
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 1716741093135573277L;

    /**
     * 是否是管理员
     */
    private Boolean isAdmin;

    /**
     * 用户名
     */
    private String name;

}