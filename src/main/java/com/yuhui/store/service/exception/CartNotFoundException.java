package com.yuhui.store.service.exception;

/**
 * 购物车数据不存在的异常
 */
public class CartNotFoundException extends ServiceException{
    public CartNotFoundException(String message) {
        super(message);
    }
}
