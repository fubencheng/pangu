package com.netlink.pangu.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "qa_comment")
public class QaComment {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

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
     * 是否逻辑删除
     */
    @Column(name = "is_delete")
    private String isDelete;

    /**
     * 创建时间
     */
    @Column(name = "gmt_created")
    private Date gmtCreated;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 评论
     */
    private String comment;
}