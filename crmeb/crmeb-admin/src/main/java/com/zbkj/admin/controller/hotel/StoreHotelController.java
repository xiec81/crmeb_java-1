package com.zbkj.admin.controller.hotel;

import com.zbkj.common.model.hotel.StoreHotel;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.CommonResult;
import com.zbkj.service.service.StoreHotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 酒店管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/hotel")
@Api(tags = "酒店管理")
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

    @PostMapping("/save")
    @ApiOperation(value = "添加酒店")
    public CommonResult save(@RequestBody StoreHotel storeHotel) {
        return storeHotelService.saveHotel(storeHotel);
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新酒店")
    public CommonResult update(@RequestBody StoreHotel storeHotel) {
        return storeHotelService.update(storeHotel);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除酒店")
    public CommonResult delete(@PathVariable Integer id) {
        return storeHotelService.delete(id);
    }
} 