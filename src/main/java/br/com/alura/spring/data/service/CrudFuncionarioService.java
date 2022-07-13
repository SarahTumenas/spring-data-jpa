package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudFuncionarioService {

    private Boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final CargoRepository cargoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public CrudFuncionarioService(CargoRepository cargoRepository,
                                  FuncionarioRepository funcionarioRepository,
                                  UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.cargoRepository = cargoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner){
        while (system) {
            System.out.println("Qual ação  de Funcionário você quer executar");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Excluir");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    visualizar(scanner);
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void salvar(Scanner scanner) {
        System.out.println("Digite o nome da Funcionario");
        String nome = scanner.next();

        System.out.println("Digite o cpf do Funcionario");
        String cpf = scanner.next();

        System.out.println("Digite o salario do Funcionario");
        Double salario = scanner.nextDouble();

        System.out.println("Digite a data de contratação do Funcionario");
        String dataContratacao = scanner.next();

        System.out.println("Digite o cargoID do Funcionario");
        Long cargoId = scanner.nextLong();


        List<UnidadeTrabalho> unidades = unidades(scanner);



        Funcionario funcionario = new Funcionario();

        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());
        funcionario.setUnidadeTrabalhos(unidades);

        funcionarioRepository.save(funcionario);
        System.out.println("Funcionário salvo com sucesso");

    }

    private void atualizar(Scanner scanner){

        System.out.println("Digite o id do Funcionario");
        Long id = scanner.nextLong();

        System.out.println("Digite o nome da Funcionario");
        String nome = scanner.next();

        System.out.println("Digite o cpf do Funcionario");
        String cpf = scanner.next();

        System.out.println("Digite o salario do Funcionario");
        Double salario = scanner.nextDouble();

        System.out.println("Digite a data de contratação do Funcionario");
        String dataContratacao = scanner.next();

        System.out.println("Digite o cargoID do Funcionario");
        Long cargoId = scanner.nextLong();

        Funcionario funcionario = new Funcionario();

        funcionario.setId(id);
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());

        funcionarioRepository.save(funcionario);
        System.out.println("Funcionário atualizado com sucesso");

    }

    private void visualizar(Scanner scanner){

        System.out.println("Qual página você quer visualizar");
        Integer page = scanner.nextInt();

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "nome"));
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);

        System.out.println(funcionarios);
        System.out.println("Página atual:" + funcionarios.getNumber());
        //System.out.println("Total de páginas:" + funcionarios.getTotalPages());
        System.out.println("Total de registros:" + funcionarios.getTotalElements());
        funcionarios.forEach(unidadeTrabalho-> System.out.println(funcionarios));

    }

    private void deletar(Scanner scanner){
        System.out.println("Digite o id do Funcionario");
        Long id = scanner.nextLong();

        funcionarioRepository.deleteById(id);


        System.out.println("Funcionario deletado com sucesso");

    }

    private List<UnidadeTrabalho> unidades(Scanner scanner){
      Boolean isTrue = true;
      List<UnidadeTrabalho> unidades = new ArrayList<>();

      while (isTrue){
          System.out.println("Digite  o unidadeId (0 para sair)");
          Long unidadeId = scanner.nextLong();

          if (unidadeId != 0) {
              Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
              unidades.add(unidade.get());
          }else{
              isTrue = false;
          }

      }
        return unidades;
    }
}

