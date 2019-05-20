package br.com.H2RestAssured.api.v1.dto;

import org.springframework.hateoas.ResourceSupport;

public class AlunoOutputDto extends ResourceSupport {

    private Long idAluno;
    private String nome;
    private String turma;

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
}
