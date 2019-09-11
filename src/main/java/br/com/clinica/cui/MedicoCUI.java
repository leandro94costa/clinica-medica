package br.com.clinica.cui;

import br.com.clinica.entity.*;
import br.com.clinica.service.FuncionarioService;
import br.com.clinica.service.MedicoEspecialidadeService;
import br.com.clinica.service.MedicoService;

import java.util.List;
import java.util.Scanner;

public class MedicoCUI extends GenericCUI {

    public void menu(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        Integer opcao = -1;
        System.out.println("--------------------------------------------------------------");
        System.out.println("** Médicos **");
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
                opcao = scanner.nextInt();
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
            } while (opcao < 0 && opcao > 2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
	
    private void cadastrarOuAlterar(Usuario usuario, String metodo) {
        Scanner scanner = new Scanner(System.in);
        Medico medico = new Medico();
        MedicoService medicoService = new MedicoService();
        FuncionarioCUI funcionarioCUI = new FuncionarioCUI();
        EspecialidadeCUI especialidadeCUI = new EspecialidadeCUI();
        Funcionario funcionario = null;
        Integer resultado;
        Boolean result = false;
        System.out.println("--------------------------------------------------------------");
        System.out.println("** " + metodo + " Médico **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();
        if (metodo.equalsIgnoreCase("alterar")) {
            medico = buscarMedico();
            funcionario = medicoService.findFuncionario(medico.getFuncionario().getId());
            System.out.println("\n" + "Novas informações" + "\n");
        }
        try {
            do {
                if (metodo.equalsIgnoreCase("cadastrar")) {
                    funcionario = funcionarioCUI.buscarFuncionario();
                }
                System.out.println("CRM (CRM/SP 12345): ");
                String crm = scanner.nextLine();
                medico.setCrm(crm);
                medico.setFuncionario(funcionario);
                medico = medicoService.save(medico);
                if (medico.getId() != null) {
                    result = true;
                    if (metodo.equalsIgnoreCase("Cadastrar")) {
                        result = especialidadeCUI.cadastrarEspecialidadesMedico(medico);
                    }
                }
                metodo = metodo.equalsIgnoreCase("cadastrar") ? "cadastrado" : "alterado";
                if (result) {
                    System.out.println();
                    System.out.println("Médico " + metodo + " com sucesso");
                    System.out.println();
                    resultado = 1;
                } else {
                    System.err.println("Erro ao " + metodo + " médico");
                    System.out.println("Digite:" + "\n" + "0 - Tentar novamente" + "\n" + "1 - Voltar");
                    resultado = Integer.parseInt(scanner.next());
                }
            } while (resultado == 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        menu(usuario);
    }

    public Medico buscarMedico() {
        Scanner scanner = new Scanner(System.in);
        MedicoService medicoService = new MedicoService();
        Integer idMedico = 0;
        try {
            do {
                System.out.println("Digite o nome do medico para buscar: ");
                String nomeMedico = scanner.nextLine();
                List<Medico> medicos = medicoService.findByNome(nomeMedico);
                if (medicos.isEmpty()) {
                    System.out.println("\n" + "Medico não encontrado" + "\n");
                } else {
                    System.out.println();
                    medicos.forEach(System.out::println);
                    System.out.println();
                    System.out.println("Digite o ID do médico ou 0 para buscar novamente");
                    idMedico = Integer.parseInt(scanner.next());
                }
            } while (idMedico == 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Medico medico = medicoService.findById(idMedico);
        return medico;
    }
}
