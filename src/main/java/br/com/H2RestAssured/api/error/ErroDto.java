package br.com.H2RestAssured.api.error;

public class ErroDto {

    private String mensagem;

    public ErroDto() {
    }

    public ErroDto(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
