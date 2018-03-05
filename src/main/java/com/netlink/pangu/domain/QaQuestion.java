package com.netlink.pangu.domain;

import com.netlink.pangu.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * QaQuestion
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "qa_question")
public class QaQuestion extends BaseDO {
    /**
     * 问题分类ID
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 分类名称
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 提问人ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 提问人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 浏览数
     */
    private Long views;

    /**
     * 回答数
     */
    private Long answers;

    /**
     * 点赞数
     */
    @Column(name = "thumb_up")
    private Long thumbUp;

    /**
     * 踩数
     */
    @Column(name = "thumb_down")
    private Long thumbDown;

    /**
     * 问题内容
     */
    private String question;
}