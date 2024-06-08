package com.dqv5.screw.server.pojo;

import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import lombok.Data;

import java.util.List;

/**
 * @author duqian
 * @date 2021/6/2
 */
@Data
public class DbdocConfigDTO {

    private String datasourceId;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private String dbSchema;

    private String title;
    private String description;
    private String version;
    private EngineFileType fileType;
    private EngineTemplateType produceType;

    private List<String> tableNames;
    private List<String> tablePrefixes;
    private List<String> tableSuffixes;
    private List<String> ignoreTableNames;
    private List<String> ignorePrefixes;
    private List<String> ignoreSuffixes;


}
