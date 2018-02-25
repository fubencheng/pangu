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
package com.netlink.pangu.dao.qa;

import com.netlink.pangu.entity.qa.QaAnswerDO;
import tk.mybatis.mapper.common.Mapper;

/**
 * 回答DAO
 *
 * @author fubencheng
 * @version 0.0.1 2018-01-16 17:21 fubencheng
 */
public interface QaAnswerDAO extends Mapper<QaAnswerDO> {

    /**
     * 用户对回答进行评论后，累加回答的评论数
     *
     * @param id 回答ID
     */
    void updateComments(Long id);

    /**
     * 更新回答点赞计数
     *
     * @param id 回答ID
     */
    void updateLikes(Long id);

    /**
     * 更新回答水计数
     *
     * @param id 回答ID
     */
    void updateDislikes(Long id);
}