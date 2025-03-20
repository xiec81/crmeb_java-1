package com.zbkj.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbkj.common.model.hotel.StoreHotel;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.CommonResult;

/**
 * 酒店服务接口
 */
public interface StoreHotelService extends IService<StoreHotel> {

    /**
     * 获取酒店列表
     * @param pageParamRequest 分页参数
     * @return 酒店列表
     */
    CommonResult getList(PageParamRequest pageParamRequest);

    /**
     * 获取酒店详情
     * @param id 酒店ID
     * @return 酒店详情
     */
    CommonResult getDetail(Integer id);

    /**
     * 添加酒店
     * @param storeHotel 酒店信息
     * @return 添加结果
     */
    CommonResult saveHotel(StoreHotel storeHotel);

    /**
     * 更新酒店
     * @param storeHotel 酒店信息
     * @return 更新结果
     */
    CommonResult update(StoreHotel storeHotel);

    /**
     * 删除酒店
     * @param id 酒店ID
     * @return 删除结果
     */
    CommonResult delete(Integer id);
} 