package edu.xalead.api;

import edu.xalead.entity.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BrandApi {
    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable Long id);
}
