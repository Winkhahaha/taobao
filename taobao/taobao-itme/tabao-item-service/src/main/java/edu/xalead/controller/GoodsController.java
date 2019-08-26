package edu.xalead.controller;

import edu.xalead.entity.Spu;
import edu.xalead.service.GoodsService;
import edu.xalead.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
