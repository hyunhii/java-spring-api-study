package com.project.springapistudy.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.springapistudy.entity.MenuType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateMenuResponse {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private MenuType type;

    @NotNull
    @JsonProperty("isUsing")
    private boolean isUsing;

    public CreateMenuResponse(Long id, String name, MenuType type, boolean isUsing) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isUsing = isUsing;
    }
}
