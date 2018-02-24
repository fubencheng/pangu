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
package com.netlink.pangu.user.service;

import com.netlink.pangu.user.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息服务实现类
 *
 * @author fubencheng
 * @version v0.1 2018-01-12 16:11 fubencheng Exp $
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final String ENV_PROPERTY = "spring.profiles.active";
    private static final String ENV_DEV = "dev";

    @Autowired
    private Environment environment;

    @Override
    public SessionUser getUser(HttpServletRequest request) {
        if (environment.getProperty(ENV_PROPERTY).equals(ENV_DEV)){
            // 开发使用 mock SessionUser
            SessionUser user = new SessionUser();
            user.setName("admin");
            user.setStaffNo("000001");
            user.setDepartmentId("01");
            user.setDepartmentName("应用开发部");
            user.setPhone("13999999999");
            return user;
        }
    }

}