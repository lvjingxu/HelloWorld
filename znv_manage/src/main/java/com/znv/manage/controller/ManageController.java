package com.znv.manage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.service.ManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 时智超
 * @ClassName: ManageController
 * @Description: xxx描述该类主要说明
 * @date 2018/5/17 11:26
 */
@RestController
@RequestMapping("/manage")
public class ManageController {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ManageController.class);

    
    /**
     * redis引用
     */
//	@Resource
//	RedisTemplate<String,Object>  redisTemplate;

    /**
     * 服务层接口
     */
    @Resource
    private ManageService manageService;

    /**
     * 根据查询条件查询服务信息
     * @param id id
     * @param serverName 服务名
     * @param status 服务状态
     * @return 服务列表
     */
    @RequestMapping(value = "/server-list")
    public Object serverList(@RequestParam(required = false,name = "id" ) String id,
                             @RequestParam(required = false,name = "serverName") String serverName,
                             @RequestParam(required = false,name = "status") String status,
                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize){
        //TODO  前端请求参数过多请使用对象接收

        //设置分页参数  也可添加排序参数等，具体可看官方文档说明，这里demo是对type降序
        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> recordMap = new HashMap<>(5);
        recordMap.put("id",id);
        recordMap.put("serverName",serverName);
        //保存到REDIS中
//        redisTemplate.opsForValue().set("recordMap", recordMap);
        List<Map<String, Object>> list = manageService.queryServer(recordMap);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);


        return pageInfo;
    }

    /**
     * 启动服务
     * @param id id
     * @return 启动服务状态结果
     * @throws Exception
     */
    @RequestMapping(value = "/server-start")
    public Object startServer(@RequestParam(required = true,name = "id") String id)throws Exception{
        return manageService.startServer(id);
    }


}
