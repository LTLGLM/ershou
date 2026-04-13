package com.ershou.ershou.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页查询条件")
public class Pagequery {
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final Integer DEFAULT_PAGE_NUM = 1;

    @ApiModelProperty("页码")
    private Integer PageNum = DEFAULT_PAGE_NUM;

    @ApiModelProperty("每页记录数")
    private Integer pageSize = DEFAULT_PAGE_SIZE;
}
