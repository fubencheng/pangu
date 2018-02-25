///*
// * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
// * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
// * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
// * License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
// * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
// * specific language governing permissions and limitations under the License.
// */
//package com.netlink.pangu.service.qa;
//
//import java.util.Date;
//import java.util.Map;
//
//import com.netlink.pangu.dao.qa.QaAnswerDAO;
//import com.netlink.pangu.dao.qa.QaAnswerCommentDAO;
//import com.netlink.pangu.entity.qa.QaAnswerCommentDO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//
//import tk.mybatis.mapper.entity.Example;
//
///**
// * 评论服务实现类
// *
// * @author fubencheng
// * @version v0.1 2017-11-30 20:45 fubencheng Exp $
// */
//@Service("commentService")
//public class CommentServiceImpl implements CommentService {
//
//	@Autowired
//	private QaAnswerCommentDAO askCommentDAO;
//
//	@Autowired
//	private QaAnswerDAO answerDAO;
//
//	@Override
//	public void saveComment(QaAnswerCommentDO comment) {
//		if (comment.getReplyToCommentId() != null) {
//			QaAnswerCommentDO originComment = askCommentDAO.selectByPrimaryKey(comment.getReplyToCommentId());
//			comment.setReplyToUserId(originComment.getUserId());
//			comment.setReplyToUserName(originComment.getUserName());
//		}
//		comment.setGmtCreated(new Date());
//		askCommentDAO.insertSelective(comment);
//
//		answerDAO.updateComments(comment.getAnswerId());
//	}
//
//	@Override
//	public Page<QaAnswerCommentDO> pageCommentByCondition(Map<String, Object> condition) {
//		Example cond = new Example(QaAnswerCommentDO.class);
//		cond.createCriteria().andEqualTo("answerId", (Long) condition.get("answerId"));
//		cond.setOrderByClause("gmt_created desc");
//
//		int pageNum = (int) condition.get("pageNum");
//		int pageSize = (int) condition.get("pageSize");
//		PageHelper.startPage(pageNum, pageSize, true);
//
//		return (Page<QaAnswerCommentDO>) askCommentDAO.selectByExample(cond);
//	}
//
//}
///*
// * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
// * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
// * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
// * License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
// * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
// * specific language governing permissions and limitations under the License.
// */
//package com.netlink.pangu.service.qa;
//
//import java.util.Date;
//import java.util.Map;
//
//import com.netlink.pangu.dao.qa.QaAnswerDAO;
//import com.netlink.pangu.dao.qa.QaAnswerCommentDAO;
//import com.netlink.pangu.entity.qa.QaAnswerCommentDO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//
//import tk.mybatis.mapper.entity.Example;
//
///**
// * 评论服务实现类
// *
// * @author fubencheng
// * @version v0.1 2017-11-30 20:45 fubencheng Exp $
// */
//@Service("commentService")
//public class CommentServiceImpl implements CommentService {
//
//	@Autowired
//	private QaAnswerCommentDAO askCommentDAO;
//
//	@Autowired
//	private QaAnswerDAO answerDAO;
//
//	@Override
//	public void saveComment(QaAnswerCommentDO comment) {
//		if (comment.getReplyToCommentId() != null) {
//			QaAnswerCommentDO originComment = askCommentDAO.selectByPrimaryKey(comment.getReplyToCommentId());
//			comment.setReplyToUserId(originComment.getUserId());
//			comment.setReplyToUserName(originComment.getUserName());
//		}
//		comment.setGmtCreated(new Date());
//		askCommentDAO.insertSelective(comment);
//
//		answerDAO.updateComments(comment.getAnswerId());
//	}
//
//	@Override
//	public Page<QaAnswerCommentDO> pageCommentByCondition(Map<String, Object> condition) {
//		Example cond = new Example(QaAnswerCommentDO.class);
//		cond.createCriteria().andEqualTo("answerId", (Long) condition.get("answerId"));
//		cond.setOrderByClause("gmt_created desc");
//
//		int pageNum = (int) condition.get("pageNum");
//		int pageSize = (int) condition.get("pageSize");
//		PageHelper.startPage(pageNum, pageSize, true);
//
//		return (Page<QaAnswerCommentDO>) askCommentDAO.selectByExample(cond);
//	}
//
//}
