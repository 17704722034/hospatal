package com.lening.controller;

import com.lening.entity.MeunBean;
import com.lening.utils.ResultInfo;
import com.lening.service.MeunService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/meun")
public class MeunController {
    @Resource
    private MeunService meunService;
    @RequestMapping("/deleteMeunById")
    public ResultInfo deleteMeunById(Long id){
        try {
            meunService.deleteMeunById(id);
            return new ResultInfo(true, "删除成功");
        }catch (Exception e){
            return new ResultInfo(false, "删除失败");
        }
    }
    @RequestMapping("/getMeunListByPid")
    public List<MeunBean> getMeunListByPid(@RequestParam(defaultValue = "1")Long pid){
        return meunService.getMeunListByPid(pid);
    }

    @RequestMapping("/saveMeun")
    public ResultInfo saveMeun(@RequestBody MeunBean meunBean){
      try {
          meunService.saveMeun(meunBean);
          return new ResultInfo(true, "编辑成功");
      }catch (Exception e){
          return new ResultInfo(false, "编辑失败");
      }
    }
}
