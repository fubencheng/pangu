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
package com.netlink.pangu.util.qa;

/**
 * 事件类型
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
public enum EventTypeEnum {

	/**
	 * 赞
	 */
	THUMB_UP((byte)1, "赞"),

	/**
	 * 踩
	 */
	THUMB_DOWN((byte)-1, "踩"),

	/**
	 * 读
	 */
	READ((byte) 0, "读");

	private byte eventCode;
	private String eventMsg;

	EventTypeEnum(byte eventCode, String eventMsg) {
		this.setEventCode(eventCode);
		this.setEventMsg(eventMsg);
	}

	public byte getEventCode() {
		return eventCode;
	}

	private void setEventCode(byte eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventMsg() {
		return eventMsg;
	}

	private void setEventMsg(String eventMsg) {
		this.eventMsg = eventMsg;
	}

}
