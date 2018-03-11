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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.netlink.pangu.util.DateUtil;
import com.netlink.pangu.util.OssUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * QaFileController.
 *
 * @author fubencheng.
 * @version 0.0.1 2017-11-30 20:45 fubencheng.
 */
@Slf4j
@RestController
@RequestMapping("/qa/upload")
public class QaFileController {

	private static final String Q_IMAGE_DIR = "qa-question-images";
	private static final String A_IMAGE_DIR = "qa-answer-images";

//	@Resource
//	private OssUtil ossUtil;

	/**
	 * 返回的数据格式定义如下：{ errno 即错误代码，0 表示没有错误，如果有错误，errno != 0，
	 * data是一个数组，返回若干图片的线上地址 data: [ '图片1地址', '图片2地址', '……' ] }
	 *
	 * @param file file
	 * @return Map<String, Object>
	 */
	@PostMapping("/question/image")
	public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file) {
        log.info("Upload image, fileName={}, fileSize={}", file.getOriginalFilename(), file.getSize());

        String objectKey = genObjectKey(Q_IMAGE_DIR, file.getOriginalFilename());
//		if (A == ops) {
//		    // 提交回答图片
//			objectKey = genObjectKey(A_IMAGE_DIR, file.getOriginalFilename());
//		}

		log.info("objectKey={}", objectKey);

		Map<String, Object> resp = new HashMap<>(16);
		try {
//			// 上传到OSS
//			String ossPath = ossUtil.uploadFile(file, objectKey);
//
//			resp.put("errno", 0);
//			resp.put("data", ossPath);
		} catch (Exception e) {
			log.error("upload qa image failed, name={}, contentType={}, size={}",
					file.getOriginalFilename(), file.getContentType(), file.getSize(), e);
			resp.put("errno", -1);
		}
		return resp;
	}

	private String genObjectKey(String dir, String originalFilename) {
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		// objectKey = md5(上传时间-文件名称).文件类型
		return dir + "/" + DigestUtils
				.md5Hex(DateUtil.toDateTime(new Date(), DateUtil.DATE_TIME_FORMAT) + "-" + originalFilename) + suffix;
	}

}
