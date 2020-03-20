package edu.xalead.api;
import edu.xalead.entity.Category;
import edu.xalead.entity.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import edu.xalead.entity.Category;

import java.util.List;

public interface CategoryApi {
    @GetMapping("/category/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
