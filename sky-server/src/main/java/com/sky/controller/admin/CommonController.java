package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 * @author f
 * @date 2023/9/2 11:28
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

	@Resource
	private AliOssUtil aliOssUtil;

	@PostMapping("/upload")
	@ApiOperation("文件上传")
	public Result<String> upload(MultipartFile file) {
		log.info("文件上传：{}", file);

		try {
			String originalFilename = file.getOriginalFilename();
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			String objectName = UUID.randomUUID().toString() + extension;
			String filePath = aliOssUtil.upload(file.getBytes(), objectName);
			return Result.success(filePath);
		} catch (IOException e) {
			log.error("文件上传失败：{}", e.getMessage());
		}

		return Result.error(MessageConstant.UPLOAD_FAILED);
	}
}
