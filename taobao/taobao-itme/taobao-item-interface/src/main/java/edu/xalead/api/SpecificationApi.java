package edu.xalead.api;

import edu.xalead.entity.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SpecificationApi {
    @GetMapping("/spec/params")
    public List<SpecParam> specParam(
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "searching", required = false) Boolean searching
    );
}
