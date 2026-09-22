package br.com.criacaodeapi.project.dto;

public class LoginResponse {

    private final String mensagem;
    private final UsuarioResponse usuario;

    public LoginResponse(String mensagem, UsuarioResponse usuario) {
        this.mensagem = mensagem;
        this.usuario = usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }
}
