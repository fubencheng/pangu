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

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.Page;

import com.netlink.pangu.ask.model.AskCommentDO;
import com.netlink.pangu.ask.response.CommentPageResponse;
import com.netlink.pangu.ask.response.dto.CommentDTO;
import com.netlink.pangu.ask.service.CommentService;
import com.netlink.pangu.user.SessionUser;
import com.netlink.pangu.auth.util.SessionUserUtil;
import com.netlink.pangu.common.BaseResponse;
import com.netlink.pangu.common.RespCodeEnum;
import com.netlink.pangu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回答评论控制器
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Slf4j
@RestController
@RequestMapping("/ask/comment")
public class AskCommentController {

	private CommentService commentService;
	private UserService userService;

	@Autowired
	public AskCommentController(CommentService commentService, UserService userService){
		this.commentService = commentService;
		this.userService = userService;
	}

	@PostMapping("/save")
	public BaseResponse saveComment(HttpServletRequest request, @RequestBody AskCommentDO comment) {
		SessionUser sessionUser = SessionUserUtil.getCurrentUser(request);
		if(sessionUser == null){
			sessionUser= userService.getUser(request);
		}
		String userNo = sessionUser.getStaffNo();
		String userName = sessionUser.getName();

		AskCommentDO commentModel = new AskCommentDO();
		commentModel.setUserId(userNo);
		commentModel.setUserName(userName);
		commentModel.setAnswerId(comment.getAnswerId());
		commentModel.setComment(comment.getComment());
		commentModel.setReplyToCommentId(comment.getReplyToCommentId());
		try {
			commentService.saveComment(commentModel);
			return new BaseResponse(RespCodeEnum.SUCCESS.getCode(), RespCodeEnum.SUCCESS.getMessage());
		} catch (Exception e) {
			log.error("Failed to save comment, userName={}, answerId={}, replyToCommentId={}, e={}",
					userName, comment.getAnswerId(), comment.getReplyToCommentId(), e);
			return new BaseResponse(RespCodeEnum.FAIL.getCode(), RespCodeEnum.FAIL.getMessage());
		}
	}

	@GetMapping("/list")
	public CommentPageResponse getCommentList(@RequestParam("answerId") Long answerId,
											  @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
		Map<String, Object> condition = new HashMap<>(8);
		condition.put("answerId", answerId);
		condition.put("pageNum", pageNum);
		condition.put("pageSize", pageSize);
		Page<AskCommentDO> commentList = commentService.pageCommentByCondition(condition);
		CommentPageResponse pageResponse = new CommentPageResponse();
		pageResponse.setPageNum(pageNum);
		pageResponse.setPageSize(commentList.getPageSize());
		pageResponse.setTotal(commentList.getTotal());
		List<CommentDTO> commentDTOList = new ArrayList<>();
		for (AskCommentDO comment : commentList) {
			CommentDTO commentDTO = mapperFacade.map(comment, CommentDTO.class);
			commentDTOList.add(commentDTO);
		}
		pageResponse.setCommentList(commentDTOList);
		return pageResponse;
	}

}
