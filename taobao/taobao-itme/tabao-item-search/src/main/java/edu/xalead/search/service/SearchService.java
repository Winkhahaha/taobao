package edu.xalead.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.xalead.GoodsRepository;
import edu.xalead.entity.*;
import edu.xalead.search.client.BrandClient;
import edu.xalead.search.client.CategoryClient;
import edu.xalead.search.client.GoodsClient;
import edu.xalead.search.client.SpecificationClient;
import edu.xalead.taobao.common.JsonUtils;
import edu.xalead.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SearchService {
    @Resource
    private CategoryClient categoryClient;
    @Resource
    private BrandClient brandClient;
    @Resource
    private SpecificationClient specificationClient;
    @Resource
    private GoodsRepository repository;

    @Resource
    private GoodsClient goodsClient;
    public Goods buildGoods(Spu spu){
        //查询分类
        List<Category> categories = categoryClient.queryCategoryByIds(
                                    Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
        List<String> names = new ArrayList<>();
        for (Category category : categories) {
            names.add(category.getName());
        }
        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());

        //查询sku
        List<Sku> skus = goodsClient.querySkuById(spu.getId());

        //查询规格参数
        List<SpecParam> params = specificationClient.specParam(spu.getCid3(), null, true);
        //查询商品详情
        SpuDetail spuDetail = goodsClient.querySpuDetailById(spu.getId());
        //获取通用规格参数map
        Map<Long, String> generSpec = JsonUtils.parseMap(spuDetail.getGenericSpec(), Long.class, String.class);

        //获取特有的规格参数map
        Map<Long, List<String>> specSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {
        });

        //查询规格参数
       Map<String,Object> specs = new HashMap<>();
        for (SpecParam param : params) {
            String key = param.getName();//规格参数名
            Object value = null;
            //判断规格参数是通用还是特有
            if(param.getGeneric()){
                value = generSpec.get(param.getId());
                //判断是否数据类型
                if(param.getNumeric() != null && !param.getNumeric()){
                    value = chooseSegment(value.toString(),param);
                }
            }else{
                value = specSpec.get(param.getId());
            }
            specs.put(key,value);
        }

        Goods goods = new Goods();
        String all = spu.getTitle() + StringUtils.join(names," ") + brand.getName();
        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setAll(all);
        goods.setCreateTime(spu.getCreateTime());
        goods.setBrandId(spu.getBrandId());
        goods.setSubTitle(spu.getSubTitle());

        List<Long> prices = new ArrayList<>();
        List<Map<String,Object>> skuss = new ArrayList<>();
        for (Sku sku : skus) {
            prices.add(sku.getPrice());
            Map<String,Object> sk = new HashMap<>();
            sk.put("id",sku.getId());
            sk.put("title",sku.getTitle());
            sk.put("price",sku.getPrice());
            sk.put("image",StringUtils.substringBefore(sku.getImages(),","));
            skuss.add(sk);
        }
        goods.setPrice(prices);// 所有sku的价格
        goods.setSkus(JsonUtils.serialize(skuss)); // 所有sku的json格式
        goods.setSpecs(specs);//  所有可搜索规格参数
        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public PageResult<Goods> search(SearchRequest request) {
        int page = request.getPage();
        int size = request.getSize();

        //创建查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //结果过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));
        //分页
        queryBuilder.withPageable(PageRequest.of(page,size));
        //过滤
        queryBuilder.withQuery(QueryBuilders.matchQuery("all",request.getKey()));
        //查询
        Page<Goods> result = repository.search(queryBuilder.build());
        //解析结果
        long totalPages = result.getTotalPages();
        long total = result.getTotalElements();
        List<Goods> goodsList = result.getContent();

        return new PageResult<>(total,totalPages,goodsList);
    }
}
