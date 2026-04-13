package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuDto extends Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Menu> children = new ArrayList<>();

}
