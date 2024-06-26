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

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.metadata.Table;
import cn.smallbun.screw.core.metadata.model.TableModel;
import cn.smallbun.screw.core.process.ProcessConfig;
import cn.smallbun.screw.core.query.DatabaseQuery;
import cn.smallbun.screw.core.query.DatabaseQueryFactory;
import com.dqv5.screw.server.entity.DataSourceInfo;
import com.dqv5.screw.server.pojo.DataSourceProps;
import com.dqv5.screw.server.pojo.DbdocConfigDTO;
import com.dqv5.screw.server.pojo.GenerateResult;
import com.dqv5.screw.server.repository.DataSourceInfoRepository;
import com.dqv5.screw.server.service.DocumentService;
import com.dqv5.screw.server.util.DataSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duqian
 * @date 2021/6/2
 */
@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    @Resource
    private DataSourceInfoRepository      dataSourceInfoRepository;

    public static final String            FILE_SEPARATOR      = File.separator;
    public static final String            AUTO_DIR            = System.getProperty("java.io.tmpdir")
                                                                + FILE_SEPARATOR + "dbdoc";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
        .ofPattern("yyyyMMdd_HHmmss_SSS");

    @Override
    public List<String> listSchemas(String datasourceId) {
        if (StringUtils.isBlank(datasourceId)) {
            throw new RuntimeException("参数错误:datasourceId");
        }
        DataSourceInfo dataSourceInfo = dataSourceInfoRepository.findById(datasourceId)
            .orElseThrow(() -> new RuntimeException("参数错误：datasourceId=" + datasourceId));
        DataSourceProps dataSourceProps = DataSourceUtil.toDataSourceProps(dataSourceInfo);
        DataSource dataSource = DataSourceUtil.getDataSource(dataSourceProps);
        DatabaseQuery query = new DatabaseQueryFactory(dataSource).newInstance();
        return query.getSchemas();
    }

    @Override
    public List<TableModel> listTables(String datasourceId, String dbSchema) {
        if (StringUtils.isBlank(datasourceId)) {
            throw new RuntimeException("参数错误:datasourceId");
        }
        DataSourceInfo dataSourceInfo = dataSourceInfoRepository.findById(datasourceId)
            .orElseThrow(() -> new RuntimeException("参数错误：datasourceId=" + datasourceId));
        DataSourceProps dataSourceProps = DataSourceUtil.toDataSourceProps(dataSourceInfo);
        if (StringUtils.isNotBlank(dbSchema)) {
            dataSourceProps.setDbSchema(dbSchema);
        }
        DataSource dataSource = DataSourceUtil.getDataSource(dataSourceProps);

        //获取query对象
        DatabaseQuery query = new DatabaseQueryFactory(dataSource).newInstance();
        List<TableModel> tableModels = new ArrayList<>();
        List<? extends Table> tables = query.getTables();
        for (Table table : tables) {
            TableModel tableModel = new TableModel();
            tableModel.setTableName(table.getTableName());
            tableModel.setRemarks(table.getRemarks());
            tableModels.add(tableModel);
        }
        query.closeConnection();
        return tableModels;
    }

    @Override
    public GenerateResult generate(DbdocConfigDTO config, MultipartFile template) {
        log.info("config: {}", config);
        log.info("template: {}", template);
        String folderName = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String folderPath = AUTO_DIR + FILE_SEPARATOR + folderName;
        File folder = new File(folderPath);
        if (!folder.mkdirs()) {
            throw new RuntimeException("创建临时目录失败:" + folderPath);
        }

        String templateFilePath = null;
        if (template != null) {
            templateFilePath = folderPath + FILE_SEPARATOR + template.getOriginalFilename();
            try {
                template.transferTo(new File(templateFilePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        DataSourceProps dataSourceProps;
        String datasourceId = config.getDatasourceId();
        if (StringUtils.isNotBlank(datasourceId)) {
            DataSourceInfo dataSourceInfo = dataSourceInfoRepository.findById(datasourceId)
                .orElseThrow(() -> new RuntimeException("参数错误：datasourceId=" + datasourceId));
            dataSourceProps = DataSourceUtil.toDataSourceProps(dataSourceInfo);
            if (StringUtils.isNotBlank(config.getDbSchema())) {
                dataSourceProps.setDbSchema(config.getDbSchema());
            }
        } else {
            dataSourceProps = new DataSourceProps();
            dataSourceProps.setDbUrl(config.getDbUrl());
            dataSourceProps.setDbUsername(config.getDbUsername());
            dataSourceProps.setDbPassword(config.getDbPassword());
            dataSourceProps.setDbSchema(config.getDbSchema());
        }
        DataSource dataSource = DataSourceUtil.getDataSource(dataSourceProps);
        EngineFileType engineFileType = config.getFileType();
        EngineTemplateType produceType = config.getProduceType();
        String title = config.getTitle();
        String description = config.getDescription();
        String version = config.getVersion();
        //生成配置
        EngineConfig engineConfig = EngineConfig.builder()
            //生成文件路径
            .fileOutputDir(folderPath)
            //文件类型
            .fileType(engineFileType)
            //生成模板实现
            .produceType(produceType)
            //自定义模板，模板需要和文件类型和使用模板的语法进行编写和处理，否则将会生成错误
            .customTemplate(templateFilePath).build();

        // 指定表名称
        List<String> tableNames = config.getTableNames();
        // 指定表前缀
        List<String> tablePrefixes = config.getTablePrefixes();
        // 指定表后缀
        List<String> tableSuffixes = config.getTableSuffixes();
        //忽略表
        List<String> ignoreTableName = config.getIgnoreTableNames();
        //忽略表前缀
        List<String> ignorePrefix = config.getIgnorePrefixes();
        //忽略表后缀
        List<String> ignoreSuffix = config.getIgnoreSuffixes();
        ProcessConfig processConfig = ProcessConfig.builder()
            //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
            //根据名称指定表生成
            .designatedTableName(tableNames)
            //根据表前缀生成
            .designatedTablePrefix(tablePrefixes)
            //根据表后缀生成
            .designatedTableSuffix(tableSuffixes)
            //忽略表名
            .ignoreTableName(ignoreTableName)
            //忽略表前缀
            .ignoreTablePrefix(ignorePrefix)
            //忽略表后缀
            .ignoreTableSuffix(ignoreSuffix).build();
        //配置
        Configuration configuration = Configuration.builder()
            //版本
            .version(version)
            // 标题
            .title(title)
            //描述
            .description(description)
            //数据源
            .dataSource(dataSource)
            //生成配置
            .engineConfig(engineConfig)
            //生成配置
            .produceConfig(processConfig).build();
        //执行生成
        File file = new DocumentationExecute(configuration).execute();
        log.info("文档生成成功: {}", file);

        return new GenerateResult(folderName, file);
    }

    @Override
    public void deleteTempFolder(String folderName) {
        String folderPath = AUTO_DIR + FILE_SEPARATOR + folderName;
        FileUtils.deleteQuietly(new File(folderPath));
    }
}
