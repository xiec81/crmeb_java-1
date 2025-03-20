package com.zbkj.controller;

import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.CommonResult;
import com.zbkj.service.service.StoreHotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/front/hotel")
@Api(tags = "酒店接口")
public class StoreHotelController {

    @Autowired
    private StoreHotelService storeHotelService;

    @GetMapping("/list")
    @ApiOperation(value = "获取酒店列表")
    public CommonResult list(PageParamRequest pageParamRequest) {
        return storeHotelService.getList(pageParamRequest);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取酒店详情")
    public CommonResult detail(@PathVariable Integer id) {
        return storeHotelService.getDetail(id);
    }
} 