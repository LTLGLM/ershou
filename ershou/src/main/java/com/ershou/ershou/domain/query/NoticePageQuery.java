package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

@Data
public class NoticePageQuery extends Pagequery {

    private String noticeTitle;

    private Integer username;
}
