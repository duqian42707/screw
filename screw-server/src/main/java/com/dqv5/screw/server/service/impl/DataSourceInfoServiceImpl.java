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
        DataSourceInfo dataSourceInfo = dataSourceInfoRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException("参数错误：id=" + param.getId()));
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
