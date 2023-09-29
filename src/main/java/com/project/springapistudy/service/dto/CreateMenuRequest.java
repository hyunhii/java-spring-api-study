package com.project.springapistudy.service.dto;

import com.project.springapistudy.entity.MenuType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CreateMenuRequest {

    @NotBlank
    private String name;

    @NotNull
    private MenuType type;

    @NotBlank
    private String useYN;


    public CreateMenuRequest(String name, MenuType type, String useYN) {
        this.name = name;
        this.type = type;
        this.useYN = useYN;
    }
}
