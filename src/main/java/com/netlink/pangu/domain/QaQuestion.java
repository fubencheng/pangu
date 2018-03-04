package com.netlink.pangu.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "qa_question")
public class QaQuestion {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

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
     * 问题内容
     */
    private String question;
}