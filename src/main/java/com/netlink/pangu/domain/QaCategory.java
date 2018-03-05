package com.netlink.pangu.domain;

import com.netlink.pangu.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * QaCategory
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "qa_category")
public class QaCategory extends BaseDO {
    /**
     * 分类名称
     */
    @Column(name = "category_name")
    private String categoryName;
}