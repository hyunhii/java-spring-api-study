package com.project.springapistudy.controller;

import com.project.springapistudy.service.MenuService;
import com.project.springapistudy.service.dto.CreateMenuRequest;
import com.project.springapistudy.service.dto.CreateMenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<CreateMenuResponse> saveMenu(@RequestBody CreateMenuRequest request) {
        return ResponseEntity.created(URI.create("/")).body(menuService.createMenu(request));
    }
    @GetMapping("/{menuId}")
    public ResponseEntity<CreateMenuResponse> findMenuOne(@PathVariable Long menuId) {
        return new ResponseEntity<>(menuService.findMenuById(menuId), HttpStatus.OK);
    }
}