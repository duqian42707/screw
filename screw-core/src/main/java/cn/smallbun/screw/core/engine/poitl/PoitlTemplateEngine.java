package cn.smallbun.screw.core.engine.poitl;

import cn.smallbun.screw.core.engine.AbstractTemplateEngine;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.exception.ProduceException;
import cn.smallbun.screw.core.metadata.model.DataModel;
import cn.smallbun.screw.core.util.Assert;
import cn.smallbun.screw.core.util.ExceptionUtils;
import cn.smallbun.screw.core.util.StringUtils;
import com.deepoove.poi.XWPFTemplate;

import java.io.*;
import java.nio.file.Files;

import static cn.smallbun.screw.core.engine.EngineTemplateType.poitl;
import static cn.smallbun.screw.core.util.FileUtils.isFileExists;

/**
 * @author duq
 * @date 2024/6/8
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
            XWPFTemplate template;
            // 如果自定义路径不为空文件也存在
            if (StringUtils.isNotBlank(path) && isFileExists(path)) {
                template = XWPFTemplate.compile(path);
            }
            //获取系统默认的模板
            else {
                String defaultPath = poitl.getTemplateDir() + getEngineConfig().getFileType().getTemplateNamePrefix() + poitl.getSuffix();
                InputStream inputStream = this.getClass().getResourceAsStream(defaultPath);
                template = XWPFTemplate.compile(inputStream);
            }
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
}
