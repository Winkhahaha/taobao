package edu.xalead.upload;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemException;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemExceptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
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
    @PostMapping("image")
    public ResponseEntity<Void> up(MultipartFile file){
        try {
            String contentType = file.getContentType();//mime类型
            String fileName=file.getOriginalFilename();
            String path="D:\\学习视频\\spring淘宝后台项目\\710）\\taobao\\upload";
            List<String> allowType=Arrays.asList("image/jpeg","image/png","image/gif");
            if(!allowType.contains(contentType)){
                throw new TaobaoItemException(TaobaoItemExceptionEnum.EXCEPTION_ENUM_INVALID_IMAGE_TYPE);
            }
            File f= new File(path,fileName);
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return ResponseEntity.ok(null);
    }


}
