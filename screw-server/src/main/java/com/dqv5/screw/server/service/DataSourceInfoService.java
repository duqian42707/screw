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
