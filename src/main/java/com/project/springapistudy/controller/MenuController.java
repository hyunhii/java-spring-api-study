package com.project.springapistudy.controller;

import com.project.springapistudy.service.MenuService;
import com.project.springapistudy.service.dto.CreateMenuRequest;
import com.project.springapistudy.service.dto.CreateMenuResponse;
import com.project.springapistudy.service.dto.UpdateMenuRequest;
import com.project.springapistudy.service.dto.UpdateMenuResponse;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok().body(menuService.findMenuById(menuId));
    }

    @GetMapping
    public ResponseEntity<List<CreateMenuResponse>> findMenuAll() {
        return ResponseEntity.ok().body(menuService.findMenuAll());
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<UpdateMenuResponse> updateMenu(@PathVariable Long menuId, @RequestBody UpdateMenuRequest request) {
        return ResponseEntity.created(URI.create("/" + menuId)).body(menuService.updateMenu(menuId, request));
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<UpdateMenuResponse> changeMenuToNonUse(@PathVariable Long menuId) {
        return ResponseEntity.created(URI.create("/" + menuId)).body(menuService.changeMenuToNonUse(menuId));
    }
}