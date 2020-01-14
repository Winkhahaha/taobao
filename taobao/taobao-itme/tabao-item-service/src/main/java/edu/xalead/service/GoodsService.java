package edu.xalead.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xalead.dao.BrandMapper;
import edu.xalead.dao.CategoryMapper;
import edu.xalead.dao.SpuDetailMapper;
import edu.xalead.dao.SpuMapper;
import edu.xalead.entity.Brand;
import edu.xalead.entity.Category;
import edu.xalead.entity.Spu;
import edu.xalead.entity.SpuDetail;
import edu.xalead.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class GoodsService {
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private SpuDetailMapper spuDetailMapper;
    @Resource
    private BrandMapper brandMapper;
    @Resource
    private CategoryMapper categoryMapper;


    public PageResult<Spu> querySpuByPage(Integer page, Integer rows, String key, Boolean saleable) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //搜索字符过滤
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%" + key + "%");
        }
        //上下架过滤
        if(saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }
        //默认排序
        example.setOrderByClause("last_update_time DESC");
        Page<Spu> spus = (Page<Spu>)spuMapper.selectByExample(example);
        //把分类和品牌id转换成字符串的名字
        transferCategoryAndBrandName(spus);
        PageResult<Spu> pr = new PageResult<>(spus.getTotal(),spus.getResult());
        return pr;
    }

    private void transferCategoryAndBrandName(Page<Spu> spus) {
        for(Spu spu : spus){
            //解析类别名称
            List<Category> categories = categoryMapper.selectByIdList(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            StringBuffer b = new StringBuffer();
            for(Category c : categories){
                b.append(c.getName()).append("/");
            }
            spu.setCname(b.toString());
            //解析品牌名称
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spu.setBname(brand.getName());
        }
    }
}
