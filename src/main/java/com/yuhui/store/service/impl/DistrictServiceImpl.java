package com.yuhui.store.service.impl;

import com.yuhui.store.entity.District;
import com.yuhui.store.mapper.DistrictMapper;
import com.yuhui.store.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        List<District> districts = districtMapper.findByParent(parent);
        // 网络数据传输时，为避免无效数据的传递，将无效数据设为null
        for (District d : districts) {
            d.setId(null);
            d.setParent(null);
        }
        return districts;
    }

    @Override
    public String geyNameByCode(String code) {
        return districtMapper.findNameByCode(code);// 一行代码，懒得测试了
    }
}
