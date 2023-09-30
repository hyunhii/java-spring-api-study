package com.project.springapistudy.service;

import com.project.springapistudy.entity.MenuType;
import com.project.springapistudy.service.dto.CreateMenuRequest;
import com.project.springapistudy.service.dto.CreateMenuResponse;
import com.project.springapistudy.service.dto.UpdateMenuRequest;
import com.project.springapistudy.service.dto.UpdateMenuResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MenuServiceTest {

    @Autowired MenuService menuService;

    @Test
    @DisplayName("메뉴 등록")
    void createMenu() {
        //given
        CreateMenuRequest request = new CreateMenuRequest("아메리카노", MenuType.BEVERAGE, "Y");

        //when
        CreateMenuResponse savedMenu = menuService.createMenu(request);

        //then
        assertThat(savedMenu.getId()).isGreaterThan(0L);
        assertThat(savedMenu.getName()).isEqualTo(request.getName());
        assertThat(savedMenu.getType()).isEqualTo(request.getType());
        assertThat(savedMenu.getUseYN()).isEqualTo(request.getUseYN());
    }

    @Test
    @DisplayName("메뉴 단건 조회")
    void findMenu() {
        //given
        CreateMenuRequest request = new CreateMenuRequest("아메리카노", MenuType.BEVERAGE, "Y");
        CreateMenuResponse savedMenu = menuService.createMenu(request);

        //when
        CreateMenuResponse response = menuService.findMenuById(savedMenu.getId());

        //then
        assertThat(response.getId()).isGreaterThan(0L);
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getType()).isEqualTo(request.getType());
        assertThat(response.getUseYN()).isEqualTo(request.getUseYN());
    }

    @Test
    @DisplayName("메뉴 전체 조회")
    void findMenuAll() {
        //given
        CreateMenuRequest request1 = new CreateMenuRequest("아메리카노", MenuType.BEVERAGE, "Y");
        menuService.createMenu(request1);

        CreateMenuRequest request2 = new CreateMenuRequest("라떼", MenuType.BEVERAGE, "Y");
        menuService.createMenu(request2);

        //when
        List<CreateMenuResponse> allMenu = menuService.findMenuAll();

        //then
        assertThat(allMenu.size()).isEqualTo(2);
        assertThat(allMenu.get(0).getName()).isEqualTo(request1.getName());
        assertThat(allMenu.get(1).getName()).isEqualTo(request2.getName());
    }

    @Test
    @DisplayName("메뉴 수정")
    void updateMenu() {
        //given
        CreateMenuRequest request = new CreateMenuRequest("아메리카노", MenuType.BEVERAGE, "Y");
        CreateMenuResponse savedMenu = menuService.createMenu(request);

        UpdateMenuRequest updateRequest = new UpdateMenuRequest("치즈케이크", MenuType.DESSERT, "N");

        //when
        UpdateMenuResponse updateMenuResponse = menuService.updateMenu(savedMenu.getId(), updateRequest);

        //then
        assertThat(updateMenuResponse.getName()).isEqualTo(updateRequest.getName());
        assertThat(updateMenuResponse.getType()).isEqualTo(updateRequest.getType());
        assertThat(updateMenuResponse.getUseYN()).isEqualTo(updateRequest.getUseYN());
    }

    @Test
    @DisplayName("메뉴 사용 안함")
    void changeMenuToNonUse() {
        //given
        CreateMenuRequest request = new CreateMenuRequest("아메리카노", MenuType.BEVERAGE, "Y");
        CreateMenuResponse savedMenu = menuService.createMenu(request);

        //when
        UpdateMenuResponse updateMenuResponse = menuService.changeMenuToNonUse(savedMenu.getId());

        //then
        assertThat(updateMenuResponse.getUseYN()).isEqualTo("N");

    }


}