package br.com.H2RestAssured.api.v1.mapper;

import br.com.H2RestAssured.api.v1.controller.AlunoController;
import br.com.H2RestAssured.api.v1.dto.AlunoInputDto;
import br.com.H2RestAssured.api.v1.dto.AlunoOutputDto;
import br.com.H2RestAssured.api.v1.dto.AlunoUpdateTurmaDto;
import br.com.H2RestAssured.model.Aluno;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AlunoMapper {

    public AlunoOutputDto criar(Aluno aluno) {
        ModelMapper modelMapper = new ModelMapper();
        AlunoOutputDto alunoOutputDto = modelMapper.map(aluno, AlunoOutputDto.class);

        alunoOutputDto.add(linkTo(methodOn(AlunoController.class).consultar(aluno.getId()))
                .withRel("Consultar")
                .withType(HttpMethod.GET.name()));

        alunoOutputDto.add(linkTo(methodOn(AlunoController.class).atualizar(aluno.getId(), new AlunoInputDto()))
                .withRel("Atualizar")
                .withType(HttpMethod.PUT.name()));

        alunoOutputDto.add(linkTo(methodOn(AlunoController.class).atualizar(aluno.getId(), new AlunoUpdateTurmaDto()))
                .withRel("Atualizar Turma")
                .withType(HttpMethod.PATCH.name()));

        alunoOutputDto.add(linkTo(methodOn(AlunoController.class).excluir(aluno.getId()))
                .withRel("excluir")
                .withType(HttpMethod.DELETE.name()));

        return alunoOutputDto;
    }

    public List<AlunoOutputDto> criar (List<Aluno> alunos) {
        return alunos.stream()
                .map(this::criar)
                .collect(Collectors.toList());
    }
}
