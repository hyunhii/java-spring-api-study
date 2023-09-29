package com.project.springapistudy.service.dto;

import com.project.springapistudy.entity.MenuType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CreateMenuResponse {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private MenuType type;

    @NotBlank
    private String useYN;

    public CreateMenuResponse(Long id, String name, MenuType type, String useYN) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.useYN = useYN;
    }
}
