package com.netlink.pangu.domain;

import com.netlink.pangu.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * QaAnswer
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "qa_answer")
public class QaAnswer extends BaseDO {
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
     * 回答内容
     */
    private String answer;
}