package com.apirest.springsecuritydemo1.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.springsecuritydemo1.entities.Usuario;

@RestController
@RequestMapping("/api")
public class IndexController {

    // http://localhost:8080/api/
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity init() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .login("daniel")
                .senha("123")
                .nome("Daniel")
                .build();

        Usuario usuario2 = Usuario.builder()
                .id(2L)
                .login("biana")
                .senha("123")
                .nome("Biana")
                .build();

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        usuarios.add(usuario2);

        return ResponseEntity.ok(usuarios);

        // ou pode fazer assim - exemplo2:
        // return new ResponseEntity(usuarios, HttpStatus.OK);

    }

    // http://localhost:8080/api/teste?nome=Daniel&sobrenome=Penelva
    @GetMapping(value = "/teste", produces = "application/json")
    public ResponseEntity seuNomeCompleto(
            @RequestParam(value = "nome", defaultValue = "nome não declarado") String nome,
            @RequestParam(value = "sobrenome", required = true, defaultValue = "sobrenome não informado") String sobrenome) {
        System.out.println("Nome: " + nome + " - Sobrenome: " + sobrenome);
        return new ResponseEntity("Nome: " + nome + " - Sobrenome: " + sobrenome, HttpStatus.OK);
    }

}
