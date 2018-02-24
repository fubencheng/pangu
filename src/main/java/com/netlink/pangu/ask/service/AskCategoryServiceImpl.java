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
package com.netlink.pangu.ask.service;

import com.netlink.pangu.ask.dao.AskCategoryDAO;
import com.netlink.pangu.ask.model.AskCategoryDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 问题分类服务实现类
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Service("askCategoryService")
public class AskCategoryServiceImpl implements AskCategoryService {

	private AskCategoryDAO askCategoryDAO;

	@Autowired
	public AskCategoryServiceImpl(AskCategoryDAO askCategoryDAO){
		this.askCategoryDAO = askCategoryDAO;
	}

	@Override
	public AskCategoryDO findById(Long id) {
		return askCategoryDAO.selectByPrimaryKey(id);
	}

	@Override
	public List<AskCategoryDO> findAll() {
		return askCategoryDAO.findAll();
	}

}
