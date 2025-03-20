package com.zbkj.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbkj.common.model.hotel.StoreHotel;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.CommonResult;
import com.zbkj.service.dao.StoreHotelDao;
import com.zbkj.service.service.StoreHotelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 酒店服务实现类
 */
@Service
public class StoreHotelServiceImpl extends ServiceImpl<StoreHotelDao, StoreHotel> implements StoreHotelService {

    @Override
    public CommonResult getList(PageParamRequest pageParamRequest) {
        LambdaQueryWrapper<StoreHotel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StoreHotel::getIsDel, false);
        queryWrapper.orderByDesc(StoreHotel::getId);
        
        List<StoreHotel> list = list(queryWrapper);
        return CommonResult.success(list);
    }

    @Override
    public CommonResult getDetail(Integer id) {
        StoreHotel hotel = getById(id);
        if (hotel == null) {
            return CommonResult.failed("酒店不存在");
        }
        return CommonResult.success(hotel);
    }

    @Override
    public CommonResult saveHotel(StoreHotel storeHotel) {
        if (storeHotel == null) {
            return CommonResult.failed("参数错误");
        }
        boolean result = super.save(storeHotel);
        return result ? CommonResult.success() : CommonResult.failed();
    }

    @Override
    public CommonResult update(StoreHotel storeHotel) {
        if (storeHotel == null || storeHotel.getId() == null) {
            return CommonResult.failed("参数错误");
        }
        boolean result = updateById(storeHotel);
        return result ? CommonResult.success() : CommonResult.failed();
    }

    @Override
    public CommonResult delete(Integer id) {
        if (id == null) {
            return CommonResult.failed("参数错误");
        }
        StoreHotel hotel = new StoreHotel();
        hotel.setId(id);
        hotel.setIsDel(true);
        boolean result = updateById(hotel);
        return result ? CommonResult.success() : CommonResult.failed();
    }
} 