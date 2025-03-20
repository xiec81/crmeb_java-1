package com.zbkj.service.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.zbkj.common.constants.SmsConstants;
import com.zbkj.common.exception.CrmebException;
import com.zbkj.common.utils.RedisUtil;
import com.zbkj.service.service.EmailService;
import com.zbkj.service.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件服务实现类
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
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 发送邮箱验证码
     * @param email 接收邮箱
     * @return Boolean
     */
    @Override
    public Boolean sendEmailCode(String email) {
        // 生成6位随机验证码
        String code = RandomUtil.randomNumbers(6);
        
        try {
            if (mailSender == null) {
                log.error("邮件发送器未配置");
                // 模拟发送成功，便于测试
                redisUtil.set(SmsConstants.SMS_VALIDATE_EMAIL + email, code, 300L);
                return true;
            }
            
            // 从系统配置中获取发件人邮箱
            String fromEmail = systemConfigService.getValueByKey("email_from");
            if (StrUtil.isBlank(fromEmail)) {
                fromEmail = "system@crmeb.com"; // 默认发件人
            }
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject(SmsConstants.EMAIL_VERIFY_SUBJECT);
            message.setText(StrUtil.format(SmsConstants.EMAIL_VERIFY_CONTENT, code));
            
            mailSender.send(message);
            //mailSender.send
            
            // 将验证码存入Redis，有效期5分钟
            redisUtil.set(SmsConstants.SMS_VALIDATE_EMAIL + email, code, 300L);
            
            return true;
        } catch (Exception e) {
            log.error("发送邮箱验证码异常：{}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 检查邮箱验证码
     * @param email 邮箱
     * @param code 验证码
     */
    @Override
    public void checkValidateEmailCode(String email, String code) {
        Object validateCode = redisUtil.get(SmsConstants.SMS_VALIDATE_EMAIL + email);
        if (ObjectUtil.isNull(validateCode)) {
            throw new CrmebException("验证码已过期");
        }
        if (!validateCode.toString().equals(code)) {
            throw new CrmebException("验证码错误");
        }
        //删除验证码
        redisUtil.delete(SmsConstants.SMS_VALIDATE_EMAIL + email);
    }
} 