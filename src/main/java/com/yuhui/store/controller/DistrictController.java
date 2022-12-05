package com.yuhui.store.controller;


import com.yuhui.store.entity.District;
import com.yuhui.store.service.DistrictService;
import com.yuhui.store.utils.JsonR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 多级联查：三级
 * 页面点击下拉框-->传递code-->后端获取例如四川省-->code作为parent获取例如成都市
 */
@RestController
@RequestMapping("/districts")
public class DistrictController extends BaseController{
    @Autowired
    private DistrictService districtService;

    @GetMapping({"","/"}) // URI后面跟的是/或者不跟
    public JsonR<List<District>> getByParent(String parent){
        List<District> list = districtService.getByParent(parent);
        return new JsonR<>(OK,list);
    }


}
