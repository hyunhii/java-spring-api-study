package com.project.springapistudy.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Size(min = 1, max = 1)
    private String useYN;


    public Menu(String name, MenuType type, String useYN) {
        this.name = name;
        this.type = type;
        this.useYN = useYN;
    }
}
