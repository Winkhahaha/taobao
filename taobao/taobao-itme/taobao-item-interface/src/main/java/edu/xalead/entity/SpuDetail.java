package edu.xalead.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name="tb_spu_detail")
public class SpuDetail {
    @Id
    private Long spuId;// 对应的SPU的id
    private String description;// 商品描述
    @Transient
    private String specTemplate;// 商品特殊规格的名称及可选值模板
    private String specialSpec;// 商品的全局规格属性
    private String genericSpec;
    private String packingList;// 包装清单
    private String afterService;// 售后服务
    // 省略getter和setter
}