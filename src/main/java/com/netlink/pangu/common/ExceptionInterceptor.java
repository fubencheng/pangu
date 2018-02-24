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

import com.netlink.pangu.common.exception.BizException;
import com.netlink.pangu.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author fubencheng
 * @version v0.1 2018-01-18 15:51 fubencheng Exp $
 */
@RestControllerAdvice
@Slf4j
public class ExceptionInterceptor {

    @ExceptionHandler({ Exception.class })
    public void illegalArgumentException(HttpServletRequest request, Exception e){
        System.out.println(e);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class, BindException.class })
    public BaseResponse parameterValidationException(HttpServletRequest request, Exception e){
        log.error("request uri[{}], exception class[{}]", request.getRequestURI(), e.getClass().getName(), e);
        return new BaseResponse(RespCodeEnum.ILLEGAL_ARG.getCode(), RespCodeEnum.ILLEGAL_ARG.getMessage());
    }

    @ExceptionHandler({SystemException.class})
    public BaseResponse systemException(HttpServletRequest request, Exception e){
        log.error("request uri[{}], exception class[{}]", request.getRequestURI(), e.getClass().getName(), e);
        return new BaseResponse(RespCodeEnum.SYS_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler({BizException.class})
    public BaseResponse bizException(HttpServletRequest request, Exception e){
        log.error("request rui[{}], exception class[{}]", request.getRequestURI(), e.getClass().getName(), e);
        return new BaseResponse(RespCodeEnum.FAIL.getCode(), e.getMessage());
    }

}