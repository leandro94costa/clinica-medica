package br.com.clinica.cui;

import br.com.clinica.entity.*;

import java.util.Scanner;

public class MenuCUI extends GenericCUI {

    public void menu(Usuario usuario) {
        System.out.println("--------------------------------------------------------------");
        System.out.println("** Seja bem vindo(a) " + usuario.getFuncionario().getNome() + " **");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Selecione uma opção: ");
        System.out.println();
        if (usuario.getPerfil().equals(Perfil.USUARIO)) {
            menuUsuario(usuario);
        } else if (usuario.getPerfil().equals(Perfil.MEDICO)) {
            menuMedico(usuario);
        } else {
            menuAdmin(usuario);
        }
    }

    public void menuUsuario(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        Integer opcao = -1;
        try {
            do {
                System.out.println(" 1 - Consultas");
                System.out.println();
                System.out.println(" 2 - Pacientes");
                System.out.println();
                System.out.println(" 3 - Perfil");
                System.out.println();
                System.out.println(" 0 - Sair");
                System.out.println();
                opcao = Integer.parseInt(scanner.next());
                switch (opcao) {
                    case 1:
                        ConsultaCUI consultaCUI = new ConsultaCUI();
                        consultaCUI.menu(usuario);
                        break;
                    case 2:
                        PacienteCUI pacienteCUI = new PacienteCUI();
                        pacienteCUI.menu(usuario);
                        break;
                    case 3:
                        UsuarioCUI usuarioCUI = new UsuarioCUI();
                        usuarioCUI.perfil(usuario);
                        break;
                    case 0:
                        LoginCUI loginCUI = new LoginCUI();
                        loginCUI.LogIn();
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

    public void menuMedico(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        Integer opcao = -1;
        try {
            do {
                System.out.println(" 1 - Consultas");
                System.out.println();
                System.out.println(" 2 - Perfil");
                System.out.println();
                System.out.println(" 0 - Sair");
                System.out.println();
                opcao = Integer.parseInt(scanner.next());
                switch (opcao) {
                    case 1:
                        ConsultaCUI consultaCUI = new ConsultaCUI();
                        consultaCUI.menu(usuario);
                        break;
                    case 2:
                        UsuarioCUI usuarioCUI = new UsuarioCUI();
                        usuarioCUI.perfil(usuario);
                        break;
                    case 0:
                        LoginCUI loginCUI = new LoginCUI();
                        loginCUI.LogIn();
                        break;
                    default:
                        System.err.println("Opção inválida, tente novamente");
                        break;
                }
            } while (opcao < 0 || opcao > 2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void menuAdmin(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        Integer opcao = -1;
        try {
            do {
                System.out.println(" 1 - Funcionários");
                System.out.println();
                System.out.println(" 2 - Médicos");
                System.out.println();
                System.out.println(" 3 - Especialidades");
                System.out.println();
                System.out.println(" 0 - Sair");
                System.out.println();
                opcao = Integer.parseInt(scanner.next());
                switch (opcao) {
                    case 1:
                        FuncionarioCUI funcionarioCUI = new FuncionarioCUI();
                        funcionarioCUI.menu(usuario);
                        break;
                    case 2:
                        MedicoCUI medicoCUI = new MedicoCUI();
                        medicoCUI.menu(usuario);
                        break;
                    case 3:
                        EspecialidadeCUI especialidadeCUI = new EspecialidadeCUI();
                        especialidadeCUI.menu(usuario);
                        break;
                    case 0:
                        LoginCUI loginCUI = new LoginCUI();
                        loginCUI.LogIn();
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
}
