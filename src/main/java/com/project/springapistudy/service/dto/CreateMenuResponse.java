package com.project.springapistudy.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.springapistudy.entity.MenuType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

}
