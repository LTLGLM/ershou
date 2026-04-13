package com.ershou.ershou.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T>{
    private Long total;
    private Long pageNum;
    private int pageSize;
    private List<T> list;
    public PageResult(IPage<T> page) {
        this.total = page.getTotal();
        this.pageNum = page.getCurrent();
        this.pageSize = page.getRecords().size();
        this.list = page.getRecords();
    }
}
