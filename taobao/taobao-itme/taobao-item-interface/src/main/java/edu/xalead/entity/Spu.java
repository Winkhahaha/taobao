package edu.xalead.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "tb_spu")
public class Spu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long brandId;
    private Long cid1;// 1级类目
    private Long cid2;// 2级类目
    private Long cid3;// 3级类目
    private String title;// 标题
    private String subTitle;// 子标题
    private Boolean saleable;// 是否上架

    private Boolean valid;// 是否有效，逻辑删除用
    @JsonIgnore
    private Date createTime;// 创建时间
    @JsonIgnore
    private Date lastUpdateTime;// 最后修改时间
	// 省略getter和setter
    @Transient
    private String cname;//三级类目名称拼接组合的字符串
    @Transient
    private String bname;//品牌名称
    @Transient
    private SpuDetail spuDetail;
    @Transient
    private List<Sku> skus;
}