package com.gsm.mat.response;

import com.gsm.mat.response.result.CommonResult;
import com.gsm.mat.response.result.ListResult;
import com.gsm.mat.response.result.SingleResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Getter
    @AllArgsConstructor
    public enum CommonResponse{
        SUCCESS(200, "성공하였습니다");

        int code;
        String msg;
    }

    // 단일건 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>(createSuccessResult(), data);
        return result;
    }
    // 다중건 결과를 처리하는 메소드
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>(createSuccessResult(), list);
        result.setList(list);
        return result;
    }
    // 성공 결과만 처리하는 메소드
    public CommonResult getSuccessResult() {
        CommonResult result = createSuccessResult();
        return result;
    }
    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private CommonResult createSuccessResult() {
        CommonResult result=new CommonResult(true, CommonResponse.SUCCESS.getCode(), CommonResponse.SUCCESS.getMsg());
        return result;
    }
}
