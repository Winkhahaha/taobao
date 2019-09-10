package edu.xalead.service;

import edu.xalead.dao.SpecGroupMapper;
import edu.xalead.dao.SpecParamMapper;
import edu.xalead.entity.SpecGroup;
import edu.xalead.entity.SpecParam;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemException;
import edu.xalead.taobao.common.exception.CommonExceptionAdvice.TaobaoItemExceptionEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class SpecificationService {
    @Resource
    private SpecGroupMapper specGroupMapper;
    @Resource
    private SpecParamMapper specParamMapper;
    public List<SpecGroup> querySpecGroups(Long cid) {
        SpecGroup g = new SpecGroup();
        g.setCid(cid);
        List<SpecGroup> sg = specGroupMapper.select(g);
        if(sg == null || sg.size() == 0){
            throw new TaobaoItemException(TaobaoItemExceptionEnum.EXCEPTION_ENUM_SPEC_GROUP_NOT_FONUND);
        }
        return sg;
    }

    public List<SpecParam> querySpecParams(Long gid,Long cid,Boolean searching) {
        SpecParam g = new SpecParam();
        if(gid != null)
            g.setGroupId(gid);
        if(cid != null)
            g.setCid(cid);
        if(searching != null)
            g.setSearching(searching);
        List<SpecParam> sg = specParamMapper.select(g);
        if(sg == null || sg.size() == 0){
            throw new TaobaoItemException(TaobaoItemExceptionEnum.EXCEPTION_ENUM_SPEC_GROUP_NOT_FONUND);
        }
        return sg;
    }
}
