package com.netlink.pangu.dao;

import com.netlink.pangu.base.BaseMapper;
import com.netlink.pangu.domain.QaQuestion;

/**
 * QaQuestionMapper
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
public interface QaQuestionMapper extends BaseMapper<QaQuestion> {

    /**
     * 累加赞
     * @param questionId questionId
     * @return result
     */
    int updateThumbUp(Long questionId);

    /**
     * 累加踩
     * @param questionId questionId
     * @return result
     */
    int updateThumbDown(Long questionId);

    /**
     * 累加读
     * @param questionId questionId
     * @return result
     */
    int updateViews(Long questionId);

}