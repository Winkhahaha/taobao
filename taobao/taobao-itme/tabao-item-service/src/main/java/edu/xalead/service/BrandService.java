package edu.xalead.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xalead.dao.BrandMapper;
import edu.xalead.entity.Brand;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemException;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemExceptionEnum;
import edu.xalead.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BrandService {
    @Resource
    private BrandMapper brandMapper;

    public PageResult queryBrands(Integer page, Integer pagesize, String search, String orderBy, Boolean desc){
        PageHelper.startPage(page,pagesize);
        Example example = new Example(Brand.class);
        if(!StringUtils.isBlank(search)) {
            example.createCriteria().orLike("name", "%" + search + "%")
                    .orEqualTo("letter", search);
        }
        if(desc != null) {
            if (desc) example.orderBy(orderBy).desc();
            else example.orderBy(orderBy).asc();
        }
        Page<Brand> brands = (Page<Brand>) brandMapper.selectByExample(example);
        PageResult pr = new PageResult(brands.getTotal(),brands);
        return pr;
        //select * from tb_brand where name like ? or letter = ?;
    }

    public Integer addOrUpdate(Brand brand,List<Long> cids){
        Integer count = brandMapper.insert(brand);
        if(count == 0){
            throw new TaobaoItemException(TaobaoItemExceptionEnum.EXCEPTION_ENUM_INVALID_DATA);
        }
        for (Long cid : cids) {
            int c = brandMapper.insertBrandCategoryId(brand.getId(),cid);
            if(c == 0){
                throw new TaobaoItemException(TaobaoItemExceptionEnum.EXCEPTION_ENUM_INVALID_DATA);
            }
        }
        return count;
    }
}
