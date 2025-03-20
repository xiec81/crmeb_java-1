package com.zbkj.service.service;

/**
 * 邮件服务接口
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2022 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
public interface EmailService {

    /**
     * 发送邮箱验证码
     * @param email 接收邮箱
     * @return Boolean
     */
    Boolean sendEmailCode(String email);
    
    /**
     * 检查邮箱验证码
     * @param email 邮箱
     * @param code 验证码
     */
    void checkValidateEmailCode(String email, String code);
} 