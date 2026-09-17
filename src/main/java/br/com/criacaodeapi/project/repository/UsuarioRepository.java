package br.com.criacaodeapi.project.repository;

import br.com.criacaodeapi.project.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}