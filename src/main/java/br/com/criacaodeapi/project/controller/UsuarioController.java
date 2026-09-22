package br.com.criacaodeapi.project.controller;

import br.com.criacaodeapi.project.dto.ErroResponse;
import br.com.criacaodeapi.project.dto.LoginRequest;
import br.com.criacaodeapi.project.dto.LoginResponse;
import br.com.criacaodeapi.project.dto.UsuarioResponse;
import br.com.criacaodeapi.project.model.Usuario;
import br.com.criacaodeapi.project.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(
            @Valid @RequestBody Usuario usuario) {

        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);

        UsuarioResponse resposta =
                UsuarioResponse.fromUsuario(usuarioCriado);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {

        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios()
                .stream()
                .map(UsuarioResponse::fromUsuario)
                .toList();

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(
            @PathVariable Long id) {

        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        UsuarioResponse resposta =
                UsuarioResponse.fromUsuario(usuario);

        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuarioAtualizado) {

        Usuario usuario =
                usuarioService.atualizarUsuario(id, usuarioAtualizado);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        UsuarioResponse resposta =
                UsuarioResponse.fromUsuario(usuario);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(
            @PathVariable Long id) {

        boolean deletado = usuarioService.deletarUsuario(id);

        if (!deletado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return usuarioService.autenticar(loginRequest.getEmail(), loginRequest.getSenha())
                .<ResponseEntity<?>>map(usuario -> ResponseEntity.ok(
                        new LoginResponse("Login realizado com sucesso", UsuarioResponse.fromUsuario(usuario))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErroResponse("E-mail ou senha inválidos")));
    }
}
