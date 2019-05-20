package br.com.H2RestAssured.service;

import br.com.H2RestAssured.api.v1.dto.AlunoUpdateTurmaDto;
import br.com.H2RestAssured.model.Aluno;
import br.com.H2RestAssured.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listar() {
        return (List<Aluno>) alunoRepository.findAll();
    }

    public Aluno consultar(Long id) {
        return alunoRepository.findById(id).get();
    }

    public Aluno incluir(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno aluno) {
        aluno.setId(id);
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Long id, AlunoUpdateTurmaDto alunoUpdateTurmaDto) {
        Aluno aluno = consultar(id);
        aluno.setTurma(alunoUpdateTurmaDto.getTurma());
        return alunoRepository.save(aluno);
    }

    public void excluir(Long id) {
        alunoRepository.deleteById(id);
    }
}
