package br.com.H2RestAssured.api.v1.controller;

import br.com.H2RestAssured.api.v1.V1;
import br.com.H2RestAssured.api.v1.dto.AlunoInputDto;
import br.com.H2RestAssured.api.v1.dto.AlunoOutputDto;
import br.com.H2RestAssured.api.v1.dto.AlunoUpdateTurmaDto;
import br.com.H2RestAssured.api.v1.mapper.AlunoMapper;
import br.com.H2RestAssured.model.Aluno;
import br.com.H2RestAssured.service.AlunoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlunoController implements V1 {

    private AlunoService alunoService;
    private AlunoMapper alunoMapper;

    public AlunoController(AlunoService alunoService, AlunoMapper alunoMapper) {
        this.alunoService = alunoService;
        this.alunoMapper = alunoMapper;
    }

    @GetMapping("/alunos")
    @ApiOperation(value = "Api para listar alunos",
            notes = "Lista todos os alunos.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem realizada com sucesso", response = AlunoOutputDto[].class),
    })
    public ResponseEntity<?> listar(@RequestParam(value = "nome", required = false, defaultValue = "") String nome) {
        List<Aluno> alunos = alunoService.listar(nome);
        List<AlunoOutputDto> alunosOutputDto = alunoMapper.criar(alunos);
        return ResponseEntity.ok(alunosOutputDto);
    }

    @GetMapping("/alunos/{id}")
    @ApiOperation(value = "Api para consultar aluno",
            notes = "Consulta aluno por id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta realizada com sucesso", response = AlunoOutputDto.class),
    })
    public ResponseEntity<?> consultar(@PathVariable("id") Long id) {
        Aluno aluno = alunoService.consultar(id);
        AlunoOutputDto alunoOutputDto = alunoMapper.criar(aluno);
        return ResponseEntity.ok(alunoOutputDto);
    }

    @PostMapping("/alunos")
    @ApiOperation(value = "Api incluir aluno",
            notes = "Lista todos os alunos.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Listagem realizada com sucesso", response = AlunoOutputDto.class),
    })
    public ResponseEntity<?> incluir(@RequestBody AlunoInputDto alunoInputDto) {
        ModelMapper modelMapper = new ModelMapper();
        Aluno aluno = modelMapper.map(alunoInputDto, Aluno.class);
        aluno = alunoService.incluir(aluno);
        AlunoOutputDto alunoOutputDto = alunoMapper.criar(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoOutputDto);
    }

    @RequestMapping(value = "/alunos/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Api para atualizar um aluno",
            notes = "Atualiza todos os dados do aluno.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atualzação realizada com sucesso", response = AlunoOutputDto.class),
    })
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody AlunoInputDto alunoInputDto) {
        ModelMapper modelMapper = new ModelMapper();
        Aluno aluno = modelMapper.map(alunoInputDto, Aluno.class);
        aluno = alunoService.atualizar(id, aluno);

        AlunoOutputDto alunoOutputDto = alunoMapper.criar(aluno);
        return ResponseEntity.ok(alunoOutputDto);
    }

    @PatchMapping("/alunos/{id}")
    @ApiOperation(value = "Api para atualizar um aluno",
            notes = "Atualiza a turma do aluno.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atualzação realizada com sucesso", response = AlunoOutputDto.class),
    })
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id,
                                       @RequestBody AlunoUpdateTurmaDto alunoUpdateTurmaDto) {
        ModelMapper modelMapper = new ModelMapper();
        Aluno aluno = alunoService.atualizar(id, alunoUpdateTurmaDto);
        AlunoOutputDto alunoOutputDto = alunoMapper.criar(aluno);
        return ResponseEntity.ok(alunoOutputDto);
    }

    @DeleteMapping("/alunos/{id}")
    @ApiOperation(value = "Api para excluir um aluno",
            notes = "Exclui o aluno da base de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exclusão realizada com sucesso"),
    })
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        alunoService.excluir(id);
        return ResponseEntity.ok().build();
    }
}
