package com.apirest.springsecuritydemo1.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.springsecuritydemo1.entities.Usuario;
import com.apirest.springsecuritydemo1.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class IndexController {

    private UsuarioRepository usuarioRepository;

    // http://localhost:8080/projeto-api-rest/usuario/{id}
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity getByIdUsuario(@PathVariable("id") Long id) {
        return new ResponseEntity(usuarioRepository.findById(id), HttpStatus.OK);
    }

    // http://localhost:8080/projeto-api-rest/usuario/all
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return new ResponseEntity<List<Usuario>>(usuarioRepository.findAll(), HttpStatus.OK);
    }

    // http://localhost:8080/projeto-api-rest/usuario/create
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {

        // Associa o cadastro do telefone ao usuário
        for(int pos=0; pos<usuario.getTelefones().size(); pos++){
            usuario.getTelefones().get(pos).setUsuario(usuario);
        }

        return new ResponseEntity<Usuario>(usuarioRepository.save(usuario), HttpStatus.CREATED);
    }

    // http://localhost:8080/projeto-api-rest/usuario/replace/{id}
    @PutMapping(value = "/replace/{id}", produces = "application/json")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") Long id, @RequestBody Usuario updateUsuario) {
        Usuario usuarioId = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id: " + id + " não encontrado"));

        Usuario newUsuario = new Usuario();
        newUsuario.setId(usuarioId.getId());
        newUsuario.setLogin(updateUsuario.getLogin());
        newUsuario.setSenha(updateUsuario.getSenha());
        newUsuario.setNome(updateUsuario.getNome());
        newUsuario.setTelefones(updateUsuario.getTelefones());

        // Associa o cadastro do telefone ao usuário
        for(int pos=0; pos<updateUsuario.getTelefones().size(); pos++){
            updateUsuario.getTelefones().get(pos).setUsuario(updateUsuario);
        }

        return new ResponseEntity<Usuario>(usuarioRepository.save(newUsuario), HttpStatus.OK);
    }

    // http://localhost:8080/projeto-api-rest/usuario/delete/{id}
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity deleteUsuario(@PathVariable("id") Long id) {

        Usuario usuarioId = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id: " + id + " não encontrado"));

        usuarioRepository.delete(usuarioId);
        return ResponseEntity.noContent().build();
    }

    // http://localhost:8080/projeto-api-rest/usuario/teste?nome=Daniel&sobrenome=Penelva
    @GetMapping(value = "/teste", produces = "application/json")
    public ResponseEntity seuNomeCompleto(
            @RequestParam(value = "nome", defaultValue = "nome não declarado") String nome,
            @RequestParam(value = "sobrenome", required = true, defaultValue = "sobrenome não informado") String sobrenome) {
        System.out.println("Nome: " + nome + " - Sobrenome: " + sobrenome);
        return new ResponseEntity("Nome: " + nome + " - Sobrenome: " + sobrenome, HttpStatus.OK);
    }
}
