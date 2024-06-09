/*
 * screw-server - 简洁好用的数据库表结构文档生成工具
 * Copyright © 2020 SanLi (qinggang.zuo@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
