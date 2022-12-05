package com.yuhui.store.service;

import com.yuhui.store.entity.District;

import java.util.List;

/**
 * 处理省/市/区数据的业务层接口
 */
public interface DistrictService {

    /**
     * 获取所有区
     * @param parent 父级代号
     * @return 所有区
     */
    List<District> getByParent(String parent);

    /**
     * 根据省/市/区的行政代号获取名称
     * @param code 省/市/区的行政代号
     * @return 匹配的省/市/区的名称，如果没有匹配的数据则返回null
     */
    String geyNameByCode(String code);
}
