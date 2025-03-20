package com.zbkj.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒店房型表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_hotel_room")
@ApiModel(value="StoreHotelRoom", description="酒店房型表")
public class StoreHotelRoom implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "房型ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "酒店ID")
    private Integer hotelId;

    @ApiModelProperty(value = "房型名称")
    private String name;

    @ApiModelProperty(value = "床型")
    private String bedType;

    @ApiModelProperty(value = "房间面积(平方米)")
    private Integer area;

    @ApiModelProperty(value = "是否有窗 0无 1有")
    private Boolean window;

    @ApiModelProperty(value = "含早餐数 0-4")
    private Integer breakfast;

    @ApiModelProperty(value = "是否含wifi 0否 1是")
    private Boolean wifi;

    @ApiModelProperty(value = "可住人数")
    private Integer peopleNum;

    @ApiModelProperty(value = "房型图片")
    private String images;

    @ApiModelProperty(value = "房间设施,JSON格式")
    private String facilities;

    @ApiModelProperty(value = "房型介绍")
    private String description;

    @ApiModelProperty(value = "房间价格")
    private BigDecimal price;

    @ApiModelProperty(value = "是否显示")
    private Boolean isShow;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDel;

    @ApiModelProperty(value = "添加时间")
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
} 