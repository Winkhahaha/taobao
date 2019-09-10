package edu.xalead.dao;

import edu.xalead.entity.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    @Insert("insert into tb_category_brand(brand_id,category_id) values(#{bid},#{cid})")
    public Integer insertBrandCategoryId(@Param("bid") Long bid, @Param("cid") Long cid);
    @Select("select * from tb_brand b inner join tb_category_brand cb on b.id = cb.brand_id where category_id = #{cid}")
    public List<Brand> queryBrandsByCid(@Param("cid") Long cid);
}
