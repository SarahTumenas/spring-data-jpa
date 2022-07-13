package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.orm.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

    //DerivedQueries
    List<Funcionario> findByNome(String nome);

    //JPQL
    @Query("SELECT f FROM Funcionario f " +
            "WHERE f.nome = :nome" +
            " AND f.salario >= :salario " +
            "AND f.dataContratacao = :data")
    List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, double salario, LocalDate data);

    //NativeQueries
    @Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data",
            nativeQuery = true)
    List<Funcionario> findDataContratacaoMaior(LocalDate data);



}
