package com.netlink.pangu.dao;

import com.netlink.pangu.base.BaseMapper;
import com.netlink.pangu.domain.QaAnswer;

/**
 * QaAnswerMapper
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
public interface QaAnswerMapper extends BaseMapper<QaAnswer> {

    /**
     * 累计喜欢数
     * @param answerId answerId
     * @return Result
     */
    int updateLikes(Long answerId);

    /**
     * 累计不喜欢数
     * @param answerId answerId
     * @return Result
     */
    int updateDislikes(Long answerId);

    /**
     * 累计评论数
     * @param answerId answerId
     * @return Result
     */
    int updateComments(Long answerId);

}