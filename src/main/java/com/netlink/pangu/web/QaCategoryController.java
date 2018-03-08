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
package com.netlink.pangu.web;

import com.netlink.pangu.domain.QaCategory;
import com.netlink.pangu.dto.response.BaseResult;
import com.netlink.pangu.dto.response.qa.QaCategoryDTO;
import com.netlink.pangu.service.qa.QaCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * QaCategoryController.
 *
 * @author fubencheng.
 * @version 0.0.1 2017-11-30 20:45 fubencheng.
 */
@RestController
@RequestMapping("/qa/category")
public class QaCategoryController {

	private QaCategoryService categoryService;

	@Autowired
	public QaCategoryController(QaCategoryService categoryService){
			this.categoryService = categoryService;
	}

	/**
	 * 查询问题分类
	 * @return BaseResult<List<QaCategoryDTO>>
	 */
	@GetMapping("/list")
	public BaseResult<List<QaCategoryDTO>> getQaCategoryList() {
		QaCategory category = new QaCategory();
		List<QaCategory> categoryDOList = categoryService.findByCondition(category);
		List<QaCategoryDTO> categoryList = new ArrayList<>();
		for (QaCategory categoryDO : categoryDOList){
			QaCategoryDTO categoryDTO = new QaCategoryDTO();
			BeanUtils.copyProperties(categoryDO, categoryDTO);
			categoryList.add(categoryDTO);
		}
		return new BaseResult<>(categoryList);
	}

}
