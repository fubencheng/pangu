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
package com.netlink.pangu.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通用分页应答
 *
 * @author fubencheng
 * @version 0.0.1 2018-01-19 19:12 fubencheng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasePageResponse extends BaseResponse {

    private static final long serialVersionUID = -4439370182500011834L;

    /**
     * 页码
     */
    private int pageNum;

    /**
     * 每页最大记录数
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private long total;

}