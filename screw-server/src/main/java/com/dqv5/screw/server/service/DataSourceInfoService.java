package com.dqv5.screw.server.service;

import com.dqv5.screw.server.entity.DataSourceInfo;

import java.util.List;

/**
 * @author duq
 * @date 2024/6/7
 */
public interface DataSourceInfoService {
    List<DataSourceInfo> queryList();

    DataSourceInfo insert(DataSourceInfo param);

    DataSourceInfo update(DataSourceInfo param);

    void delete(String id);
}
