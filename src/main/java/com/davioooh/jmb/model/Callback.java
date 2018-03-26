package com.davioooh.jmb.model;

import lombok.Data;

import java.util.List;

@Data
public class Callback {
    public static final String PAGE = "page";

    private String object;
    private List<Entry> entry;

}
