package com.ityj.boot.util;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class CommonUtils {

    public static void transfer(List<MultipartFile> files, String destDirectory) {
        files.forEach(file -> {
            String originalFilename = file.getOriginalFilename();
            if (file.isEmpty()) {
                log.warn("The size of file:{} is 0", originalFilename);
            }
            FileUtil.mkdir(destDirectory);
            try {
                File dest = new File(destDirectory + originalFilename);
                file.transferTo(dest);
                log.info("File upload successfully: {}", dest.getAbsolutePath());
            } catch (IOException e) {
                log.error("Error transferTo:", e);
            }
        });

    }
}
