package com.zbkj.front.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zbkj.common.constants.Constants;
import com.zbkj.common.constants.SmsConstants;
import com.zbkj.common.exception.CrmebException;
import com.zbkj.common.model.user.User;
import com.zbkj.common.request.LoginEmailRequest;
import com.zbkj.common.request.LoginMobileRequest;
import com.zbkj.common.request.LoginRequest;
import com.zbkj.common.response.LoginResponse;
import com.zbkj.common.token.FrontTokenComponent;
import com.zbkj.common.utils.CommonUtil;
import com.zbkj.common.utils.CrmebUtil;
import com.zbkj.common.utils.DateUtil;
import com.zbkj.common.utils.RedisUtil;
import com.zbkj.front.service.LoginService;
import com.zbkj.service.service.EmailService;
import com.zbkj.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * 移动端登录服务类
 * +----------------------------------------------------------------------
 * | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016~2022 https://www.crmeb.com All rights reserved.
 * +----------------------------------------------------------------------
 * | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 * +----------------------------------------------------------------------
 * | Author: CRMEB Team <admin@crmeb.com>
 * +----------------------------------------------------------------------
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FrontTokenComponent tokenComponent;
    
    @Autowired
    private EmailService emailService;

    /**
     * 账号密码登录
     *
     * @return LoginResponse
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userService.getByAccount(loginRequest.getAccount());

        if (ObjectUtil.isNull(user)) {
            throw new CrmebException("此账号未注册");
        }
        if (!user.getStatus()) {
            throw new CrmebException("此账号被禁用");
        }

        // 校验密码
        String password = CrmebUtil.encryptPassword(loginRequest.getPassword(), loginRequest.getAccount());
        if (!user.getPwd().equals(password)) {
            throw new CrmebException("密码错误");
        }

        LoginResponse loginResponse = new LoginResponse();
        String token = tokenComponent.createToken(user);
        loginResponse.setToken(token);

        //绑定推广关系
        if (loginRequest.getSpreadPid() > 0) {
            bindSpread(user, loginRequest.getSpreadPid());
        }

        // 记录最后一次登录时间
        user.setLastLoginTime(DateUtil.nowDateTime());
        userService.updateById(user);

        loginResponse.setUid(user.getUid());
        loginResponse.setNikeName(user.getNickname());
        loginResponse.setPhone(user.getPhone());
        return loginResponse;
    }

    /**
     * 手机号验证码登录
     *
     * @param loginRequest 登录请求信息
     * @return LoginResponse
     */
    @Override
    public LoginResponse phoneLogin(LoginMobileRequest loginRequest) {
        //检测验证码
        checkValidateCode(loginRequest.getPhone(), loginRequest.getCaptcha());
        Integer spreadPid = Optional.ofNullable(loginRequest.getSpreadPid()).orElse(0);
        //查询手机号信息
        User user = userService.getByPhone(loginRequest.getPhone());
        if (ObjectUtil.isNull(user)) {// 此用户不存在，走新用户注册流程
            user = userService.registerPhone(loginRequest.getPhone(), spreadPid);
        } else {
            if (!user.getStatus()) {
                throw new CrmebException("当前账户已禁用，请联系管理员！");
            }
            if (user.getSpreadUid().equals(0) && spreadPid > 0) {
                // 绑定推广关系
                bindSpread(user, spreadPid);
            }
            // 记录最后一次登录时间
            user.setLastLoginTime(DateUtil.nowDateTime());
            boolean b = userService.updateById(user);
            if (!b) {
                logger.error("用户登录时，记录最后一次登录时间出错,uid = " + user.getUid());
            }
        }

        //生成token
        LoginResponse loginResponse = new LoginResponse();
        String token = tokenComponent.createToken(user);
        loginResponse.setToken(token);
        loginResponse.setUid(user.getUid());
        loginResponse.setNikeName(user.getNickname());
        loginResponse.setPhone(user.getPhone());
        return loginResponse;
    }
    
    /**
     * 邮箱验证码登录
     *
     * @param loginRequest 登录请求信息
     * @return LoginResponse
     */
    @Override
    public LoginResponse emailLogin(LoginEmailRequest loginRequest) {
        //检测验证码
        //emailService.checkValidateEmailCode(loginRequest.getEmail(), loginRequest.getCaptcha());
        
        // 处理推广人ID，如果有推荐码则通过推荐码获取推广人ID
        Integer spreadPid = Optional.ofNullable(loginRequest.getSpreadPid()).orElse(0);
        if (spreadPid == 0 && StrUtil.isNotBlank(loginRequest.getInviteCode())) {
            User spreadUser = userService.getBySpreadCode(loginRequest.getInviteCode());
            if (ObjectUtil.isNull(spreadUser)) {
                throw new CrmebException("推荐码不存在");
            }
            spreadPid = spreadUser.getUid();
        }
        
        // 查询是否已存在邮箱用户
        User user = userService.getByEmail(loginRequest.getEmail());
        if (ObjectUtil.isNull(user)) {// 此用户不存在，走新用户注册流程
            user = registerByEmail(loginRequest.getEmail(), loginRequest.getPassword(), spreadPid);
        } else {
            if (!user.getStatus()) {
                throw new CrmebException("当前账户已禁用，请联系管理员！");
            }
            if (user.getSpreadUid().equals(0) && spreadPid > 0) {
                // 绑定推广关系
                bindSpread(user, spreadPid);
            }
            // 记录最后一次登录时间
            user.setLastLoginTime(DateUtil.nowDateTime());
            boolean b = userService.updateById(user);
            if (!b) {
                logger.error("用户登录时，记录最后一次登录时间出错,uid = " + user.getUid());
            }
        }

        //生成token
        LoginResponse loginResponse = new LoginResponse();
        String token = tokenComponent.createToken(user);
        loginResponse.setToken(token);
        loginResponse.setUid(user.getUid());
        loginResponse.setNikeName(user.getNickname());
        loginResponse.setEmail(user.getEmail());
        return loginResponse;
    }
    
    /**
     * 使用邮箱注册用户
     * @param email 邮箱
     * @param password 密码
     * @param spreadUid 推广人ID
     * @return User
     */
    private User registerByEmail(String email, String password, Integer spreadUid) {
        User user = new User();
        user.setAccount(email);
        // 使用提供的密码而不是生成的密码
        user.setPwd(CrmebUtil.encryptPassword(password, email));
        user.setEmail(email);
        user.setUserType(Constants.USER_LOGIN_TYPE_H5);
        user.setNickname(CommonUtil.createNickName(email));
        user.setAvatar(userService.getDefaultAvatar());
        Date nowDate = DateUtil.nowDateTime();
        user.setCreateTime(nowDate);
        user.setLastLoginTime(nowDate);

        // 推广人
        user.setSpreadUid(0);
        if (spreadUid > 0) {
            Boolean check = userService.checkBingSpread(user, spreadUid, "new");
            if (check) {
                user.setSpreadUid(spreadUid);
                user.setSpreadTime(nowDate);
            } else {
                throw new CrmebException("绑定推广人失败");
            }
        }

        boolean save = userService.save(user);
        if (!save) {
            throw new CrmebException("用户注册失败");
        }
        
        // 新用户注册成功后，为推广人增加推广人数
        if (user.getSpreadUid() > 0) {
            userService.updateSpreadCountByUid(spreadUid, "add");
        }
        
        return user;
    }

    /**
     * 检测手机验证码
     *
     * @param phone 手机号
     * @param code  验证码
     */
    private void checkValidateCode(String phone, String code) {
        Object validateCode = redisUtil.get(SmsConstants.SMS_VALIDATE_PHONE + phone);
        if (ObjectUtil.isNull(validateCode)) {
            throw new CrmebException("验证码已过期");
        }
        if (!validateCode.toString().equals(code)) {
            throw new CrmebException("验证码错误");
        }
        //删除验证码
        redisUtil.delete(SmsConstants.SMS_VALIDATE_PHONE + phone);
    }

    /**
     * 绑定分销关系
     *
     * @param user      User 用户user类
     * @param spreadUid Integer 推广人id
     * @return Boolean
     * 1.判断分销功能是否启用
     * 2.判断分销模式
     * 3.根据不同的分销模式校验
     * 4.指定分销，只有分销员才可以分销，需要spreadUid是推广员才可以绑定
     * 5.满额分销，同上
     * 6.人人分销，可以直接绑定
     */
    @Override
    public Boolean bindSpread(User user, Integer spreadUid) {
        Boolean checkBingSpread = userService.checkBingSpread(user, spreadUid, "old");
        if (!checkBingSpread) return false;

        user.setSpreadUid(spreadUid);
        user.setSpreadTime(DateUtil.nowDateTime());

        Boolean execute = transactionTemplate.execute(e -> {
            userService.updateById(user);
            userService.updateSpreadCountByUid(spreadUid, "add");
            return Boolean.TRUE;
        });
        if (!execute) {
            logger.error(StrUtil.format("绑定推广人时出错，userUid = {}, spreadUid = {}", user.getUid(), spreadUid));
        }
        return execute;
    }

    /**
     * 推出登录
     * @param request HttpServletRequest
     */
    @Override
    public void loginOut(HttpServletRequest request) {
        tokenComponent.logout(request);
    }
}
