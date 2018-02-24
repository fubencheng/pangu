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

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.netlink.pangu.common.DateUtil;
import com.netlink.pangu.common.OssUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件控制器
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Slf4j
@RestController
@RequestMapping("/ask/file")
public class AskFileController {

	private static final String Q_IMAGE_DIR = "ask-question-images";
	private static final String A_IMAGE_DIR = "ask-answer-images";

	@Autowired
	private OssUtil ossUtil;

	/**
	 * 返回的数据格式定义如下： { // errno 即错误代码，0 表示没有错误。 // 如果有错误，errno != 0， //
	 * data是一个数组，返回若干图片的线上地址 data: [ '图片1地址', '图片2地址', '……' ] }
	 * 
	 * @param file 文件
	 * @param ops 操作，question：问题中包含的图片，answer：回答中包含的图片
	 * @return
	 */
	@PostMapping("/upload/image/{ops}")
	public Map<String, Object> uploadPoster(@RequestParam("file") MultipartFile file, @PathVariable("ops") String ops) {

		String objectKey = "";
		if ("question".equalsIgnoreCase(ops)) {
			objectKey = genObjectKey(Q_IMAGE_DIR, file.getOriginalFilename());
		}
		if ("answer".equalsIgnoreCase(ops)) {
			objectKey = genObjectKey(A_IMAGE_DIR, file.getOriginalFilename());
		}

		Map<String, Object> resp = new HashMap<>(16);
		try {
			log.info("Upload image, fileName={}, fileSize={}, objectKey={}", file.getOriginalFilename(),
					file.getSize(), objectKey);
			// 上传到OSS
			String ossPath = ossUtil.uploadFile(file, objectKey);

			resp.put("errno", 0);
			resp.put("data", Arrays.asList(ossPath));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed to upload image, name={}, contentType={}, size={}, e={}",
					file.getOriginalFilename(), file.getContentType(), file.getSize(), e);
			resp.put("errno", -1);
			resp.put("data", Arrays.asList(objectKey));
		}

		return resp;
	}

	private String genObjectKey(String dir, String originalFilename) {
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		// objectKey = md5(上传时间-文件名称).文件类型
		String objectKey = dir + "/" + DigestUtils
				.md5Hex(DateUtil.toDateTime(new Date(), DateUtil.DATE_TIME_FORMAT) + "-" + originalFilename) + suffix;

		return objectKey;
	}

}
