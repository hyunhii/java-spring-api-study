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

        Menu menu = new Menu(request.getName(), request.getType(), request.getUseYN());
        Menu savedMenu = menuRepository.save(menu);

        return new CreateMenuResponse(savedMenu.getId(), savedMenu.getName(), savedMenu.getType(), savedMenu.getUseYN());
    }

    public CreateMenuResponse findMenuById(Long id) {
        Menu findMenu = menuRepository.findById(id).orElseThrow();

        return new CreateMenuResponse(findMenu.getId(), findMenu.getName(), findMenu.getType(), findMenu.getUseYN());
    }

    public List<CreateMenuResponse> findMenuAll() {
        List<Menu> allMenu = menuRepository.findAll();

        return allMenu.stream()
                .map(menu -> new CreateMenuResponse(
                        menu.getId(), menu.getName(), menu.getType(), menu.getUseYN()
                )).collect(Collectors.toList());
    }

    @Transactional
    public UpdateMenuResponse updateMenu(Long id, UpdateMenuRequest updateMenuRequest) {
        Menu findMenu = menuRepository.findById(id).orElseThrow();

        findMenu.changeMenuInfo(updateMenuRequest);

        return new UpdateMenuResponse(id, findMenu.getName(), findMenu.getType(), findMenu.getUseYN());
    }

}
