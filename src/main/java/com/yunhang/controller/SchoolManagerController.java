package com.yunhang.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yunhang.entity.SchoolManage;
import com.yunhang.service.SchoolManageService;
import com.yunhang.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 王耀
 * \* Date: 2019/9/28
 * \* Time: 13:40
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/schoolmanagerinfo/")
public class SchoolManagerController

{

    @Autowired
    private SchoolManageService schoolManageService;
    /**
     * 添加学校信息!
     * @param schoolManage
     * @return
     */
    @PostMapping(value = "insertschoolmanagerinfo")
    public JsonResult apendSchoolManagerInfo(@RequestBody SchoolManage schoolManage){
        if ("{}".equals(JSON.toJSONString(schoolManage)))return JsonResult.errorMsg("数据为空,失败");
       Integer sign=schoolManageService.apendSchoolManagerInfos(schoolManage);
            if (sign>0)return JsonResult.ok();
            else return JsonResult.errorMsg("添加失败!");
    }

    /**
     * 修改学校信息!
     * @return
     */
    @PostMapping("updateschoolinfo")
    public JsonResult updateSchoolManagerInfo(@RequestBody SchoolManage schoolManage){
        System.out.println("修改的信息:"+schoolManage);
        if ("{}".equals(JSON.toJSONString(schoolManage)))return JsonResult.errorMsg("数据为空,失败");
            Integer sign=schoolManageService.updateShoolManagerInfo(schoolManage);
                if (sign>0)return JsonResult.ok();
                else return JsonResult.errorMsg("修改失败!");
    }

    /**
     * 删除学校管理(将就把关注的学生也删了)
     * @param schoolManage
     * @return
     */
    @PostMapping("deleteschoolmanager")
    public JsonResult deleteSchoolManagerInfo(@RequestBody SchoolManage schoolManage){
        if ("{}".equals(JSON.toJSONString(schoolManage)))return JsonResult.errorMsg("数据为空,失败");
        Integer mark = schoolManageService.deleteSchoolInfos(schoolManage);
            if (mark>0)return JsonResult.ok();
            else return JsonResult.errorMsg("删除失败!");
    }

    /**
     * 根据编号查询数据信息!
     * @param schoolId
     * @return
     */
    @GetMapping("selectschoolmanagerschoolinfo/{schoolId}")
    public JsonResult querySchoolManagerInfosBySchoolId(@PathVariable(value = "schoolId") Integer schoolId){
        if (schoolId>0) {
            return JsonResult.ok(schoolManageService.querySchoolManagerInfoBySchoolId(schoolId));
        }
        return JsonResult.errorMsg("数据为空!");
    }

    /**
     * 查询所有的学校信息
     * @return
     */
    @PostMapping("selectschoolmanagerinfos")
    public JsonResult querySchoolManagerInfoAlls(@RequestParam(required = false,defaultValue = "1") Integer startPage,
                                                 @RequestParam(required = false,defaultValue = "6") Integer pageSize){
        Page<SchoolManage> info = PageHelper.startPage(startPage, pageSize);
        List<SchoolManage> schoolManagerList = schoolManageService.querySchoolManagerInfosByAlls();
        return JsonResult.ok(schoolManagerList);
    }

    /**
     * 模糊查询学校信息! 根据地址查询学校信息...
     * @param schoolName
     * @param  schoolAddress
     * @return
     */
    @GetMapping("vaguesearchschoolinfo")
    public JsonResult vagueSearchSchoolInfos(@RequestParam(required = false,defaultValue = "1") Integer startPage,
                                             @RequestParam(required = false,defaultValue = "6") Integer pageSize,
                                             @RequestParam(required = false)  String schoolName,
                                             @RequestParam(required = false)  String schoolAddress
    ) {
        //根据学校的地址查询学校
        if (schoolAddress!=null) {
            return JsonResult.ok(schoolManageService.querySchoolManagerInfosBySchoolAddress(schoolAddress));
        } else
            if (StringUtil.isNotEmpty(schoolName.trim())) {
                Page<SchoolManage> info = PageHelper.startPage(startPage, pageSize);
                List<SchoolManage> schoolManagerListInfo = schoolManageService.vagueSearchSchoolManagerInfo(schoolName);
                return JsonResult.ok(schoolManagerListInfo);
            } else {
                return querySchoolManagerInfoAlls(startPage, pageSize);
            }

    }






}
