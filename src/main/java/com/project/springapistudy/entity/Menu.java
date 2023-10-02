package com.project.springapistudy.entity;

import com.project.springapistudy.service.dto.UpdateMenuRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SequenceGenerator(
        name = "MENU_SEQ_GENERATOR",
        sequenceName = "MENU_SEQ",
        initialValue = 1,
        allocationSize = 50)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_SEQ_GENERATOR")
    @Column(name = "menu_id")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 10)
    private String name;

    @Enumerated(EnumType.STRING)
    private MenuType type;

    @Column(name = "use_yn")
    private boolean isUsing;


    public Menu(String name, MenuType type, boolean isUsing) {
        this.name = name;
        this.type = type;
        this.isUsing = isUsing;
    }

    public void changeMenuInfo(UpdateMenuRequest updateRequest) {
        name = updateRequest.getName();
        type = updateRequest.getType();
        isUsing = updateRequest.isUsing();
    }

    public void changeMenuToNonUse() {
        isUsing = false;
    }
}
