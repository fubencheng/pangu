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
package com.netlink.pangu.dto.response;

import com.netlink.pangu.consts.RespCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础应答类.
 *
 * @author fubencheng.
 * @version 0.0.1 2017-11-30 20:45 fubencheng.
 */
@Data
public class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = 5784851657861601192L;

	/**
	 * 返回码
	 */
	protected String code = RespCodeEnum.SUCCESS.getCode();

	/**
	 * 返回信息
	 */
	protected String message = RespCodeEnum.SUCCESS.getMessage();

	/**
	 * 返回数据
	 */
	protected T result;

	public BaseResult(RespCodeEnum respCodeEnum){
		this.code = respCodeEnum.getCode();
		this.message = respCodeEnum.getMessage();
	}

	public BaseResult(T result){
		this.code  = RespCodeEnum.SUCCESS.getCode();
		this.message = RespCodeEnum.SUCCESS.getMessage();
		this.result = result;
	}

}
