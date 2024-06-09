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
package com.dqv5.screw.server.service.impl;

import com.dqv5.screw.server.entity.DataSourceInfo;
import com.dqv5.screw.server.repository.DataSourceInfoRepository;
import com.dqv5.screw.server.service.DataSourceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author duq
 * @date 2024/6/7
 */
@Service
public class DataSourceInfoServiceImpl implements DataSourceInfoService {
    @Resource
    private DataSourceInfoRepository dataSourceInfoRepository;

    @Override
    public List<DataSourceInfo> queryList() {
        return dataSourceInfoRepository.findAllByOrderByIdDesc();
    }

    @Override
    public DataSourceInfo insert(DataSourceInfo param) {
        param.setId(null);
        return dataSourceInfoRepository.save(param);
    }

    @Override
    public DataSourceInfo update(DataSourceInfo param) {
        DataSourceInfo dataSourceInfo = dataSourceInfoRepository.findById(param.getId())
            .orElseThrow(() -> new RuntimeException("参数错误：id=" + param.getId()));
        dataSourceInfo.setName(param.getName());
        dataSourceInfo.setDbUrl(param.getDbUrl());
        dataSourceInfo.setDbUsername(param.getDbUsername());
        dataSourceInfo.setDbPassword(param.getDbPassword());
        dataSourceInfo.setDbSchema(param.getDbSchema());
        dataSourceInfo.setRemark(param.getRemark());
        return dataSourceInfoRepository.save(dataSourceInfo);
    }

    @Override
    public void delete(String id) {
        dataSourceInfoRepository.deleteById(id);
    }
}
