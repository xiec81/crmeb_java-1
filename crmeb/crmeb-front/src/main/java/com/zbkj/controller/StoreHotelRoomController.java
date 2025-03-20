package com.zbkj.controller;

import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.CommonResult;
import com.zbkj.service.service.StoreHotelRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/front/hotel/room")
@Api(tags = "酒店房型接口")
public class StoreHotelRoomController {

    @Autowired
    private StoreHotelRoomService storeHotelRoomService;

    @GetMapping("/list")
    @ApiOperation(value = "获取房型列表")
    public CommonResult list(@RequestParam Integer hotelId, PageParamRequest pageParamRequest) {
        return storeHotelRoomService.getList(hotelId, pageParamRequest);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取房型详情")
    public CommonResult detail(@PathVariable Integer id) {
        return storeHotelRoomService.getDetail(id);
    }
} 