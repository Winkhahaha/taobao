package edu.xalead.dao;

import edu.xalead.entity.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {
    @Insert("insert into tb_category_brand(brand_id,category_id) values(#{bid},#{cid})")
    public Integer insertBrandCategoryId(@Param("bid") Long bid, @Param("cid") Long cid);
}
