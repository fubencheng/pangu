package com.netlink.pangu.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "qa_answer_evaluate")
public class QaAnswerEvaluate {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 回答ID
     */
    @Column(name = "answer_id")
    private Long answerId;

    /**
     * -1:踩,1:赞
     */
    private Byte evaluate;

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
}