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
package com.netlink.pangu.user.web;

import com.netlink.pangu.user.SessionUser;
import com.netlink.pangu.user.response.UserInfoResponse;
import com.netlink.pangu.user.response.dto.UserInfoDTO;
import com.netlink.pangu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息控制器
 *
 * @author fubencheng
 * @version v0.1 2017-12-15 19:49 fubencheng Exp $
 */
@RestController
public class UserController {

    @Value("${user.admin}")
    private String userAdmin;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public UserInfoResponse getCurrentUserName(HttpServletRequest request) {
        SessionUser sessionUser = userService.getUser(request);
        String name = null;
        boolean isAdmin = false;

        if (sessionUser != null){
            name = sessionUser.getName();
            String phone = sessionUser.getPhone();
            isAdmin = StringUtils.contains(userAdmin, phone);
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setName(name);
        userInfoDTO.setIsAdmin(isAdmin);

        return new UserInfoResponse(userInfoDTO);
    }

}