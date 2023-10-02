package com.project.springapistudy.service.dto;

import com.project.springapistudy.entity.MenuType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UpdateMenuRequest {

    @NotBlank
    private String name;

    @NotNull
    private MenuType type;

    @NotNull
    private boolean isUsing;

    public UpdateMenuRequest(String name, MenuType type, boolean isUsing) {
        this.name = name;
        this.type = type;
        this.isUsing = isUsing;
    }
}
