package com.project.springapistudy.controller;

import com.project.springapistudy.service.MenuService;
import com.project.springapistudy.service.dto.CreateMenuRequest;
import com.project.springapistudy.service.dto.CreateMenuResponse;
import com.project.springapistudy.service.dto.UpdateMenuRequest;
import com.project.springapistudy.service.dto.UpdateMenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<CreateMenuResponse>> findMenuAll() {
        return new ResponseEntity<>(menuService.findMenuAll(), HttpStatus.OK);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<UpdateMenuResponse> updateMenu(@PathVariable Long menuId, @RequestBody UpdateMenuRequest request) {
        return ResponseEntity.created(URI.create("/" + menuId)).body(menuService.updateMenu(menuId, request));
    }
}