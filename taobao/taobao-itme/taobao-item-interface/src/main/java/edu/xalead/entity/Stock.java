package edu.xalead.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="tb_stock")
public class Stock {
    @Id
    private Long sku_id;
    private Long stock;
    private Long seckill_stock;
    private Long seckill_total;
}
