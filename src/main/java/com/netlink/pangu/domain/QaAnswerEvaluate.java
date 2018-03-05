package com.netlink.pangu.domain;

import com.netlink.pangu.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * QaAnswerEvaluate
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "qa_answer_evaluate")
public class QaAnswerEvaluate extends BaseDO {
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
}