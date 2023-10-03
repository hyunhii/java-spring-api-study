package com.project.springapistudy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springapistudy.entity.MenuType;
import com.project.springapistudy.service.MenuService;
import com.project.springapistudy.service.dto.CreateMenuRequest;
import com.project.springapistudy.service.dto.CreateMenuResponse;
import com.project.springapistudy.service.dto.UpdateMenuRequest;
import com.project.springapistudy.service.dto.UpdateMenuResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MenuController.class)
@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired private WebApplicationContext context;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("메뉴 저장")
    void save() throws Exception {
        //given
        CreateMenuRequest request = new CreateMenuRequest("아메리카노", MenuType.BEVERAGE, true);

        CreateMenuResponse response = CreateMenuResponse.builder()
                .id(1L)
                .name("아메리카노")
                .type(MenuType.BEVERAGE)
                .isUsing(true)
                .build();

        given(menuService.createMenu(any())).willReturn(response);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/menu")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        assertSoftly((softly) -> {
            softly.assertThat(mvcResult.getResponse().getRedirectedUrl()).isNotNull();
            softly.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        });
    }

    @Test
    @DisplayName("메뉴 조회")
    void findMenuOne() throws Exception {
        //given
        CreateMenuResponse response = CreateMenuResponse.builder()
                .id(1L)
                .name("아메리카노")
                .type(MenuType.BEVERAGE)
                .isUsing(true)
                .build();

        when(menuService.findMenuById(1L)).thenReturn(response);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/menu/{menuId}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().methodName("findMenuOne"))
                .andExpect(status().isOk())
                .andReturn();

        CreateMenuResponse findMenu = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CreateMenuResponse.class);

        //then
        assertSoftly((softly) -> {
            softly.assertThat(findMenu.getId()).isEqualTo(response.getId());
            softly.assertThat(findMenu.getName()).isEqualTo(response.getName());
            softly.assertThat(findMenu.getType()).isEqualTo(response.getType());
            softly.assertThat(findMenu.isUsing()).isEqualTo(response.isUsing());
        });
    }

    @Test
    @DisplayName("메뉴 전체 조회")
    void findMenuAll() throws Exception {
        //given
        ArrayList<CreateMenuResponse> menus = new ArrayList<>();
        menus.add(CreateMenuResponse.builder().id(1L).name("아메리카노").type(MenuType.BEVERAGE).isUsing(true).build());
        menus.add(CreateMenuResponse.builder().id(2L).name("라떼").type(MenuType.BEVERAGE).isUsing(true).build());
        menus.add(CreateMenuResponse.builder().id(3L).name("카푸치노").type(MenuType.BEVERAGE).isUsing(true).build());
        given(menuService.findMenuAll()).willReturn(menus);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andReturn();

        String resultString = mvcResult.getResponse().getContentAsString();

        List<CreateMenuResponse> findMenus = objectMapper.readValue(resultString, new TypeReference<>() {});

        //then
        assertThat(findMenus.size()).isEqualTo(menus.size());

    }

    @Test
    @DisplayName("메뉴 수정")
    void updateMenu() throws Exception {
        //given
        UpdateMenuRequest updateMenuRequest = new UpdateMenuRequest("케이크", MenuType.DESSERT, true);

        UpdateMenuResponse updateMenuResponse = UpdateMenuResponse.builder()
                .id(1L).name("케이크")
                .type(MenuType.DESSERT)
                .isUsing(true)
                .build();

        given(menuService.updateMenu(any(), any())).willReturn(updateMenuResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(put("/menu/{menuId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMenuRequest)))
                .andExpect(handler().methodName("updateMenu"))
                .andExpect(status().isCreated())
                .andReturn();

        UpdateMenuResponse updatedMenu = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UpdateMenuResponse.class);

        //then
        assertSoftly((softly) -> {
            softly.assertThat(updatedMenu.getId()).isEqualTo(updateMenuResponse.getId());
            softly.assertThat(updatedMenu.getName()).isEqualTo(updateMenuResponse.getName());
            softly.assertThat(updatedMenu.getType()).isEqualTo(updateMenuResponse.getType());
            softly.assertThat(updatedMenu.isUsing()).isEqualTo(updateMenuResponse.isUsing());
        });
    }

    @Test
    @DisplayName("메뉴 사용 안함")
    void changeMenuToNonUse() throws Exception {
        //given
        UpdateMenuRequest updateMenuRequest = new UpdateMenuRequest("케이크", MenuType.DESSERT, false);
        UpdateMenuResponse updateMenuResponse = UpdateMenuResponse.builder()
                .id(1L).name("케이크")
                .type(MenuType.DESSERT)
                .isUsing(false)
                .build();

        given(menuService.changeMenuToNonUse(1L)).willReturn(updateMenuResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(patch("/menu/{menuId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMenuRequest)))
                .andExpect(handler().methodName("changeMenuToNonUse"))
                .andExpect(status().isCreated())
                .andReturn();

        UpdateMenuResponse nonUseMenu = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UpdateMenuResponse.class);

        //then
        assertThat(nonUseMenu.isUsing()).isEqualTo(false);
    }

}
