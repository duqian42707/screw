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

    private String             datasourceId;
    private String             dbUrl;
    private String             dbUsername;
    private String             dbPassword;
    private String             dbSchema;

    private String             title;
    private String             description;
    private String             version;
    private EngineFileType     fileType;
    private EngineTemplateType produceType;

    private List<String>       tableNames;
    private List<String>       tablePrefixes;
    private List<String>       tableSuffixes;
    private List<String>       ignoreTableNames;
    private List<String>       ignorePrefixes;
    private List<String>       ignoreSuffixes;

}
