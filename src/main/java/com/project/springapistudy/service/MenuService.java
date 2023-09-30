package com.project.springapistudy.service;

import com.project.springapistudy.entity.Menu;
import com.project.springapistudy.repository.MenuRepository;
import com.project.springapistudy.service.dto.CreateMenuRequest;
import com.project.springapistudy.service.dto.CreateMenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public CreateMenuResponse createMenu(CreateMenuRequest request) {

        Menu menu = new Menu(request.getName(), request.getType(), request.getUseYN());
        Menu savedMenu = menuRepository.save(menu);

        return new CreateMenuResponse(savedMenu.getId(), savedMenu.getName(), savedMenu.getType(), savedMenu.getUseYN());
    }

    public CreateMenuResponse findMenuById(Long id) {
        Menu findMenu = menuRepository.findById(id).orElseThrow();

        return new CreateMenuResponse(findMenu.getId(), findMenu.getName(), findMenu.getType(), findMenu.getUseYN());
    }

}
