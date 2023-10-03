package com.project.springapistudy.service.dto;

import com.project.springapistudy.entity.MenuType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UpdateMenuResponse {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private MenuType type;

    @NotNull
    private boolean isUsing;

}
