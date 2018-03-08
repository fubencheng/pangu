/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netlink.pangu.service.qa;

import com.github.pagehelper.Page;
import com.netlink.pangu.dao.QaAnswerMapper;
import com.netlink.pangu.domain.QaAnswer;
import com.netlink.pangu.dto.request.qa.AnswerPageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Condition;

/**
 * QaAnswerServiceImpl
 *
 * @author fubencheng
 * @version 0.0.1 2017-11-30 20:45 fubencheng
 */
@Service("answerService")
public class QaAnswerServiceImpl implements QaAnswerService {

	private QaAnswerMapper answerMapper;

	@Autowired
	public QaAnswerServiceImpl(QaAnswerMapper answerMapper){
	    this.answerMapper = answerMapper;
    }

//	@Autowired
//	private QaQuestionDAO questionDAO;

//	@Override
//	public void saveAnswer(QaAnswerDO answer) {
//		answer.setGmtCreated(new Date());
//		answerDAO.insertSelective(answer);
//		questionDAO.updateAnswers(answer.getQuestionId());
//	}
//
//	@Override
//	public void signAnswer(Long id, Integer eventType) {
//		if (EventTypeEnum.THUMB_UP.getEventCode() == eventType) {
//			answerDAO.updateLikes(id);
//		}
//		if (EventTypeEnum.THUMB_DOWN.getEventCode() == eventType) {
//			answerDAO.updateDislikes(id);
//		}
//	}

	@Override
	public Page<QaAnswer> pageByCondition(AnswerPageDTO pageDTO) {
		Condition condition = new Condition(QaAnswer.class);
		Condition.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("questionId", pageDTO.getQuestionId());
		String orderByClause = generateOrderByClause(pageDTO);
		condition.setOrderByClause(orderByClause);

		PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize(), true);

		return (Page<QaAnswer>) answerMapper.selectByCondition(condition);
	}

	private String generateOrderByClause(AnswerPageDTO pageDTO){
        StringBuilder orderBuilder = new StringBuilder();
        if (pageDTO.getOrderIndex() != null) {
            switch (pageDTO.getOrderIndex()){
                case 1 :
                    orderBuilder.append(" gmt_created desc ").append(" ");
                    break;
                case 2 :
                    orderBuilder.append(" comments desc ").append(" ");
                    break;
                case 3 :
                    orderBuilder.append(" likes desc ").append(" ");
                    break;
                default:
                    orderBuilder.append(" gmt_created desc ").append(" ");
            }
        } else {
            orderBuilder.append(" gmt_created desc ").append(" ");
        }
        return orderBuilder.toString();
    }

}
