/*
 * screw-core - 简洁好用的数据库表结构文档生成工具
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
package cn.smallbun.screw.core.engine.poitl;

import cn.smallbun.screw.core.engine.AbstractTemplateEngine;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.exception.ProduceException;
import cn.smallbun.screw.core.metadata.model.DataModel;
import cn.smallbun.screw.core.util.Assert;
import cn.smallbun.screw.core.util.ExceptionUtils;
import cn.smallbun.screw.core.util.StringUtils;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static cn.smallbun.screw.core.engine.EngineTemplateType.poitl;
import static cn.smallbun.screw.core.util.FileUtils.isFileExists;

/**
 * @author duq
 */
public class PoitlTemplateEngine extends AbstractTemplateEngine {

    public PoitlTemplateEngine(EngineConfig engineConfig) {
        super(engineConfig);
    }

    @Override
    public File produce(DataModel info, String docName) throws ProduceException {
        Assert.notNull(info, "DataModel can not be empty!");
        String path = getEngineConfig().getCustomTemplate();
        try {
            InputStream inputStream;
            // 如果自定义路径不为空文件也存在
            if (StringUtils.isNotBlank(path) && isFileExists(path)) {
                inputStream = Files.newInputStream(Paths.get(path));
            }
            //获取系统默认的模板
            else {
                String defaultPath = poitl.getTemplateDir()
                                     + getEngineConfig().getFileType().getTemplateNamePrefix()
                                     + poitl.getSuffix();
                inputStream = this.getClass().getResourceAsStream(defaultPath);
            }
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder().bind("columns", policy).useSpringEL().build();
            XWPFTemplate template = XWPFTemplate.compile(inputStream, config);
            // create file
            File file = getFile(docName);
            // writer
            try (OutputStream out = Files.newOutputStream(file.toPath())) {
                // process
                template.render(info);
                template.writeAndClose(out);
                // open the output directory
                openOutputDir();
            }
            return file;
        } catch (IOException e) {
            throw ExceptionUtils.mpe(e);
        }
    }

    protected String getFileSuffix() {
        return ".docx";
    }

}
