package edu.xalead.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_brand")
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private String letter; // 注意isParent生成的getter和setter方法需要手动加上Is

    // getter和setter略
}