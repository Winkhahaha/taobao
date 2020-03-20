package edu.xalead;

import edu.xalead.entity.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {

    @Resource
    private GoodsRepository goodsRepository;
    @Resource
    private ElasticsearchTemplate template;


    // 创建索引库
    @Test
    public void test1(){
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }

}