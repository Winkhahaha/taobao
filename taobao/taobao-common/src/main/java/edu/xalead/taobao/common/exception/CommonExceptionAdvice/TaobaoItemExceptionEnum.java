package edu.xalead.taobao.common.exception.CommonExceptionAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum TaobaoItemExceptionEnum {
    EXCEPTION_ENUM_CATEGORY_NOT_FONUND(404,"查不到商品分类！"),
    EXCEPTION_ENUM_BRAND_NOT_FONUND(404,"查不到任何品牌！"),
    EXCEPTION_ENUM_INVALID_DATA(401,"无效的数据！无法添加或更新！"),
    EXCEPTION_ENUM_INVALID_IMAGE_TYPE(405,"无效MIME类型"),
    EXCEPTION_ENUM_SPEC_GROUP_NOT_FONUND(503,"商品规格组不存在" );
    ;
    //    TaobaoItemExceptionEnum t = new TaobaoItemExceptionEnum(222,"");
    private Integer code;
    private String mesg;

}
