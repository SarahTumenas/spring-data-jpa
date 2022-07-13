package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatoriosService {

    private Boolean system = true;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FuncionarioRepository funcionarioRepository;

    public RelatoriosService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual ação  de cargo você quer executar");
            System.out.println("0 - Sair");
            System.out.println("1 - Busca Funcionario por nome");
            System.out.println("2 - Busca Funcionario por nome, data de contratação e salario maior");
            System.out.println("3 - Busca Funcionario por data de contratação");


            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    buscaFuncionarioNome(scanner);
                    break;
                case 2:
                    buscaFuncionarioSalarioMaiorData(scanner);
                    break;
                case 3:
                    buscaFuncionarioDataContratacao(scanner);
                    break;

                default:
                    system = false;
                    break;
            }
        }

    }

    private void buscaFuncionarioNome(Scanner scanner) {
        System.out.println("Digite o nome do funcionario");
        String nome = scanner.next();

        List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);

        funcionarios.forEach(System.out::println);
    }

    private void buscaFuncionarioSalarioMaiorData(Scanner scanner) {
        System.out.println("Digite o nome do funcionario");
        String nome = scanner.next();

        System.out.println("Digite o salario do funcionario");
        double salario = scanner.nextDouble();

        System.out.println("Digite a data de contratação do funcionario");
        String data = scanner.next();

        LocalDate localDate = LocalDate.parse(data, formatter);

        List<Funcionario> lista = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);

        lista.forEach(System.out::println);
    }

    private void buscaFuncionarioDataContratacao(Scanner scanner) {

        System.out.println("Digite a data de contratação que deseja pesquisar");
        String data = scanner.next();

        LocalDate localDate = LocalDate.parse(data, formatter);

        List<Funcionario> lista = funcionarioRepository.findDataContratacaoMaior(localDate);

        lista.forEach(System.out::println);
    }

}
