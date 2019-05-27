package br.com.H2RestAssured.api.v1.controller.aluno;

import br.com.H2RestAssured.model.Aluno;
import br.com.H2RestAssured.repository.AlunoRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlunoControllerTest {

    @LocalServerPort
    private int randomPort;

    @Autowired
    private AlunoRepository alunoRepository;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = randomPort;
        alunoRepository.deleteAll();
    }

    @Test
    public void deveRetornarUmaListaDeAlunos() {
        List<Aluno> alunos = Arrays.asList(new Aluno("Caterine", "t1")
                , new Aluno("Isabele", "t2"));
        alunoRepository.saveAll(alunos);

        RestAssured
                .get("/v1/alunos")
                .then()
                .assertThat()
                .body("nome", Matchers.hasItems("Caterine", "Isabele"))
                .body("turma", Matchers.hasItems("t1", "t2"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarUmaListaDeAlunosFiltrandoPorNome() {
        List<Aluno> alunos = Arrays.asList(new Aluno("Caterine", "t1")
                , new Aluno("Isabele", "t2"));
        alunoRepository.saveAll(alunos);

        RestAssured
                .given()
                .queryParam("nome", "cat")
                .get("/v1/alunos")
                .then()
                .assertThat()
                .body("nome", Matchers.hasItems("Caterine"))
                .body("turma", Matchers.hasItems("t1"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarUmAluno() {
        Aluno aluno = alunoRepository.save(new Aluno("Sofia", "t1"));
        RestAssured
                .get("/v1/alunos/{id}", aluno.getId())
                .then()
                .body("idAluno", Matchers.equalTo(aluno.getId().intValue()))
                .body("nome", Matchers.equalTo("Sofia"))
                .body("turma", Matchers.equalTo("t1"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveIncluirUmAluno() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\"nome\": \"Stella\", \"turma\": \"t3\"}")
                .post("/v1/alunos")
                .then()
                .body("idAluno", Matchers.greaterThan(0))
                .body("nome", Matchers.equalTo("Stella"))
                .body("turma", Matchers.equalTo("t3"))
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveValidarInclusaoDeAlunoSemNome() {
          RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{ \"turma\": \"t3\"}")
                .post("/v1/alunos")
                .then()
                .body("errors[0].defaultMessage", Matchers.equalTo("Nome é obrigatório"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deveAtualizarUmAluno() {
        Aluno aluno = alunoRepository.save(new Aluno("Mariana", "t9"));
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\"nome\": \"Vitória\", \"turma\": \"t5\"}")
                .put("/v1/alunos/{id}", aluno.getId())
                .then()
                .body("nome", Matchers.equalTo("Vitória"))
                .body("turma", Matchers.equalTo("t5"))
                .body("idAluno", Matchers.equalTo(aluno.getId().intValue()))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveAtualizarATurmaDeUmAluno() {
        Aluno aluno = alunoRepository.save(new Aluno("Juliana", "t9"));
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\"turma\": \"t5\"}")
                .patch("/v1/alunos/{id}", aluno.getId())
                .then()
                .body("nome", Matchers.equalTo("Juliana"))
                .body("turma", Matchers.equalTo("t5"))
                .body("idAluno", Matchers.equalTo(aluno.getId().intValue()))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveExcluirUmAluno() {
        Aluno aluno = alunoRepository.save(new Aluno("Denise", "t9"));
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .delete("/v1/alunos/{id}", aluno.getId())
                .then()
                .statusCode(HttpStatus.OK.value());
        Assert.assertEquals(0, alunoRepository.count());
    }


}
