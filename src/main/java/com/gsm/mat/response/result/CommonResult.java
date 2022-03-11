package com.gsm.mat.response.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResult {
    private boolean success;

    private int code;

    private String msg;
}
