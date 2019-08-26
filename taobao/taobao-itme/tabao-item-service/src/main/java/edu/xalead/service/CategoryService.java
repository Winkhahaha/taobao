package edu.xalead.service;

import edu.xalead.dao.CategoryMapper;
import edu.xalead.entity.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    public List<Category> findCategoryByPid(long pid){
        Category c = new Category();
        c.setParentId(pid);
        return categoryMapper.select(c);
    }
}
