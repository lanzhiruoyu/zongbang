package com.zongbang.controller;

import com.zongbang.pojo.Result;
import com.zongbang.pojo.StatusCode;
import com.zongbang.utils.FastDFSFile;
import com.zongbang.utils.FastDFSUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import java.io.IOException;

/**
 * @program: zongbang_parent
 * @description:
 * @author: LiaoHui
 * @create: 2020-02-09 00:15
 **/
@RestController
@RequestMapping(value = "/upload")
@CrossOrigin
public class FileUploadController {


    @RequestMapping
    public Result upload(@RequestParam(value = "file") MultipartFile file) throws Exception {

        //判断文件是否存在
        if (null == file){
            throw new RuntimeException("文件不存在");
        }
        //获取文件的完整名称
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)){
            throw new RuntimeException("文件不存在");
        }

        FastDFSFile fastDFSFile = new FastDFSFile(
                originalFilename,
                file.getBytes(),
                StringUtils.getFilenameExtension(originalFilename));
        String[] upload = FastDFSUtil.uploadFile(fastDFSFile);
        String url = FastDFSUtil.getTrackerInfo()+"/"+upload[0]+"/"+upload[1];
        return new Result(true, StatusCode.OK, "上传成功",url);
    }

}