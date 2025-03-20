package com.zbkj.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbkj.common.model.hotel.StoreHotelRoom;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.CommonResult;
import com.zbkj.service.dao.StoreHotelRoomDao;
import com.zbkj.service.service.StoreHotelRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 酒店房型服务实现类
 */
@Service
public class StoreHotelRoomServiceImpl extends ServiceImpl<StoreHotelRoomDao, StoreHotelRoom> implements StoreHotelRoomService {

    @Override
    public CommonResult getList(Integer hotelId, PageParamRequest pageParamRequest) {
        LambdaQueryWrapper<StoreHotelRoom> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StoreHotelRoom::getHotelId, hotelId);
        queryWrapper.eq(StoreHotelRoom::getIsDel, false);
        queryWrapper.orderByDesc(StoreHotelRoom::getId);
        
        List<StoreHotelRoom> list = list(queryWrapper);
        return CommonResult.success(list);
    }

    @Override
    public CommonResult getDetail(Integer id) {
        StoreHotelRoom room = getById(id);
        if (room == null) {
            return CommonResult.failed("房型不存在");
        }
        return CommonResult.success(room);
    }

    @Override
    public CommonResult saveRoom(StoreHotelRoom storeHotelRoom) {
        if (storeHotelRoom == null) {
            return CommonResult.failed("参数错误");
        }
        boolean result = super.save(storeHotelRoom);
        return result ? CommonResult.success() : CommonResult.failed();
    }

    @Override
    public CommonResult update(StoreHotelRoom storeHotelRoom) {
        if (storeHotelRoom == null || storeHotelRoom.getId() == null) {
            return CommonResult.failed("参数错误");
        }
        boolean result = updateById(storeHotelRoom);
        return result ? CommonResult.success() : CommonResult.failed();
    }

    @Override
    public CommonResult delete(Integer id) {
        if (id == null) {
            return CommonResult.failed("参数错误");
        }
        StoreHotelRoom room = new StoreHotelRoom();
        room.setId(id);
        room.setIsDel(true);
        boolean result = updateById(room);
        return result ? CommonResult.success() : CommonResult.failed();
    }
} 