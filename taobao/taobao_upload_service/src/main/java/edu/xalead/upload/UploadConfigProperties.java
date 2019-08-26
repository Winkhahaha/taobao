package edu.xalead.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
@Data
@ConfigurationProperties("taobao.upload")
public class UploadConfigProperties {
    private String base_url;
    private List<String> allowTypes;
}
