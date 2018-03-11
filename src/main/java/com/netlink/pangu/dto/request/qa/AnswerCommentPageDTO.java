/*  
 * Licensed under the Apache License, Version 2.0 (the "License");  
 *  you may not use this file except in compliance with the License.  
 *  You may obtain a copy of the License at  
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0  
 *  
 *  Unless required by applicable law or agreed to in writing, software  
 *  distributed under the License is distributed on an "AS IS" BASIS,  
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
 *  See the License for the specific language governing permissions and  
 *  limitations under the License.  
 */
package com.netlink.pangu.dto.request.qa;

import com.netlink.pangu.dto.request.PageDTO;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * AnswerCommentPageDTO.
 *
 * @author fubencheng.
 * @version 0.0.1 2018-03-11 21:55 fubencheng.
 */
@Data
@ToString
public class AnswerCommentPageDTO extends PageDTO {

    private static final long serialVersionUID = -7540352280655322581L;

    /**
     * 回答主键ID
     */
    @NotNull
    private Long answerId;
}
