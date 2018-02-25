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
package com.netlink.pangu.web.user;

import com.netlink.pangu.response.BaseResponse;
import com.netlink.pangu.response.dto.user.UserInfoDTO;
import com.netlink.pangu.web.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器
 *
 * @author fubencheng
 * @version 0.0.1 2017-12-15 19:49 fubencheng
 */
@RestController
public class UserController extends BaseController {

    @Value("${user.admin}")
    private String userAdmin;

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/user/info")
    public BaseResponse getCurrentUser() {

        boolean isAdmin = StringUtils.contains(userAdmin, USER);

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setIsAdmin(isAdmin);
        userInfoDTO.setName(USER);

        BaseResponse resp = new BaseResponse();
        resp.setRespObject(userInfoDTO);

        return resp;
    }

}