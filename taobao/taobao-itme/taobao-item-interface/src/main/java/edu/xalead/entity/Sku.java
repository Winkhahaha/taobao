package edu.xalead.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name="tb_sku")
public class Sku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Long spu_id;
    private String images;
    private String indexes;
    private Long price;
    private String ownSpec;
    private boolean enable;
    private Date create_time;
    private Date last_update_time;
    @Transient
    private Long stock;
}
