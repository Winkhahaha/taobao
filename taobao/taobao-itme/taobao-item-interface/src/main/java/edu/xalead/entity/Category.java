package edu.xalead.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent; // 注意isParent生成的getter和setter方法需要手动加上Is
    private Integer sort;
    // getter和setter略
}