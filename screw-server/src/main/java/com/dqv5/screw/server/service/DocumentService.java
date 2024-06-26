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

import cn.smallbun.screw.core.metadata.model.TableModel;
import com.dqv5.screw.server.pojo.DbdocConfigDTO;
import com.dqv5.screw.server.pojo.GenerateResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author duqian
 * @date 2020/7/22
 */

public interface DocumentService {
    List<String> listSchemas(String datasourceId);

    List<TableModel> listTables(String datasourceId, String dbSchema);

    /**
     * 生成文件并下载
     */
    GenerateResult generate(DbdocConfigDTO param, MultipartFile template);

    void deleteTempFolder(String folderName);

}
