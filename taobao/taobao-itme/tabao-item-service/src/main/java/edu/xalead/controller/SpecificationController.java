package edu.xalead.controller;

import edu.xalead.entity.SpecGroup;
import edu.xalead.entity.SpecParam;
import edu.xalead.service.SpecificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/spec")
public class SpecificationController {
    @Resource
    private SpecificationService specService;
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> specGroup(@PathParam("cid") Long cid){

       return ResponseEntity.ok(specService.querySpecGroups(cid));
    }

    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> specParam(
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "searching",required = false) Boolean searching
    ){

        return ResponseEntity.ok(specService.querySpecParams(gid,cid,searching));
    }
}
