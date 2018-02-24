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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
public class DateUtil {

	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

	public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	private DateUtil() {
	}

	public static String toDateTime(Date date) {
		return toDateTime(date, DEFAULT_DATE_TIME_FORMAT);
	}

	public static String toDateTime(Date dateTime, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(dateTime);
	}

}