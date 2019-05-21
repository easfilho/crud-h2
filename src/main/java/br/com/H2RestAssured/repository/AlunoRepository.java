package br.com.H2RestAssured.repository;

import br.com.H2RestAssured.model.Aluno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {
    List<Aluno> findByNomeContainingIgnoreCase(String nome);
}
