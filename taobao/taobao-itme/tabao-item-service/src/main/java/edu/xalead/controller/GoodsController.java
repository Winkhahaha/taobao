package edu.xalead.controller;

import edu.xalead.entity.Sku;
import edu.xalead.entity.Spu;
import edu.xalead.entity.SpuDetail;
import edu.xalead.service.GoodsService;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemException;
import edu.xalead.vo.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name="rows",defaultValue = "5") Integer rows,
            @RequestParam(name = "key",required = false) String key,
            @RequestParam(name = "saleable",required = false) Boolean saleable
    ){

        return ResponseEntity.ok(goodsService.querySpuByPage(page,rows,key,saleable));
    }

    @PostMapping("/goods")
    public ResponseEntity<Void> addSpu(
            @RequestBody Spu spu
    ){
        Integer count = goodsService.addSpu(spu);
        if(count == null || count == 0){
            throw new TaobaoItemException(505,"商品添加失败");
        }
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/goods")
    public ResponseEntity<Void> updateSpu(
            @RequestBody Spu spu
    ){
        Integer count = goodsService.updateSpu(spu);
        if(count == null || count == 0){
            throw new TaobaoItemException(505,"商品更新失败");
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable("id") Long id) {
        SpuDetail detail = this.goodsService.querySpuDetailById(id);
        if (detail == null) {
            throw new TaobaoItemException(HttpStatus.NOT_FOUND.value(),"商品明细找不到！");
        }
        return ResponseEntity.ok(detail);
    }

    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuById(@RequestParam("id") Long id) {
        List<Sku> detail = this.goodsService.querySkusById(id);
        if (detail == null) {
            throw new TaobaoItemException(HttpStatus.NOT_FOUND.value(),"Sku找不到！");
        }
        return ResponseEntity.ok(detail);
    }
}
