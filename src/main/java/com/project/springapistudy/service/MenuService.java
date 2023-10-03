package com.project.springapistudy.service;

import com.project.springapistudy.entity.Menu;
import com.project.springapistudy.repository.MenuRepository;
import com.project.springapistudy.service.dto.CreateMenuRequest;
import com.project.springapistudy.service.dto.CreateMenuResponse;
import com.project.springapistudy.service.dto.UpdateMenuRequest;
import com.project.springapistudy.service.dto.UpdateMenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public CreateMenuResponse createMenu(CreateMenuRequest request) {

        Menu menu = new Menu(request.getName(), request.getType(), request.isUsing());
        Menu savedMenu = menuRepository.save(menu);

        return CreateMenuResponse.builder()
                .id(savedMenu.getId())
                .name(savedMenu.getName())
                .type(savedMenu.getType())
                .isUsing(savedMenu.isUsing())
                .build();
    }

    public CreateMenuResponse findMenuById(Long id) {
        Menu findMenu = menuRepository.findById(id).orElseThrow();

        return CreateMenuResponse.builder()
                .id(findMenu.getId())
                .name(findMenu.getName())
                .type(findMenu.getType())
                .isUsing(findMenu.isUsing())
                .build();
    }

    public List<CreateMenuResponse> findMenuAll() {
        List<Menu> allMenu = menuRepository.findAll();

        return allMenu.stream()
                .map(menu -> CreateMenuResponse.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .type(menu.getType())
                        .isUsing(menu.isUsing())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public UpdateMenuResponse updateMenu(Long id, UpdateMenuRequest updateMenuRequest) {
        Menu findMenu = menuRepository.findById(id).orElseThrow();

        findMenu.changeMenuInfo(updateMenuRequest);

        return UpdateMenuResponse.builder()
                .id(findMenu.getId())
                .name(findMenu.getName())
                .type(findMenu.getType())
                .isUsing(findMenu.isUsing())
                .build();
    }

    @Transactional
    public UpdateMenuResponse changeMenuToNonUse(Long id) {
        Menu findMenu = menuRepository.findById(id).orElseThrow();

        findMenu.changeMenuToNonUse();

        return UpdateMenuResponse.builder()
                .id(findMenu.getId())
                .name(findMenu.getName())
                .type(findMenu.getType())
                .isUsing(findMenu.isUsing())
                .build();
    }

}
