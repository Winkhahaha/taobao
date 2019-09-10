package edu.xalead.upload;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemException;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@EnableConfigurationProperties(UploadConfigProperties.class)
@RequestMapping("upload")
public class UploadController {
    @Resource
    private UploadConfigProperties props;

    @Resource
    private FastFileStorageClient storageClient;

    @Resource
    private ThumbImageConfig thumbImageConfig;

    @PostMapping("image")
    public ResponseEntity<String> up(MultipartFile file){
        String path = null;
        try {
            String contentType = file.getContentType();//mime类型
            String fileName = file.getOriginalFilename();

            List<String> allowType = props.getAllowTypes();
            if(!allowType.contains(contentType)){
                throw new TaobaoItemException(TaobaoItemExceptionEnum.EXCEPTION_ENUM_INVALID_IMAGE_TYPE);
            }
//            String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
            String extension = StringUtils.substringAfterLast(fileName,".");
            // 上传并且生成缩略图
            StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(), file.getSize(), extension, null);

            //返回路径
            // 获取缩略图路径
            path = props.getBase_url() + thumbImageConfig.getThumbImagePath(storePath.getFullPath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new TaobaoItemException(501,e.getMessage());
        }
        return ResponseEntity.ok(path);
    }
}
