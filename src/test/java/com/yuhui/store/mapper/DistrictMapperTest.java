package com.yuhui.store.mapper;

import com.yuhui.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DistrictMapperTest {

    @Autowired
    private DistrictMapper districtMapper;

    @Test
    void findByParent(){
        List<District> districts = districtMapper.findByParent("210100");
        for (District district : districts) {
            System.out.println(district);
        }
    }

    @Test
    void findNameByCode(){
        String name = districtMapper.findNameByCode("610000");
        System.out.println(name);
    }
}
