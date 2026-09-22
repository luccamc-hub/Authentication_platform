package br.com.criacaodeapi.project;

import br.com.criacaodeapi.project.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeEach
	void limparBanco() {
		usuarioRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void deveExecutarCrudELoginSemExporSenha() throws Exception {
		String cadastro = """
				{"nome":"Lucca","email":"lucca@email.com","senha":"123456"}
				""";

		mockMvc.perform(post("/usuarios")
					.contentType(MediaType.APPLICATION_JSON)
					.content(cadastro))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.senha").doesNotExist());

		mockMvc.perform(get("/usuarios/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("lucca@email.com"))
				.andExpect(jsonPath("$.senha").doesNotExist());

		mockMvc.perform(get("/usuarios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value("Lucca"))
				.andExpect(jsonPath("$[0].senha").doesNotExist());

		mockMvc.perform(put("/usuarios/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"nome\":\"Lucca Atualizado\",\"email\":\"lucca.novo@email.com\",\"senha\":\"654321\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("Lucca Atualizado"))
				.andExpect(jsonPath("$.senha").doesNotExist());

		mockMvc.perform(post("/usuarios/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"email\":\"lucca.novo@email.com\",\"senha\":\"654321\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.mensagem").value("Login realizado com sucesso"))
				.andExpect(jsonPath("$.usuario.senha").doesNotExist());

		mockMvc.perform(post("/usuarios/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"email\":\"lucca.novo@email.com\",\"senha\":\"senha-errada\"}"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.mensagem").value("E-mail ou senha inválidos"));

		mockMvc.perform(delete("/usuarios/1"))
				.andExpect(status().isNoContent());
	}

}
