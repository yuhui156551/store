package com.yuhui.store.controller;


import com.yuhui.store.entity.Product;
import com.yuhui.store.service.ProductService;
import com.yuhui.store.utils.JsonR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController{

    @Autowired
    private ProductService productService;

    /**
     * 请求路径：/products/hot_list
     * 请求参数：无
     * 请求类型：GET
     * 响应结果：JsonResult<List<Product>>
     * 是否拦截：否，需要将index.html和products/**添加到白名单
     */
    @RequestMapping("hot_list")
    public JsonR<List<Product>> getHotList(){
        List<Product> hotList = productService.findHotList();

        //后台记录日志
        for (Product product : hotList) {
            System.out.println(product);
        }

        return new JsonR<>(OK,hotList);
    }

    /**
     *请求路径：/products/{id}/details
     * 请求参数：@PathVariable("id") Integer id
     * 请求类型：GET
     * 响应结果：JsonResult<Product>
     */
    @GetMapping("{id}/details")
    public JsonR<Product> geyById(@PathVariable("id") Integer id){
        Product product = productService.findById(id);
        return new JsonR<>(OK,product);
    }
}
