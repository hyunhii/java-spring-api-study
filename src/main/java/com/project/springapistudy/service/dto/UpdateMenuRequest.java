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

    @NotBlank
    private String useYN;

    public UpdateMenuRequest(String name, MenuType type, String useYN) {
        this.name = name;
        this.type = type;
        this.useYN = useYN;
    }
}
