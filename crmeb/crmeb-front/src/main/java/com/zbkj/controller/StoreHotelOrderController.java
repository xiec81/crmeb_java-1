package com.zbkj.controller;

import com.zbkj.common.response.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/front/hotel/order")
@Api(tags = "酒店订单接口")
public class StoreHotelOrderController {

    @PostMapping("/create")
    @ApiOperation(value = "创建订单")
    public CommonResult create() {
        // TODO: 实现创建订单逻辑
        return CommonResult.success();
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "订单详情")
    public CommonResult detail(@PathVariable Integer id) {
        // TODO: 实现订单详情逻辑
        return CommonResult.success();
    }
} 