package com.yx.demo.source.newspring.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class Pojo {
    private String value;
    @NotBlank
    private String detail;
}
