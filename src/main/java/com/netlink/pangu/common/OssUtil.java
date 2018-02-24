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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.openservices.ClientConfiguration;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;

/**
 * aliyun oss 文件工具类
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Slf4j
@Data
public class OssUtil {

	@Resource
	private ClientConfiguration printOssClientConfig;

	private OSSClient client;

	private String accessId;

	private String accessKey;

	private String endpoint;

	private String bucketName;

	private String publicEndpoint;

	private OSSClient getClient() {
		if (client == null) {
			if (StringUtils.isNotEmpty(accessId) && StringUtils.isNotEmpty(accessKey)) {
				log.debug("endpoint[" + endpoint + "], accessId[" + accessId + "], accessKey[" + accessKey + "]");
				client = new OSSClient(endpoint, accessId, accessKey, printOssClientConfig);

			}
		}
		log.debug("OSS getClient:" + client);
		return client;
	}

	/**
	 * 如果Bucket不存在，则创建它。
	 */
	public void createBucket() {
		if (!getClient().doesBucketExist(bucketName)) {
			// 创建bucket
			getClient().createBucket(bucketName);
		}
	}

	public void queryBucket() {
		ObjectListing objectListing = getClient().listObjects(bucketName);
		List<OSSObjectSummary> listDeletes = objectListing.getObjectSummaries();
		for (int i = 0; i < listDeletes.size(); i++) {
			String objectName = listDeletes.get(i).getKey();
			log.debug("OSS hasObjects:" + objectName);
		}
	}

	/**
	 * 删除一个Bucket和其中的Objects
	 */
	public void deleteBucket() {
		ObjectListing objectListing = getClient().listObjects(bucketName);
		List<OSSObjectSummary> listDeletes = objectListing.getObjectSummaries();
		for (int i = 0; i < listDeletes.size(); i++) {
			String objectName = listDeletes.get(i).getKey();
			// 如果不为空，先删除bucket下的文件
			getClient().deleteObject(bucketName, objectName);
		}
		getClient().deleteBucket(bucketName);
	}

	/**
	 * 把Bucket设置为所有人可读
	 */
	public void setBucketPublicReadable() {
		// 设置bucket的访问权限，public-read-write权限
		getClient().setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
	}

	/**
	 * 上传文件到OSS
	 *
	 * @param file 文件
	 * @param objectKey 文件对象Key
	 * @return
	 */
	public String uploadFile(MultipartFile file, String objectKey) {
		try {
			log.info("OSS uploadFile params:fileName[" + file.getOriginalFilename() + "], size[" + file.getSize() + "], objectkey[" + objectKey + "]");
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(file.getSize());
			getClient().putObject(bucketName, objectKey, file.getInputStream(), objectMeta);
		} catch (IOException e) {
			throw new RuntimeException("File upload to OSS failed, fileName is " + objectKey, e);
		}
		return "http://" + bucketName + "." + endpoint.substring(7, endpoint.length()) + "/" + objectKey;
	}

	/**
	 * 上传文件至OSS
	 *
	 * @param in 文件输入流
	 * @param objectKey 文件对象Key
	 * @return
	 */
	public String uploadFile(InputStream in, long size, String objectKey) {
		log.info("OSS uploadFile params:in[" + in + "], size[" + size + "], objectkey[" + objectKey + "]");
		try {
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(size);
			getClient().putObject(bucketName, objectKey, in, objectMeta);
		} catch (Exception e) {
			throw new RuntimeException("Upload file to OSS failed", e);
		}
		return "http://" + bucketName + "." + endpoint.substring(7, endpoint.length()) + "/" + objectKey;
	}

	/**
	 * 上传文件至OSS
	 *
	 * @param in 文件输入流
	 * @param objectKey 文件对象Key
	 * @return
	 */
	public String uploadFile(InputStream in, String objectKey) {
		try {
			int size = in.available();
			return uploadFile(in, size, objectKey);
		} catch (IOException e) {
			throw new RuntimeException("Unable to estimate the number of bytes that can be read from input stream");
		}
	}

	/**
	 * 从OSS下载文件
	 *
	 * @param objectkey 文件对象Key
	 * @param fileName 文件名
	 */
	public void downloadFile(String objectkey, String fileName) {
		getClient().getObject(new GetObjectRequest(bucketName, objectkey), new File(fileName));
	}

	/**
	 * 从OSS下载内容到指定文件
	 *
	 * @param objectkey 文件对象Key
	 * @param file 文件
	 */
	public void downloadFile(String objectkey, File file) {
		getClient().getObject(new GetObjectRequest(bucketName, objectkey), file);
	}

	/**
	 * 从OSS取得对应的File object
	 *
	 * @param objectKey 文件对象Key
	 */
	public OSSObject getFileObject(String objectKey) {
		return getClient().getObject(new GetObjectRequest(bucketName, objectKey));
	}

	/**
	 * 生成一个用HTTP GET方法访问OSSObject的URL
	 *
	 * @param objectKey 文件对象Key
	 * @return 文件下载URL
	 */
	public URL getDownloadURL(String objectKey) {
		return getClient().generatePresignedUrl(bucketName, objectKey, new Date());
	}

	/**
	 * 从OSS删除文件
	 *
	 * @param objectKey 文件对象Key
	 */
	public void deleteFile(String objectKey) {
		getClient().deleteObject(bucketName, objectKey);
	}

}
