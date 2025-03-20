package com.zbkj.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbkj.common.model.hotel.StoreHotelRoom;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.CommonResult;

/**
 * 酒店房型服务接口
 */
public interface StoreHotelRoomService extends IService<StoreHotelRoom> {

    /**
     * 获取房型列表
     * @param hotelId 酒店ID
     * @param pageParamRequest 分页参数
     * @return 房型列表
     */
    CommonResult getList(Integer hotelId, PageParamRequest pageParamRequest);

    /**
     * 获取房型详情
     * @param id 房型ID
     * @return 房型详情
     */
    CommonResult getDetail(Integer id);

    /**
     * 添加房型
     * @param storeHotelRoom 房型信息
     * @return 添加结果
     */
    CommonResult saveRoom(StoreHotelRoom storeHotelRoom);

    /**
     * 更新房型
     * @param storeHotelRoom 房型信息
     * @return 更新结果
     */
    CommonResult update(StoreHotelRoom storeHotelRoom);

    /**
     * 删除房型
     * @param id 房型ID
     * @return 删除结果
     */
    CommonResult delete(Integer id);
} 