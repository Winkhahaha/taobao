package edu.xalead.api;

import edu.xalead.entity.Sku;
import edu.xalead.entity.Spu;
import edu.xalead.entity.SpuDetail;
import edu.xalead.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsApi {
    @GetMapping("/spu/page")
    public PageResult<Spu> querySpuByPage(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows,
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "saleable", required = false) Boolean saleable
    );

    @GetMapping("/sku/list")
    public List<Sku> querySkuById(@RequestParam("id") Long id);

    @GetMapping("/spu/detail/{id}")
    public SpuDetail querySpuDetailById(@PathVariable("id") Long id);
}
