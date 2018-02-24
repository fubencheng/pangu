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
package com.netlink.pangu.common;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 基础域对象
 *
 * @author fubencheng
 * @version v0.1 2018-01-11 20:30 fubencheng Exp $
 */
@Data
public class BaseDO {

    @Id
    @GeneratedValue(generator = "JDBC")
    public Long id;

    @Column(name = "is_delete")
    public boolean isDelete;

    @Column(name = "gmt_created")
    public Date gmtCreated;

    @Column(name = "gmt_modified")
    public Date gmtModified;

}