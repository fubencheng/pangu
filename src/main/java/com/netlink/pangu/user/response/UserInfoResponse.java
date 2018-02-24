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
package com.netlink.pangu.user.response;

import com.netlink.pangu.common.BaseResponse;
import com.netlink.pangu.user.response.dto.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息应答
 *
 * @author fubencheng
 * @version v0.1 2018-01-08 11:21 fubencheng Exp $
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserInfoResponse extends BaseResponse {

    private UserInfoDTO userInfo;

}