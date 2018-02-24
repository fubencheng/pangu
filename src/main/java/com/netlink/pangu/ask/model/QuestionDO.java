package com.netlink.pangu.ask.model;

import com.netlink.pangu.common.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 问题映射实体
 *
 * @author fubencheng
 * @version v0.1 2017-11-30 20:45 fubencheng Exp $
 */
@Data
@Table(name = "ask_question")
@EqualsAndHashCode(callSuper = true)
public class QuestionDO  extends BaseDO {

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "category_name")
	private String categoryName;

	private String title;

	private String question;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_name")
	private String userName;

	private Long views;

	private Long answers;

	@Column(name = "thumb_up")
	private Long thumbUp;

	@Column(name = "thumb_down")
	private Long thumbDown;

}
