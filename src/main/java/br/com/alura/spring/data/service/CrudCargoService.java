package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CrudCargoService {

    private Boolean system = true;
    private final CargoRepository cargoRepository;

    public CrudCargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual ação  de cargo você quer executar");
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
                    visualizar();
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
        System.out.println("Digite a descrição do cargo");
        String descricao = scanner.next();
        Cargo cargo = new Cargo();
        cargo.setDescricao(descricao);
        cargoRepository.save(cargo);
        System.out.println("Cargo salvo com sucesso");

    }

    private void atualizar(Scanner scanner){
        System.out.println("Digite o id do cargo");
        Long id = scanner.nextLong();

        System.out.println("Digite a descrição do cargo");
        String descricao = scanner.next();

        Cargo cargo = cargoRepository.findById(id).get();

        cargo.setDescricao(descricao);
        cargoRepository.save(cargo);
        System.out.println("Cargo atualizado com sucesso");

    }

    private void visualizar(){

        Iterable<Cargo> cargos = cargoRepository.findAll();
        cargos.forEach(cargo -> System.out.println(cargo));

    }

    private void deletar(Scanner scanner){
        System.out.println("Digite o id do cargo");
        Long id = scanner.nextLong();

        cargoRepository.deleteById(id);


        System.out.println("Cargo deletado com sucesso");

    }


 }



