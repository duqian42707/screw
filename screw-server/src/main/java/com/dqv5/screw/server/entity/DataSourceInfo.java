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
package com.dqv5.screw.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author duq
 * @date 2024/6/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "data_source_info")
public class DataSourceInfo extends BaseEntity {
    /**
     * Long类型js精度不够，会出现数值错误的情况
     */
    @Id
    @GenericGenerator(name = "snowflakeId", strategy = "com.dqv5.screw.server.config.SnowflakeIdGenerator")
    @GeneratedValue(generator = "snowflakeId")
    private String id;
    private String name;
    private String remark;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private String dbSchema;

}
