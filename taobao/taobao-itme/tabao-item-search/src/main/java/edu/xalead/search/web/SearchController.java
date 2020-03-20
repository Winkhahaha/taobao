package edu.xalead.search.web;

import edu.xalead.entity.Goods;
import edu.xalead.entity.SearchRequest;
import edu.xalead.search.service.SearchService;
import edu.xalead.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SearchController {

    @Resource
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> pageSearch(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(searchService.search(request));
    }
}
