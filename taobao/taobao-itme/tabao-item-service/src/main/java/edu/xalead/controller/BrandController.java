package edu.xalead.controller;

import edu.xalead.entity.Brand;
import edu.xalead.service.BrandService;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemException;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemExceptionEnum;
import edu.xalead.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Resource
    private BrandService brandService;
    @GetMapping("list")
    public ResponseEntity<PageResult> list(@RequestParam(name = "page",defaultValue = "1") Integer page,
                                           @RequestParam(name="rowsPerPage",defaultValue = "5") Integer pagesize,
                                           @RequestParam(name = "search",required = false) String search,
                                           @RequestParam(name="sortBy",required = false) String orderBy ,
                                           @RequestParam(name = "descending",required = false) Boolean desc){

        PageResult result = brandService.queryBrands(page,pagesize,search,orderBy,desc);
        if(result.getTotal() == 0){
            throw new TaobaoItemException(TaobaoItemExceptionEnum.EXCEPTION_ENUM_BRAND_NOT_FONUND);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("addOrUpdate")
    public ResponseEntity<Void> addOrUpdateBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        brandService.addOrUpdate(brand,cids);
        return ResponseEntity.ok(null);
    }
}
