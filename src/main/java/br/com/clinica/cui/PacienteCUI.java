package br.com.clinica.cui;

import br.com.clinica.entity.Endereco;
import br.com.clinica.entity.Paciente;
import br.com.clinica.entity.Usuario;
import br.com.clinica.service.EnderecoService;
import br.com.clinica.service.PacienteService;

import java.util.List;
import java.util.Scanner;

public class PacienteCUI extends GenericCUI {

    @Override
    public void menu(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        Integer opcao = -1;
        System.out.println("--------------------------------------------------------------");
        System.out.println("** Pacientes **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();
        try {
            do {
                System.out.println(" 1 - Cadastrar");
                System.out.println();
                System.out.println(" 2 - Alterar");
                System.out.println();
                System.out.println(" 0 - Voltar");
                System.out.println();
                opcao = Integer.parseInt(scanner.next());
                switch (opcao) {
                    case 1:
                        cadastrarOuAlterar(usuario, "Cadastrar");
                        break;
                    case 2:
                        cadastrarOuAlterar(usuario, "Alterar");
                        break;
                    case 0:
                        MenuCUI menuCUI = new MenuCUI();
                        menuCUI.menu(usuario);
                        break;
                    default:
                        System.err.println("Opção inválida, tente novamente");
                        break;
                }
            } while (opcao < 0 || opcao > 2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        menu(usuario);
    }

    private void cadastrarOuAlterar(Usuario usuario, String metodo) {
        Scanner scanner = new Scanner(System.in);
        Paciente paciente = null;
        EnderecoService enderecoService = new EnderecoService();
        PacienteService pacienteService = new PacienteService();
        Integer resultado, idEndereco = null, idPaciente = null;
        String alterando = "";
        System.out.println("--------------------------------------------------------------");
        System.out.println("** " + metodo + " Paciente **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();
        if (metodo.equalsIgnoreCase("alterar")) {
            paciente = buscarPaciente();
            idPaciente = paciente.getId();
            idEndereco = paciente.getEndereco().getId();
            alterando = " ou 0 para pular";
            System.out.println("\n" + "Novas informações" + "\n");
        }
        try {
            do {
                System.out.println("Nome Completo" + alterando + ": ");
                String nome = scanner.nextLine();
                System.out.println("CPF (somente números)" + alterando + ": ");
                Long cpf = Long.parseLong(scanner.nextLine());
                System.out.println("Data Nascimento (01/01/1999)" + alterando + ": ");
                String dataNascimento = scanner.nextLine();
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
                Integer cep = Integer.parseInt(scanner.next());
                Endereco endereco = enderecoService.save(idEndereco, logradouro, numero, bairro, complemento, cidade, estado, cep);
                resultado = pacienteService.save(idPaciente, nome, cpf, dataNascimento, endereco);
                metodo = metodo.equalsIgnoreCase("cadastrar") ? "cadastrado" : "alterado";
                if (resultado == 0) {
                    System.err.println("Erro ao " + metodo + " paciente");
                    System.out.println("Digite:" + "\n" + "0 - Tentar novamente" + "\n" + "1 - Voltar");
                    resultado = Integer.parseInt(scanner.next());
                } else {
                    System.out.println();
                    System.out.println("Paciente " + metodo + " com sucesso");
                    System.out.println();
                }
            } while (resultado == 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        menu(usuario);
    }

    public Paciente buscarPaciente() {
        Scanner scanner = new Scanner(System.in);
        PacienteService pacienteService = new PacienteService();
        Integer idPaciente = 0;
        try {
            do {
                System.out.println("Digite o nome do paciente para buscar: ");
                String nomePaciente = scanner.nextLine();
                List<Paciente> pacientes = pacienteService.findByNome(nomePaciente);
                if (pacientes.isEmpty()) {
                    System.out.println("\n" + "Paciente não encontrado" + "\n");
                } else {
                    System.out.println();
                    pacientes.forEach(System.out::println);
                    System.out.println();
                    System.out.println("Digite o ID do paciente ou 0 para buscar novamente");
                    idPaciente = Integer.parseInt(scanner.next());
                }
            } while (idPaciente == 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Paciente paciente = pacienteService.findById(idPaciente);
        return paciente;
    }
}
