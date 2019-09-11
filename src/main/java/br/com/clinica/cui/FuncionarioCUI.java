package br.com.clinica.cui;

import br.com.clinica.entity.Endereco;
import br.com.clinica.entity.Funcionario;
import br.com.clinica.entity.Usuario;
import br.com.clinica.service.EnderecoService;
import br.com.clinica.service.FuncionarioService;

import java.util.List;
import java.util.Scanner;

public class FuncionarioCUI extends GenericCUI {
    
    public void menu(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        Integer opcao = -1;
        System.out.println("--------------------------------------------------------------");
        System.out.println("** Funcionários **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();
        try {
            do {
                System.out.println(" 1 - Cadastrar");
                System.out.println();
                System.out.println(" 2 - Alterar");
                System.out.println();
                System.out.println(" 3 - Demitir");
                System.out.println();
                System.out.println(" 0 - Voltar");
                System.out.println();
                opcao = scanner.nextInt();
                switch (opcao) {
                    case 1:
                        cadastrarOuAlterar(usuario, "Cadastrar");
                        break;
                    case 2:
                        cadastrarOuAlterar(usuario, "Alterar");
                        break;
                    case 3:
                        demitir(usuario);
                        break;
                    case 0:
                        MenuCUI menuCUI = new MenuCUI();
                        menuCUI.menu(usuario);
                        break;
                    default:
                        System.err.println("Opção inválida, tente novamente");
                        break;
                }
            } while (opcao < 0 || opcao > 3);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void cadastrarOuAlterar(Usuario usuario, String metodo) {
        Scanner scanner = new Scanner(System.in);
        Funcionario funcionario = null;
        EnderecoService enderecoService = new EnderecoService();
        FuncionarioService funcionarioService = new FuncionarioService();
        UsuarioCUI usuarioCUI = new UsuarioCUI();
        String alterando = "";
        Integer resultado, idEndereco = null, idFuncionario = null;
        System.out.println("--------------------------------------------------------------");
        System.out.println("** " + metodo + " Funcionário **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();
        if (metodo.equals("Alterar")) {
            funcionario = buscarFuncionario();
            idFuncionario = funcionario.getId();
            idEndereco = funcionario.getEndereco().getId();
            alterando = " ou 0 para pular";
            System.out.println("\n" + "Novas informações" + "\n");
        }
        do {
            try {
                System.out.println("Nome Completo" + alterando + ": ");
                String nome = scanner.nextLine();
                System.out.println("CPF (somente números)" + alterando + ": ");
                Long cpf = Long.parseLong(scanner.nextLine());
                System.out.println("Data Nascimento (01/01/1999)" + alterando + ": ");
                String dataNascimento = scanner.nextLine();
                System.out.println("Data Admissão (01/01/1999)" + alterando + ": ");
                String dataAdmissao = scanner.nextLine();
                System.out.println("Salário (9999.99)" + alterando + ": ");
                Float salario = Float.parseFloat(scanner.nextLine());
                System.out.println("Função" + alterando + ": ");
                String funcao = scanner.nextLine();
                System.out.println("\n" + "* Endereço *" + "\n");
                System.out.println("Logradouro (Rua Um)" + alterando + ": ");
                String logradouro = scanner.nextLine();
                System.out.println("Número (100)" + alterando + ":");
                Integer numero = Integer.parseInt(scanner.nextLine());
                System.out.println("Bairro" + alterando + ": ");
                String bairro = scanner.nextLine();
                System.out.println("Complemento (ou 0 para pular): ");
                String complemento = scanner.nextLine();
                System.out.println("Cidade" + alterando + ": ");
                String cidade = scanner.nextLine();
                System.out.println("Estado (São Paulo)" + alterando + ": ");
                String estado = scanner.nextLine();
                System.out.println("Cep (12345678) (ou 0 para pular): ");
                Integer cep = Integer.parseInt(scanner.nextLine());
                Endereco endereco = enderecoService.save(idEndereco, logradouro, numero, bairro, complemento, cidade, estado, cep);
                funcionario = funcionarioService.save(idFuncionario, nome, cpf, dataNascimento, dataAdmissao, null,
                    salario, funcao, endereco);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            resultado = usuarioCUI.cadastrarOuAlterar(alterando, funcionario.getId());
            metodo = metodo.equals("Cadastrar") ? "cadastrar" : "alterar";
            if (resultado == 0) {
                System.err.println("Erro ao " + metodo + " funcionário");
                System.out.println("Digite:" + "\n" + "0 - Tentar novamente"  + "\n" + "1 - Voltar");
                resultado = scanner.nextInt();
            } else {
                System.out.println();
                System.out.println("Funcionário " + metodo + " com sucesso");
                System.out.println();
            }
        } while (resultado == 0);
        menu(usuario);
    }

    private void demitir(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        FuncionarioService funcionarioService = new FuncionarioService();
        Integer resposta;
        try {
            do {
                Funcionario funcionario = buscarFuncionario();
                System.out.println("Data de demissão (01/01/1999): ");
                String dataDemissao = scanner.next();
                System.out.println("Confirmar a demissão do funcionário(a) " + funcionario.getNome() + " ?" + "\n" + "1 - Sim" + "\n" + "2 - Não");
                resposta = Integer.parseInt(scanner.next());
                if (resposta == 1) {
                    if (funcionarioService.demitir(funcionario.getId(), dataDemissao)) {
                        System.out.println("\n" + "Funcionário demitido com sucesso" + "\n");
                    } else {
                        System.err.println("\n" + "Erro ao demitir funcionário" + "\n");
                        System.out.println("Buscar novamente?" + "\n" + "0 - Sim" + "\n" + "1 - Não");
                        resposta = Integer.parseInt(scanner.nextLine());
                    }
                } else {
                    System.out.println("Buscar novamente?" + "\n" + "0 - Sim" + "\n" + "1 - Não");
                    resposta = Integer.parseInt(scanner.nextLine());
                }
            } while (resposta == 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        menu(usuario);
    }

    public Funcionario buscarFuncionario() {
        Scanner scanner = new Scanner(System.in);
        FuncionarioService funcionarioService = new FuncionarioService();
        Integer idFuncionario = 0;
        try {
            do {
                System.out.println("Digite o nome do funcionário para buscar: ");
                String nomeFuncionario = scanner.nextLine();
                List<Funcionario> funcionarios = funcionarioService.findByNome(nomeFuncionario);
                if (funcionarios.isEmpty()) {
                    System.err.println("\n" + "Funcionário não encontrado" + "\n");
                } else {
                    System.out.println();
                    funcionarios.forEach(System.out::println);
                    System.out.println();
                    System.out.println("Digite o ID do funcionário ou 0 para buscar novamente");
                    idFuncionario = Integer.parseInt(scanner.nextLine());
                }
            } while (idFuncionario == 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Funcionario funcionario = funcionarioService.findById(idFuncionario);
        return funcionario;
    }
}
