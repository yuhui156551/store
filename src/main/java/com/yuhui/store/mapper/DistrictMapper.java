package com.yuhui.store.mapper;

import com.yuhui.store.entity.District;

import java.util.List;

/**
 * 处理省/市/区数据的持久层接口
 */
public interface DistrictMapper {
    /**
     * 获取全国所有省/某省所有市/某市所有区
     * @param parent 父级代号，当获取某市所有区时，使用市的代号；当获取省所有市时，使用省的代号；当获取全国所有省时，使用"86"作为父级代号
     * @return 全国所有省/某省所有市/某市所有区的列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据代号获取地区名称
     * @param code 行政代号
     * @return 名称或null
     */
    String findNameByCode(String code);
}
