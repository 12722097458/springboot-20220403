package com.ityj.boot.controller;

import com.ityj.boot.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Slf4j
public class FileUploadTestController {

    @Value("${path.fileupload}")
    private String destPath;

    @GetMapping("/fileupload")
    public String toFileUploadPage() {
        return "fileupload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("username") String username,
                         @RequestPart MultipartFile profilePhoto,
                         @RequestPart List<MultipartFile> lifePhotos,
                         Model model) {
        log.info("username = {}", username);
        log.info("profilePhoto.size() = {}", profilePhoto.getSize());
        log.info("lifePhotos数量 = {}", lifePhotos.size());

        CommonUtils.transfer(Stream.of(profilePhoto).collect(Collectors.toList()), destPath);
        CommonUtils.transfer(lifePhotos, destPath);

        model.addAttribute("msg", "文件上传成功！");
        return "success";

    }
}
