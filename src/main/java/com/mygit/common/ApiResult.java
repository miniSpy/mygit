package com.mygit.common;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * 统一結果封裝
 * @author ispy
 * @date 2021/2/2
 */
@Component
@Data
public class ApiResult implements Serializable {

    /**
     * 返回結果碼
     */
    private Integer code;

    /**
     * msg
     */
    private String msg;

    /**
     * 返回數據
     */
    private Object data;

    /**
     * 封裝簡化成功返回
     * @param data
     * @return
     */
    public static ApiResult sendSuccess(Object data){
        ApiResult res = new ApiResult();
        res.setData(data);
        res.setMsg("返回成功");
        res.setCode(200);
        return res;
    }

    /**
     * 成功返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ApiResult sendSuccess(Integer code,String msg,Object data){
        ApiResult res = new ApiResult();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    /**
     * 封裝失敗返回
     * @param msg
     * @return
     */
    public static ApiResult sendFail(String msg) {
        ApiResult res = new ApiResult();
        res.setCode(400);
        res.setMsg(msg);
        res.setData(null);
        return res;
    }

    /**
     * 失敗返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ApiResult sendFail(Integer code,String msg,Object data){
        ApiResult res = new ApiResult();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

}
