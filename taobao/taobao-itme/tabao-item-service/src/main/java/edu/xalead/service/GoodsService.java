package edu.xalead.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xalead.dao.*;
import edu.xalead.entity.*;
import edu.xalead.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
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
    @Resource
    private SkuMapper skuMapper;
    @Resource
    private StockMapper stockMapper;
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

    public Integer addSpu(Spu spu) {
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(new Date());
        spu.setSaleable(true);
        spu.setValid(true);
        Integer count = spuMapper.insert(spu);

        SpuDetail detail = spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        spuDetailMapper.insert(detail);
        saveSkuAndStock(spu);
        return count;
    }

    private void saveSkuAndStock(Spu spu){
        List<Sku> skus = spu.getSkus();
        for(Sku sku : skus ){
            sku.setCreate_time(new Date());
            sku.setEnable(true);
            sku.setLast_update_time(new Date());
            sku.setSpu_id(spu.getId());
            skuMapper.insert(sku);
            Stock stock = new Stock();
            stock.setSku_id(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        }
    }

    public SpuDetail querySpuDetailById(Long spuid) {
        SpuDetail detail = new SpuDetail();
        detail.setSpuId(spuid);
        return spuDetailMapper.selectOne(detail);
    }

    public List<Sku> querySkusById(Long spuid) {
        Sku sku = new Sku();
        sku.setSpu_id(spuid);
        List<Sku> list = skuMapper.select(sku);
        for (Sku sku1 : list) {
            Stock stock = new Stock();
            stock.setSku_id(sku1.getId());
            Stock stock1 = stockMapper.selectOne(stock);
            sku1.setStock(stock1.getStock());
        }
            

        return list;
    }

    public Integer updateSpu(Spu spu) {
        Sku sku = new Sku();
        sku.setSpu_id(spu.getId());
        //查询
        List<Sku> select = skuMapper.select(sku);
        if(!CollectionUtils.isEmpty(select)){
            //删除sku和stock
            for (Sku sku1 : select) {
                Stock stock = new Stock();
                stock.setSku_id(sku1.getId());
                stock = stockMapper.selectOne(stock);
                stockMapper.delete(stock);
                skuMapper.delete(sku1);
            }
        }
        //修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);
        spuMapper.updateByPrimaryKeySelective(spu);
        //修改detail
        spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());

        //新增sku和stock
        saveSkuAndStock(spu);
        return 1;
    }
}
