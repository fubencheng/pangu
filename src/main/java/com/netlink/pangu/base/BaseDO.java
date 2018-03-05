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
package com.netlink.pangu.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * BaseDO
 *
 * @author fubencheng
 * @version 0.0.1 2018-03-05 20:20 fubencheng
 */
@Data
public class BaseDO {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 是否逻辑删除
     */
    @Column(name = "is_delete")
    private String isDelete = "0";

    /**
     * 创建时间
     */
    @Column(name = "gmt_created")
    private Date gmtCreated = new Date();

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;
}