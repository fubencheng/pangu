package com.netlink.pangu.domain;

import com.netlink.pangu.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * QaAnswerComment
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "qa_answer_comment")
public class QaAnswerComment extends BaseDO {
    /**
     * 回答ID
     */
    @Column(name = "answer_id")
    private Long answerId;

    /**
     * 评论人ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 评论人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 被回复评论ID
     */
    @Column(name = "reply_to_comment_id")
    private Long replyToCommentId;

    /**
     * 被回复用户ID
     */
    @Column(name = "reply_to_user_id")
    private String replyToUserId;

    /**
     * 被回复用户名
     */
    @Column(name = "reply_to_user_name")
    private String replyToUserName;

    /**
     * 评论
     */
    private String comment;
}