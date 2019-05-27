package br.com.H2RestAssured.api.v1.dto;

import javax.validation.constraints.NotEmpty;

public class AlunoInputDto {

    @NotEmpty(message = "Nome é obrigatório")
    private String nome;
    private String turma;

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
