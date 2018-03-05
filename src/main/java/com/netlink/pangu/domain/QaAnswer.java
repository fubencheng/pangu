package com.netlink.pangu.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "qa_answer")
public class QaAnswer {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 问题ID
     */
    @Column(name = "question_id")
    private Long questionId;

    /**
     * 回答人ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 回答人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 评论数
     */
    private Long comments;

    /**
     * 赞
     */
    private Long likes;

    /**
     * 踩
     */
    private Long dislikes;

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
     * 回答内容
     */
    private String answer;
}