package com.gsm.mat.response.result;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResult<T> extends CommonResult {
    private List<T> list;

    public ListResult(CommonResult result,List<T> list) {
        super(result.isSuccess(), result.getCode(), result.getMsg());
        this.list=list;
    }
}