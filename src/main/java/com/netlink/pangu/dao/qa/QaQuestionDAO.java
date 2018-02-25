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

import com.netlink.pangu.entity.qa.QaQuestionDO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 问题DAO
 *
 * @author fubencheng
 * @version 0.0.1 2018-01-16 17:37 fubencheng
 */
public interface QaQuestionDAO extends Mapper<QaQuestionDO> {

    /**
     * 根据条件查询问题列表
     *
     * @param paramMap 查询参数
     * @return 问题列表
     */
    List<QaQuestionDO> findByCondition(Map<String, Object> paramMap);

    /**
     * 用户回答问题后，累加回答数
     *
     * @param id 问题ID
     */
    void updateAnswers(Long id);

    /**
     * 更新问题点赞计数
     *
     * @param id 问题ID
     */
    void updateThumbUp(Long id);

    /**
     * 更新问题踩计数
     *
     * @param id 问题ID
     */
    void updateThumbDown(Long id);

    /**
     * 更新问题浏览计数
     *
     * @param id 问题ID
     */
    void updateViews(Long id);
}
