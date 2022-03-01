package com.gsm.mat.tag.controller;

import com.gsm.mat.response.ResponseService;
import com.gsm.mat.response.result.CommonResult;
import com.gsm.mat.response.result.ListResult;
import com.gsm.mat.tag.Tag;
import com.gsm.mat.tag.dto.TagDto;
import com.gsm.mat.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TagController {
    private final ResponseService responseService;
    private final TagService tagService;
    @PostMapping("/tag/{notificationIdx}")
    public CommonResult createTag(@PathVariable Long notificationIdx, @RequestBody List<TagDto> tagDtoList){
        tagService.create(tagDtoList,notificationIdx);
        return responseService.getSuccessResult();
    }
    @GetMapping("/tag/{notificationIdx}")
    public ListResult<Tag> findByNotification(@PathVariable Long notificationIdx){
        return responseService.getListResult(tagService.findByNotification(notificationIdx));
    }
    @DeleteMapping("/tag/{tagIdx}")
    public CommonResult deleteTag(@PathVariable Long tagIdx){
        tagService.deleteTag(tagIdx);
        return responseService.getSuccessResult();
    }
}
