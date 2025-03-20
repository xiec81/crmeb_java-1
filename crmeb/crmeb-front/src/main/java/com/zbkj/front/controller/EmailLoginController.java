package com.zbkj.front.controller;

import com.zbkj.common.request.LoginEmailRequest;
import com.zbkj.common.response.CommonResult;
import com.zbkj.common.response.LoginResponse;
import com.zbkj.front.service.LoginService;
import com.zbkj.service.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 邮箱注册登录控制器
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
@RestController
@RequestMapping("api/front")
@Api(tags = "用户 -- 邮箱注册登录")
public class EmailLoginController {
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private LoginService loginService;
    
    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @return 发送是否成功
     */
    @ApiOperation(value = "发送邮箱验证码")
    @RequestMapping(value = "/sendEmailCode", method = RequestMethod.POST)
    @ApiImplicitParam(name="email", value="邮箱", required = true)
    public CommonResult<Object> sendCode(@RequestParam String email) {
        if(emailService.sendEmailCode(email)) {
            return CommonResult.success("发送成功");
        } else {
            return CommonResult.failed("发送失败");
        }
    }
    
    /**
     * 邮箱验证码登录注册
     */
    @ApiOperation(value = "邮箱验证码登录注册")
    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    public CommonResult<LoginResponse> emailLogin(@RequestBody @Validated LoginEmailRequest loginRequest) {
        return CommonResult.success(loginService.emailLogin(loginRequest));
    }
} 