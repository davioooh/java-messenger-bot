package com.davioooh.jmb.model;

import lombok.Data;

import java.util.List;

@Data
public class Entry {
    private String id;
    private Long time;
    private List<Messaging> messaging;

}
