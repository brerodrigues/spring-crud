package com.brenno.springcrud.resources;

import com.brenno.springcrud.models.Pessoa;
import com.brenno.springcrud.models.enums.Sexo;
import com.brenno.springcrud.services.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PessoaResource.class)
class PessoaResourceWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PessoaService pessoaService;

    private static final String PATH = "/pessoas/";

    private Pessoa criaPessoa() {
        return new Pessoa("Breno", Sexo.MASCULINO, "breno@silva.com",
                LocalDate.now().minusDays(1), "Pernambuco", "Brasil",
                "098062312");
    }

    @Test
    public void deveObterListaDePessoas() throws Exception {
        List<Pessoa> pessoas = new ArrayList<>();
        Pessoa pessoa = criaPessoa();
        pessoas.add(pessoa);

        when(pessoaService.findAll()).thenReturn(pessoas);

        MvcResult mvcResult = mockMvc.perform(get("/pessoas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(pessoa.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(pessoa.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sexo").value(pessoa.getSexo().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(pessoa.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].naturalidade").value(pessoa.getNaturalidade()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nacionalidade").value(pessoa.getNacionalidade()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cpf").value(pessoa.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(pessoa.getEmail()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deveObterPessoaById() throws Exception {
        Integer pessoaId = 1;
        Optional<Pessoa> pessoa = Optional.of(criaPessoa());
        when(pessoaService.findById(pessoaId)).thenReturn(pessoa);

        MvcResult mvcResult = mockMvc.perform(get(PATH + pessoaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Pessoa pessoaFromResource = objectMapper.readValue(jsonResponse, Pessoa.class);

        assertEquals(pessoa.get().getId(), pessoaFromResource.getId());
        assertEquals(pessoa.get().getNome(), pessoaFromResource.getNome());
        assertEquals(pessoa.get().getSexo(), pessoaFromResource.getSexo());
        assertEquals(pessoa.get().getEmail(), pessoaFromResource.getEmail());
        assertEquals(pessoa.get().getNaturalidade(), pessoaFromResource.getNaturalidade());
        assertEquals(pessoa.get().getNacionalidade(), pessoaFromResource.getNacionalidade());
        assertEquals(pessoa.get().getCpf(), pessoaFromResource.getCpf());
        assertEquals(pessoa.get().getEmail(), pessoaFromResource.getEmail());
    }

    @Test
    public void deveRetornarNotFoundQuandoNaoEncontrarPessoaById() throws Exception {
        Integer pessoaId = 1;
        Optional<Pessoa> pessoa = Optional.empty();
        when(pessoaService.findById(pessoaId)).thenReturn(pessoa);

        mockMvc.perform(get(PATH + pessoaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deveCriarAndRetornarNovaPessoa() throws Exception {
        Pessoa pessoa = criaPessoa();
        String pessoaJson = objectMapper.writeValueAsString(pessoa);
        when(pessoaService.save(pessoa)).thenReturn(pessoa);

        MvcResult mvcResult = mockMvc.perform(post(PATH).content(pessoaJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Pessoa pessoaFromResource = objectMapper.readValue(jsonResponse, Pessoa.class);

        assertEquals(pessoa.getId(), pessoaFromResource.getId());
        assertEquals(pessoa.getNome(), pessoaFromResource.getNome());
        assertEquals(pessoa.getSexo(), pessoaFromResource.getSexo());
        assertEquals(pessoa.getEmail(), pessoaFromResource.getEmail());
        assertEquals(pessoa.getNaturalidade(), pessoaFromResource.getNaturalidade());
        assertEquals(pessoa.getNacionalidade(), pessoaFromResource.getNacionalidade());
        assertEquals(pessoa.getCpf(), pessoaFromResource.getCpf());
        assertEquals(pessoa.getEmail(), pessoaFromResource.getEmail());
    }

}