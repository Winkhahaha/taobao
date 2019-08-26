package edu.xalead.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name="tb_spec_param")
@Data
public class SpecParam {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long GroupId;
    private String name;
    @Column(name="'numeric'")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;
}