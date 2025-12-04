package uz.pdp.lmsad.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lmsad.dto.auth.*;
import uz.pdp.lmsad.service.AuthService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "BearerAuth")
public class AdminController {


    private final AuthService authService;

    @GetMapping("/users")
    public Page<AuthUserDto> getAllUsers(
            @RequestParam(name = "page", required = false,defaultValue = "0") int page,
            @RequestParam(name = "size", required = false,defaultValue = "10") int size ) {
        return authService.getAllUsers(page,size);
    }

//todo course active
    @GetMapping("/users/{id}")
    public AuthUserDto getUser(@PathVariable String id){
        return authService.get(id);
    }

    @DeleteMapping("/users/{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        authService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/users/{id}/role")
    public AuthUserDto updateRole(@PathVariable String id, @RequestBody UpdateAuthUserDto dto){
        return authService.update(id, dto);
    }



}
