package com.project.springapistudy.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.springapistudy.entity.MenuType;
import com.project.springapistudy.service.MenuService;
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

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "아메리카노");
        requestMap.put("type", "BEVERAGE");
        requestMap.put("useYN", "Y");
        String request = objectMapper.writeValueAsString(requestMap);


        //when
        MvcResult mvcResult = mockMvc.perform(post("/menu")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        assertThat(mvcResult.getResponse().getRedirectedUrl()).isNotNull();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("메뉴 조회")
    void findMenuOne() throws Exception {
        //given
        CreateMenuResponse response = new CreateMenuResponse(1L, "아메리카노", MenuType.BEVERAGE, "Y");

        when(menuService.findMenuById(1L)).thenReturn(response);

        //when
        MvcResult result = mockMvc.perform(get("/menu/{menuId}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().methodName("findMenuOne"))
                .andExpect(status().isOk())
                .andReturn();

        CreateMenuResponse findMenu = objectMapper.readValue(result.getResponse().getContentAsString(), CreateMenuResponse.class);

        //then
        assertThat(findMenu.getId()).isEqualTo(response.getId());
        assertThat(findMenu.getName()).isEqualTo(response.getName());
        assertThat(findMenu.getType()).isEqualTo(response.getType());
        assertThat(findMenu.getUseYN()).isEqualTo(response.getUseYN());
    }

    @Test
    @DisplayName("메뉴 전체 조회")
    void findMenuAll() throws Exception {
        //given
        ArrayList<CreateMenuResponse> menus = new ArrayList<>();
        menus.add(new CreateMenuResponse(1L, "아메리카노", MenuType.BEVERAGE,"Y"));
        menus.add(new CreateMenuResponse(2L, "라떼", MenuType.BEVERAGE,"Y"));
        menus.add(new CreateMenuResponse(3L, "카푸치노", MenuType.BEVERAGE,"Y"));
        given(menuService.findMenuAll()).willReturn(menus);

        //when
        MvcResult result = mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper1 = new ObjectMapper();
        List<CreateMenuResponse> findMenus = objectMapper1.readValue(resultString, new TypeReference<>() {});

        //then
        assertThat(findMenus.size()).isEqualTo(menus.size());

    }

    @Test
    @DisplayName("메뉴 수정")
    void updateMenu() throws Exception {
        //given
        UpdateMenuRequest updateMenuRequest = new UpdateMenuRequest("케이크", MenuType.DESSERT, "N");
        UpdateMenuResponse updateMenuResponse = new UpdateMenuResponse(1L, "케이크", MenuType.DESSERT, "N");

        given(menuService.updateMenu(1L, updateMenuRequest)).willReturn(updateMenuResponse);

        //when
        MvcResult result = mockMvc.perform(put("/menu/{menuId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateMenuRequest)))
                .andExpect(handler().methodName("updateMenu"))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(result.getResponse().getRedirectedUrl()).isNotNull();
    }

}
