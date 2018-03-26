package com.davioooh.jmb.model;

import lombok.Data;

@Data
public class Messaging {
    private User sender;
    private User recipient;
    private String message;

}
