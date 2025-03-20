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
import java.util.Date;

/**
 * 酒店表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_hotel")
@ApiModel(value="StoreHotel", description="酒店表")
public class StoreHotel implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "酒店ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联商品ID")
    private Integer productId;

    @ApiModelProperty(value = "酒店名称")
    private String name;

    @ApiModelProperty(value = "酒店星级 1-5星")
    private Integer grade;

    @ApiModelProperty(value = "酒店地址")
    private String address;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "设施服务,JSON格式")
    private String facilities;

    @ApiModelProperty(value = "酒店介绍")
    private String description;

    @ApiModelProperty(value = "入住时间")
    private String checkInTime;

    @ApiModelProperty(value = "退房时间")
    private String checkOutTime;

    @ApiModelProperty(value = "是否显示")
    private Boolean isShow;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDel;

    @ApiModelProperty(value = "添加时间")
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
} 