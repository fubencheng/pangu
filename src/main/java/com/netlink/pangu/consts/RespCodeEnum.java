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
package com.netlink.pangu.consts;

/**
 * 应答码枚举
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
public enum RespCodeEnum {

    /**
     * 成功
     */
	SUCCESS("000", "操作成功"),

    /**
     * 参数异常
     */
    ILLEGAL_ARG("001", "参数异常"),

    /**
     * 失败
     */
	FAIL("999", "操作失败"),

    /**
     * 问答模块，已经点过赞
     */
    ALREADY_THUMBSUP("1000", "已经点过赞"),

    /**
     * 问答模块，已经点过踩
     */
    ALREADY_THUMBSDOWN("1001", "已经点过踩");

	private String code;
	private String message;

	RespCodeEnum(String code, String message){
	    this.code = code;
	    this.message = message;
    }

    public String getCode(){
	    return code;
    }

    public String getMessage(){
        return message;
    }
	
}
