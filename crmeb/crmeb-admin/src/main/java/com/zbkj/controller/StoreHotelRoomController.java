package com.zbkj.controller;

import com.zbkj.common.model.hotel.StoreHotelRoom;
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
@RequestMapping("/api/admin/hotel/room")
@Api(tags = "酒店房型管理")
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

    @PostMapping("/save")
    @ApiOperation(value = "保存房型")
    public CommonResult save(@RequestBody StoreHotelRoom storeHotelRoom) {
        return storeHotelRoomService.saveRoom(storeHotelRoom);
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新房型")
    public CommonResult update(@RequestBody StoreHotelRoom storeHotelRoom) {
        return storeHotelRoomService.update(storeHotelRoom);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除房型")
    public CommonResult delete(@PathVariable Integer id) {
        return storeHotelRoomService.delete(id);
    }
} 