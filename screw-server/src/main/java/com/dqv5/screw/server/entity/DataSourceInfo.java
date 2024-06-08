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
