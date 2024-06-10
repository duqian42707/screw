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
package com.dqv5.screw.server.util;

import com.dqv5.screw.server.entity.DataSourceInfo;
import com.dqv5.screw.server.pojo.DataSourceProps;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author duq
 * @date 2024/6/9
 */
public class DataSourceUtil {
    private static final Map<DataSourceProps, DataSource> DATA_SOURCE_MAP = new ConcurrentHashMap<>();

    /**
     * 工具类构造函数私有，不需要实例化
     */
    private DataSourceUtil() {
    }

    public static DataSourceProps toDataSourceProps(DataSourceInfo dataSourceInfo) {
        DataSourceProps dataSourceProps = new DataSourceProps();
        dataSourceProps.setDbUrl(dataSourceInfo.getDbUrl());
        dataSourceProps.setDbUsername(dataSourceInfo.getDbUsername());
        dataSourceProps.setDbPassword(dataSourceInfo.getDbPassword());
        dataSourceProps.setDbSchema(dataSourceInfo.getDbSchema());
        return dataSourceProps;
    }

    public static DataSource getDataSource(DataSourceProps dataSourceProps) {
        if (DATA_SOURCE_MAP.containsKey(dataSourceProps)) {
            return DATA_SOURCE_MAP.get(dataSourceProps);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceProps.getDbUrl());
        hikariConfig.setUsername(dataSourceProps.getDbUsername());
        hikariConfig.setPassword(dataSourceProps.getDbPassword());
        hikariConfig.setMaximumPoolSize(3);
        hikariConfig.setMaxLifetime(3);
        if (StringUtils.isNotBlank(dataSourceProps.getDbSchema())) {
            hikariConfig.setSchema(dataSourceProps.getDbSchema());
        }

        // 连接池参数，暂时使用默认的配置

        // 设置额外的属性
        Properties props = new Properties();
        hikariConfig.setDataSourceProperties(props);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        DATA_SOURCE_MAP.put(dataSourceProps, dataSource);
        return dataSource;
    }
}
