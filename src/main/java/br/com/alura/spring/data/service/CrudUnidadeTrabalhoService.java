package br.com.alura.spring.data.service;


import br.com.alura.spring.data.orm.UnidadeTrabalho;

import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CrudUnidadeTrabalhoService {
    private Boolean system = true;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual ação  de Unidade de Trabalho você quer executar");
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
        System.out.println("Digite o nome da unidade de trabalho");
        String nome = scanner.next();

        System.out.println("Digite o endereço da unidade de trabalho");
        String endereco = scanner.next();


        UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
        unidadeTrabalho.setNome(nome);
        unidadeTrabalho.setEndereco(endereco);
        unidadeTrabalhoRepository.save(unidadeTrabalho);
        System.out.println("Unidade de trabalho salva com sucesso");

    }

    private void atualizar(Scanner scanner){
        System.out.println("Digite o id da unidade de trabalho");
        Long id = scanner.nextLong();

        System.out.println("Digite o nome da unidade de trabalho");
        String nome = scanner.next();

        System.out.println("Digite o endereço da unidade de trabalho");
        String endereco = scanner.next();

        UnidadeTrabalho unidadeTrabalho = unidadeTrabalhoRepository.findById(id).get();

        unidadeTrabalho.setNome(nome);
        unidadeTrabalho.setEndereco(endereco);
        unidadeTrabalhoRepository.save(unidadeTrabalho);
        System.out.println("Unidade de Trabalho atualizada com sucesso");

    }

    private void visualizar(){

        Iterable<UnidadeTrabalho> uniades = unidadeTrabalhoRepository.findAll();
        uniades.forEach(unidadeTrabalho-> System.out.println(uniades));

    }

    private void deletar(Scanner scanner){
        System.out.println("Digite o id da Uniade de Trabalho");
        Long id = scanner.nextLong();

        unidadeTrabalhoRepository.deleteById(id);


        System.out.println("Unidade de Trabalho deletada com sucesso");

    }
}
