package com.dqv5.screw.server.web;

import com.dqv5.screw.server.entity.DataSourceInfo;
import com.dqv5.screw.server.pojo.CommonResponse;
import com.dqv5.screw.server.service.DataSourceInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author duqian
 * @date 2023/9/11
 */
@RestController
@RequestMapping("/api/datasource")
public class DataSourceInfoController {
    @Resource
    private DataSourceInfoService dataSourceInfoService;

    @GetMapping("/queryList")
    public ResponseEntity<CommonResponse<List<DataSourceInfo>>> queryList() {
        List<DataSourceInfo> list = dataSourceInfoService.queryList();
        return ResponseEntity.ok(CommonResponse.build(true, "查询成功", list));
    }

    @PostMapping("/insert")
    public ResponseEntity<CommonResponse<DataSourceInfo>> insert(@RequestBody DataSourceInfo param) {
        DataSourceInfo result = dataSourceInfoService.insert(param);
        return ResponseEntity.ok(CommonResponse.build(true, "操作成功", result));
    }

    @PostMapping("/update")
    public ResponseEntity<CommonResponse<DataSourceInfo>> update(@RequestBody DataSourceInfo param) {
        DataSourceInfo result = dataSourceInfoService.update(param);
        return ResponseEntity.ok(CommonResponse.build(true, "操作成功", result));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<CommonResponse<Object>> update(@PathVariable String id) {
        dataSourceInfoService.delete(id);
        return ResponseEntity.ok(CommonResponse.build(true, "操作成功"));
    }


}
