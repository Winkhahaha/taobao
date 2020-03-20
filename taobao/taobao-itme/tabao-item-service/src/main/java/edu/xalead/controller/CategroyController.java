package edu.xalead.controller;

import edu.xalead.entity.Category;
import edu.xalead.service.CategoryService;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemException;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemExceptionEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategroyController {
    @Resource
    private CategoryService categoryService;
    @GetMapping("list")
    public ResponseEntity<List<Category>> list(Long pid){
        List<Category> list = categoryService.findCategoryByPid(pid);
        if(list == null || list.size() == 0){
            throw new TaobaoItemException(TaobaoItemExceptionEnum.EXCEPTION_ENUM_CATEGORY_NOT_FONUND);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(categoryService.queryByIds(ids));
    }
}
