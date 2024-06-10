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
package com.dqv5.screw.server.web;

import cn.smallbun.screw.core.metadata.model.TableModel;
import com.dqv5.screw.server.pojo.CommonResponse;
import com.dqv5.screw.server.pojo.DbdocConfigDTO;
import com.dqv5.screw.server.pojo.GenerateResult;
import com.dqv5.screw.server.service.DocumentService;
import com.dqv5.screw.server.util.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;

/**
 * @author duqian
 * @date 2023/9/11
 */
@RestController
@RequestMapping("/api/document")
public class DocumentController {
    @Resource
    private DocumentService documentService;

    @GetMapping("/list-schemas")
    public ResponseEntity<CommonResponse<List<String>>> listSchemas(String datasourceId) {
        List<String> list = documentService.listSchemas(datasourceId);
        return ResponseEntity.ok(CommonResponse.build(true, "查询成功", list));
    }

    @GetMapping("/list-tables")
    public ResponseEntity<CommonResponse<List<TableModel>>> listTables(String datasourceId, String dbSchema) {
        List<TableModel> list = documentService.listTables(datasourceId, dbSchema);
        return ResponseEntity.ok(CommonResponse.build(true, "查询成功", list));
    }

    @GetMapping("/download-templates")
    public ResponseEntity<InputStreamResource> downloadTemplates(HttpServletResponse response) throws IOException {
        String fileName = "templates.zip";
        ClassPathResource classPathResource = new ClassPathResource("files/" + fileName);
        InputStream inputStream = classPathResource.getInputStream();
        InputStreamResource resource = new InputStreamResource(inputStream);

        HttpHeaders headers = new HttpHeaders();
        String filename = URLEncoder.encode(fileName, "UTF-8");
        headers.add("Content-Disposition", String.format("attachment;filename=%s", filename));
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok().headers(headers).contentLength(inputStream.available())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @PostMapping("/generate")
    public void generate(@RequestParam String json,
                         @RequestParam(required = false) MultipartFile template,
                         HttpServletResponse response) {
        DbdocConfigDTO param = JsonUtil.readValue(json, DbdocConfigDTO.class);
        GenerateResult result = documentService.generate(param, template);
        String folderName = result.getFolderName();
        File file = result.getFile();

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = Files.newInputStream(file.toPath());
            outputStream = response.getOutputStream();

            String filename = URLEncoder.encode(file.getName(), "UTF-8");
            response.setHeader("Content-Disposition",
                    String.format("attachment;filename=%s", filename));
            response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            response.setContentLength(inputStream.available());
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
            documentService.deleteTempFolder(folderName);
        }
    }
}
