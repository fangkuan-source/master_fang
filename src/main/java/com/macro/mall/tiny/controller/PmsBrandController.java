package com.macro.mall.tiny.controller;

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.mbg.model.PmsBrand;
import com.macro.mall.tiny.service.PmsBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理
 */
@Controller
@RequestMapping("/brand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService demoService;
    //使用指定类初始化日志对象,在日志输出的时候，可以打印出日志信息所在类
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    /*    RequestMapping注解有六个属性，下面分成三类进行说明。

    value， method
    value： 指定请求的实际地址，指定的地址可以是具体地址、可以RestFul动态获取、也可以使用正则设置；

    method： 指定请求的method类型， 分为GET、POST、PUT、DELETE等；
            ————————————————
    版权声明：本文为CSDN博主「伟大的豪哥」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/qq_36709499/article/details/83654944
    */
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    @RequestMapping(value="listAll",method = RequestMethod.GET)
    @ResponseBody

    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(demoService.listAllBrand());
    }

    @RequestMapping(value = "/create",method= RequestMethod.POST)
    @ResponseBody
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand){
        CommonResult commonResult;
        int count = demoService.createBrand(pmsBrand);
        if(count ==1){
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.debug("createBrand success:{}",pmsBrand);
        }else{
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("createBrand FAILED:{}",pmsBrand);
        }

        return commonResult;

    }

   // @PathVariable是spring3.0的一个新功能：接收请求路径中占位符的值
    @RequestMapping(value = "/update/{id}" , method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id,@RequestBody PmsBrand pmsBrandDto, BindingResult result){
        CommonResult commonResult;
        int count = demoService.updateBrand(id,pmsBrandDto);
        if(count==1){
            commonResult= CommonResult.success(pmsBrandDto);
            LOGGER.debug("updateBrand success:()",pmsBrandDto);
        }else{
            commonResult= CommonResult.failed("更新失败");
            LOGGER.debug("updateBrand failed:()",pmsBrandDto);
        }
        return commonResult;
    }
// POST：一般用于修改服务器上的资源，对所发送的信息没有限制。
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody

    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        int count = demoService.deleteBrand(id);
        if (count == 1) {
            LOGGER.debug("deleteBrand success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("deleteBrand failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<PmsBrand> brandList = demoService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        return CommonResult.success(demoService.getBrand(id));
    }
}
