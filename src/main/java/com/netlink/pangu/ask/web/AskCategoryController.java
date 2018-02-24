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
package com.netlink.pangu.ask.web;

import com.netlink.pangu.ask.model.AskCategoryDO;
import com.netlink.pangu.ask.response.AskCategoryListResponse;
import com.netlink.pangu.ask.response.dto.AskCategoryDTO;
import com.netlink.pangu.ask.service.AskCategoryService;
import com.netlink.pangu.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题分类控制器
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@RestController
@RequestMapping("/ask/category")
public class AskCategoryController {

	private AskCategoryService askCategoryService;

	@Autowired
	public AskCategoryController(AskCategoryService askCategoryService){
		this.askCategoryService = askCategoryService;
	}

	@GetMapping("/list")
	public BaseResponse getAskCategoryList() {
		List<AskCategoryDO> categoryDOList = askCategoryService.findAll();
		List<AskCategoryDTO> categoryList = new ArrayList<>();
		for (AskCategoryDO categoryDO : categoryDOList){
			AskCategoryDTO categoryDTO = new AskCategoryDTO();
			categoryDTO.setId(categoryDO.getId());
			categoryDTO.setCategoryName(categoryDO.getCategoryName());
			categoryList.add(categoryDTO);
		}

		return new AskCategoryListResponse(categoryList);
	}

}
