package com.apirest.springsecuritydemo1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {

    // http://localhost:8080/api/
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity init() {
        return new ResponseEntity("Olá Mundo!", HttpStatus.OK);
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
