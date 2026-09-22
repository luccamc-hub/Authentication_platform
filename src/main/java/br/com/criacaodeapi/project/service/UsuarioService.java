package br.com.criacaodeapi.project.service;

import br.com.criacaodeapi.project.model.Usuario;
import br.com.criacaodeapi.project.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario) {

        String senhaCriptografada =
                passwordEncoder.encode(usuario.getSenha());

        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElse(null);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());

        String senhaCriptografada =
                passwordEncoder.encode(usuarioAtualizado.getSenha());

        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    public boolean deletarUsuario(Long id) {

        if (!usuarioRepository.existsById(id)) {
            return false;
        }

        usuarioRepository.deleteById(id);
        return true;
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                .filter(usuario -> passwordEncoder.matches(senha, usuario.getSenha()));
    }
}
