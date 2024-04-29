package com.apirest.springsecuritydemo1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@RestController
@RequestMapping("/usuario")
public class IndexController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // http://localhost:8080/projeto-api-rest/usuario/
    @GetMapping("/")
    public ResponseEntity<String> getPaginaPrincipal(){
        return new ResponseEntity("Página Principal", HttpStatus.OK);
    }

    // http://localhost:8080/projeto-api-rest/usuario/{id}
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity getByIdUsuario(@PathVariable("id") Long id) {
        return new ResponseEntity(usuarioRepository.findById(id), HttpStatus.OK);
    }

    // http://localhost:8080/projeto-api-rest/usuario/admin/{loginAdmin}/all
    @GetMapping(value = "/admin/{loginAdmin}/all", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<List<Usuario>> getAllUsuarios(@PathVariable("loginAdmin") String loginAdmin) {

        Usuario usuarioLogin = usuarioRepository.findUserByLogin(loginAdmin)
                .orElseThrow(() -> new RuntimeException("Login: " + loginAdmin + " não encontrado"));

        // Obter usuário autenticado
        UserDetails usuarioRegistrado = obterDetalhesUsuarioLogin();

        // Verifica se o usuário autenticado é o mesmo que está sendo atualizado
        if(!usuarioLogin.getLogin().equals(usuarioRegistrado.getUsername())){
            throw new AccessDeniedException("Você não tem permissão para atualizar este usuário.");
        }

        return new ResponseEntity<List<Usuario>>(usuarioRepository.findAll(), HttpStatus.OK);
    }

    // http://localhost:8080/projeto-api-rest/usuario/create
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // Associa o cadastro do telefone ao usuário
        for(int pos=0; pos<usuario.getTelefones().size(); pos++){
            usuario.getTelefones().get(pos).setUsuario(usuario);
        }

        return new ResponseEntity<Usuario>(usuarioRepository.save(usuario), HttpStatus.CREATED);
    }

    // http://localhost:8080/projeto-api-rest/usuario/replace/admin/{loginAdmin}
    @PutMapping(value = "/replace/admin/{loginAdmin}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("loginAdmin") String loginAdmin, @RequestBody Usuario updateUsuario) {
        Usuario usuarioLogin = usuarioRepository.findUserByLogin(loginAdmin)
                .orElseThrow(() -> new RuntimeException("Login: " + loginAdmin + " não encontrado"));

        // Obter usuário autenticado
        UserDetails usuarioRegistrado = obterDetalhesUsuarioLogin();

        // Verifica se o usuário autenticado é o mesmo que está sendo atualizado
        if(!usuarioLogin.getLogin().equals(usuarioRegistrado.getUsername())){
            throw new AccessDeniedException("Você não tem permissão para atualizar este usuário.");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setId(updateUsuario.getId());
        newUsuario.setLogin(updateUsuario.getLogin());
        newUsuario.setSenha(updateUsuario.getSenha());
        newUsuario.setNome(updateUsuario.getNome());
        newUsuario.setTelefones(updateUsuario.getTelefones());
        newUsuario.setRole(updateUsuario.getRole());

        // Atualiza a senha apenas se uma nova senha foi fornecida
        if (updateUsuario.getSenha() != null && !updateUsuario.getSenha().isEmpty()) {
            newUsuario.setSenha(passwordEncoder.encode(updateUsuario.getSenha()));
        }

        // Associa o cadastro do telefone ao usuário
        for(int pos=0; pos<updateUsuario.getTelefones().size(); pos++){
            updateUsuario.getTelefones().get(pos).setUsuario(updateUsuario);
        }

        return new ResponseEntity<Usuario>(usuarioRepository.save(newUsuario), HttpStatus.OK);
    }

    // http://localhost:8080/projeto-api-rest/usuario/admin/{loginAdmin}/deletar/{idDeletar}
    @DeleteMapping(value = "/admin/{loginAdmin}/deletar/{idDeletar}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable("loginAdmin") String loginAdmin, @PathVariable("idDeletar") Long idDeletar) {


        Usuario usuarioLogin = usuarioRepository.findUserByLogin(loginAdmin)
                .orElseThrow(() -> new RuntimeException("Login: " + loginAdmin + " não encontrado"));

        // Obter usuário autenticado
        UserDetails usuarioRegistrado = obterDetalhesUsuarioLogin();

        // Verifica se o usuário autenticado é o mesmo que está sendo atualizado
        if(!usuarioLogin.getLogin().equals(usuarioRegistrado.getUsername())){
            throw new AccessDeniedException("Você não tem permissão para atualizar este usuário.");
        }

        Usuario deletarUsuario = usuarioRepository.findById(idDeletar)
                .orElseThrow(() -> new RuntimeException("id: " + idDeletar + " não encontrado"));

        usuarioRepository.delete(deletarUsuario);
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


    /*Método usado para obter as informações do usuário atualmente autenticado em uma aplicação Spring Security.
     * Ou seja ele obtêm as informações do usuário autenticado. */
    public UserDetails obterDetalhesUsuarioLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // obtém a autenticação atual do contexto de segurança atual usando SecurityContextHolder.getContext().getAuthentication().
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {    // Verifica se a autenticação não é nula e se o principal da autenticação é uma instância de UserDetails.
            return (UserDetails) authentication.getPrincipal();                                  // retorna o objeto UserDetails, que contém as informações do usuário autenticado, como o nome de usuário, senha e funções.
        }
        return null;
    }
}
