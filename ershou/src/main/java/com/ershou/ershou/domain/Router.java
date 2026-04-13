package com.ershou.ershou.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@Data
public class Router implements Serializable {

    private static final long serialVersionUID = 1L;

    private String path;

    private String component;

    private String name;

    private Map<String,String> meta;

    private ArrayList<Router> children;
}
