package com.dqv5.screw.server.pojo;

import lombok.Data;

/**
 * @author duq
 * @date 2024/6/9
 */
@Data
public class DataSourceProps {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private String dbSchema;
}
