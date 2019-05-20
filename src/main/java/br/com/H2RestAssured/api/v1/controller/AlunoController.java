package br.com.H2RestAssured.api.v1.controller;

import br.com.H2RestAssured.api.v1.V1;
import br.com.H2RestAssured.api.v1.dto.AlunoInputDto;
import br.com.H2RestAssured.api.v1.dto.AlunoOutputDto;
import br.com.H2RestAssured.api.v1.dto.AlunoUpdateTurmaDto;
import br.com.H2RestAssured.model.Aluno;
import br.com.H2RestAssured.service.AlunoService;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@ExposesResourceFor(Aluno.class)
public class AlunoController implements V1 {

    private AlunoService alunoService;
    private EntityLinks entityLinks;

    public AlunoController(AlunoService alunoService, EntityLinks entityLinks) {
        this.alunoService = alunoService;
        this.entityLinks = entityLinks;
    }

    @GetMapping("/alunos")
    public ResponseEntity<?> listar() {
        ModelMapper modelMapper = new ModelMapper();
        List<AlunoOutputDto> alunosOutputDto = alunoService.listar()
                .stream()
                .map(aluno -> modelMapper.map(aluno, AlunoOutputDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(alunosOutputDto);
    }

    @GetMapping("/alunos/{id}")
    public ResponseEntity<?> consultar(@PathVariable("id") Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Aluno aluno = alunoService.consultar(id);

        AlunoOutputDto alunoOutputDto = modelMapper.map(aluno, AlunoOutputDto.class);

        Link selfLink = entityLinks.linkToSingleResource(aluno);
        alunoOutputDto.add(selfLink.withSelfRel());
        alunoOutputDto.add(selfLink.withSelfRel().withHref("/alunos/" + aluno.getId()).withType("PUT"));
        alunoOutputDto.add(selfLink.withSelfRel().withHref("/alunos/" + aluno.getId()).withType("PATCH"));
        alunoOutputDto.add(selfLink.withSelfRel().withHref("/alunos/" + aluno.getId()).withType("DELETE"));

        return ResponseEntity.ok(alunoOutputDto);
    }

    @PostMapping("/alunos")
    public ResponseEntity<?> incluir(@RequestBody AlunoInputDto alunoInputDto) {
        ModelMapper modelMapper = new ModelMapper();
        Aluno aluno = modelMapper.map(alunoInputDto, Aluno.class);
        aluno = alunoService.incluir(aluno);
        AlunoOutputDto alunoOutputDto = modelMapper.map(aluno, AlunoOutputDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoOutputDto);
    }

    @RequestMapping(value = "/alunos/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody AlunoInputDto alunoInputDto) {
        ModelMapper modelMapper = new ModelMapper();
        Aluno aluno = modelMapper.map(alunoInputDto, Aluno.class);
        aluno = alunoService.atualizar(id, aluno);

        AlunoOutputDto alunoOutputDto = modelMapper.map(aluno, AlunoOutputDto.class);
        return ResponseEntity.ok(alunoOutputDto);
    }

    @PatchMapping("/alunos/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id,
                                       @RequestBody AlunoUpdateTurmaDto alunoUpdateTurmaDto) {
        ModelMapper modelMapper = new ModelMapper();
        Aluno aluno = alunoService.atualizar(id, alunoUpdateTurmaDto);
        AlunoOutputDto alunoOutputDto = modelMapper.map(aluno, AlunoOutputDto.class);
        return ResponseEntity.ok(alunoOutputDto);
    }

    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        alunoService.excluir(id);
        return ResponseEntity.ok().build();
    }
}
