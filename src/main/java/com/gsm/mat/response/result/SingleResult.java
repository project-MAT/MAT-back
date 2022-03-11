package com.gsm.mat.response.result;

import lombok.Getter;

@Getter
public class SingleResult<T> extends CommonResult {
    private T data;

    public SingleResult(CommonResult result, T data) {
        super(result.isSuccess(), result.getCode(), result.getMsg());
        this.data=data;
    }
}
