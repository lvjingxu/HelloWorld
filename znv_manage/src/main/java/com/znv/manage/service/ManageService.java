package com.znv.manage.service;

import java.util.List;
import java.util.Map;

import com.znv.manage.common.bean.Result;

/**
 * @author 时智超
 * @ClassName: ManageService
 * @Description:
 * @date 2018/5/18 14:56
 */
public interface ManageService {

    Result startServer(String id)throws Exception;
    
    List<Map<String,Object>> queryServer(Map<String, Object> recordMap);

}
