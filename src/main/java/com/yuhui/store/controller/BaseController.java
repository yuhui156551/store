package com.yuhui.store.controller;

import com.yuhui.store.controller.exception.*;
import com.yuhui.store.service.exception.*;
import com.yuhui.store.utils.JsonR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制器类的基类
 */
@Slf4j
public class BaseController {
    public static final int OK = 200;

    /**
     * 统一处理方法抛出的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonR<Void> handleException(Throwable e) {
        log.info(e.getMessage());

        JsonR<Void> result = new JsonR<Void>(e); //异常的massage信息封装进result

        if (e instanceof UsernameDuplicateException) {
            result.setCode(4000);
        } else if (e instanceof InsertException) {
            result.setCode(5000);
        } else if (e instanceof UserNotFoundException) {
            result.setCode(4001);
        } else if (e instanceof PasswordNotMatchException) {
            result.setCode(4002);
        } else if (e instanceof UpdateException) {
            result.setCode(5002);
        }  else if (e instanceof FileEmptyException) {
            result.setCode(6000);
        } else if (e instanceof FileSizeException) {
            result.setCode(6001);
        } else if (e instanceof FileTypeException) {
            result.setCode(6002);
        } else if (e instanceof FileStateException) {
            result.setCode(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setCode(6004);
        } else if (e instanceof AddressCountLimitException) {
            result.setCode(4003);
        } else if (e instanceof AddressNotFoundException) {
            result.setCode(4004);
        } else if (e instanceof DeleteException) {
            result.setCode(5002);
        } else if (e instanceof ProductNotFoundException) {
            result.setCode(4006);
        }
        return result;
    }

    /**
     * 从session中获取uid
     * @param session session对象
     * @return 当前登录用户的uid值
     */
    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid")
                .toString());
    }

    /**
     * 从session中获取username
     * @param session
     * @return 用户名
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
