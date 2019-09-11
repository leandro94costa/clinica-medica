package br.com.clinica.cui;

import br.com.clinica.entity.*;
import br.com.clinica.service.ConsultaService;
import br.com.clinica.service.PacienteService;
import br.com.clinica.util.FormatterUtil;

import java.text.ParseException;
import java.util.*;

public class ConsultaCUI extends GenericCUI {

    public void menu(Usuario usuario) {

        Scanner scanner = new Scanner(System.in);
        Integer opcao = -1;

        System.out.println("--------------------------------------------------------------");
        System.out.println("** Consultas **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();

        try {
        if (usuario.getPerfil().equals(Perfil.USUARIO)) {

            do {

                System.out.println(" 1 - Agendar");
                System.out.println();
                System.out.println(" 2 - Alterar");
                System.out.println();
                System.out.println(" 3 - Desmarcar");
                System.out.println();
                System.out.println(" 0 - Voltar");
                System.out.println();

                opcao = Integer.parseInt(scanner.next());

            } while (opcao < 0 || opcao > 3);

            switch (opcao) {

                case 1:
                    agendarOuAlterarConsulta(usuario, "agendar");
                    break;

                case 2:
                    agendarOuAlterarConsulta(usuario, "alterar");
                    break;

                case 3:
                    deletarConsulta(usuario);
                    break;

                case 0:
                    MenuCUI menuCUI = new MenuCUI();
                    menuCUI.menu(usuario);
                    break;

                default:
                    System.out.println("Digite uma opção válida");
                    break;
            }

        } else {

            do {

                System.out.println(" 1 - Buscar");
                System.out.println();
                System.out.println(" 0 - Voltar");
                System.out.println();

                opcao = Integer.parseInt(scanner.next());

            } while (opcao != 0 && opcao != 1);

            switch (opcao) {

                case 1:
                    buscarConsulta(usuario);
                    break;

                case 0:
                    MenuCUI menuCUI = new MenuCUI();
                    menuCUI.menu(usuario);
                    break;

                default:
                    System.err.println("Digite uma opção válida");
                    break;
            }
        }

        } catch (Exception e) {

            System.err.println(e.getMessage());
        }
    }

    private void agendarOuAlterarConsulta(Usuario usuario, String metodo) throws ParseException {

        Scanner scanner = new Scanner(System.in);
        ConsultaService consultaService = new ConsultaService();
        FormatterUtil formatterUtil = new FormatterUtil();
        Consulta consulta = new Consulta();
        Paciente paciente = null;
        Medico medico;
        PacienteCUI pacienteCUI = new PacienteCUI();
        MedicoCUI medicoCUI = new MedicoCUI();
        Integer idConsulta = null, resultado;
        List<Consulta> consultas;
        String alterando = "";

        String titulo = metodo.substring(1);

        System.out.println("--------------------------------------------------------------");
        System.out.println("** A" + titulo + " Consulta **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();

        try {

            if (metodo.equals("alterar")) {

                alterando = " ou 0 para pular";

                do {

                    paciente = pacienteCUI.buscarPaciente();

                    System.out.println("Data consulta (dd/mm/aaaa): ");
                    String data = scanner.next();

                    consultas = consultaService.findByByNomeAndData(paciente.getNome(), data);

                    if (!consultas.isEmpty()) {

                        System.out.println();
                        consultas.forEach(System.out::println);
                        System.out.println();

                        System.out.println("Digite o ID da consulta ou 0 para buscar novamente");
                        idConsulta = Integer.parseInt(scanner.next());

                        if (idConsulta == 0) {

                            consultas.clear();

                        } else {

                            consulta = consultaService.find(idConsulta);

                            idConsulta = consulta.getId();

                            System.out.println("\n" + "Novas informações" + "\n");
                        }
                    }

                } while (consultas.isEmpty());
            }

            do {

                System.out.println("Data (dd/mm/aaaa)" + alterando + ": ");
                String data = scanner.next();

                System.out.println("Horario (hh:mm)" + alterando + ": ");
                String horario = scanner.next();

                System.out.println("Valor (10.00)" + alterando + ": ");
                Float valor = Float.parseFloat(scanner.next());

                if (metodo.equals("agendar")) {

                    paciente = pacienteCUI.buscarPaciente();
                    medico = medicoCUI.buscarMedico();

                } else {

                    System.out.println("Trocar médico?" + "\n" + "1 - Sim" + "\n" + "2 - Não");
                    Integer trocar = Integer.parseInt(scanner.next());

                    if (trocar == 1) {

                        medico = medicoCUI.buscarMedico();

                    } else {

                        medico = consulta.getMedico();
                    }
                }

                resultado = consultaService.save(idConsulta,data, horario, valor, paciente, medico);

                if (resultado == -1) {

                    System.err.println("Horario indisponível para esse médico");
                    System.out.println("Digite:" + "\n" + "0 - Tentar novamente" + "\n" + "1 - Voltar");
                    resultado = Integer.parseInt(scanner.next());

                } else if (resultado == 0) {

                    System.err.println("Erro ao " + metodo + " consulta");
                    System.out.println("Digite:" + "\n" + "0 - Tentar novamente" + "\n" + "1 - Voltar");
                    resultado = Integer.parseInt(scanner.next());

                } else {

                    metodo = metodo.equals("agendar") ? "agendada" : "alterada";

                    System.out.println();
                    System.out.println("Consulta " + metodo + " com sucesso");
                    System.out.println();
                }

            } while (resultado == 0 || resultado == -1);

        } catch (Exception e) {

            System.err.println(e.getMessage());
        }

        menu(usuario);
    }

    private void deletarConsulta(Usuario usuario) {

        Scanner scanner = new Scanner(System.in);
        ConsultaService consultaService = new ConsultaService();
        Integer idConsulta = 0, resposta;

        System.out.println("--------------------------------------------------------------");
        System.out.println("** Desmarcar Consulta **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();

        do {

            try {

                System.out.println("Nome do paciente: ");
                String nome = scanner.nextLine();

                System.out.println("Data (dd/mm/aaaa): ");
                String data = scanner.next();

                List<Consulta> consultas = consultaService.findByByNomeAndData(nome, data);

                if (consultas.isEmpty()) {

                    System.out.println("\n" + "Consulta não encontrada" + "\n");

                } else {

                    System.out.println();
                    consultas.forEach(System.out::println);
                    System.out.println();

                    System.out.println("Digite o ID da consulta ou 0 para procurar novamente");
                    idConsulta = Integer.parseInt(scanner.next());

                    if (idConsulta != 0) {

                        System.out.println("Deseja realmente remover essa consulta?" + "\n" + "1 - Sim" + "\n" + "2 - Não");
                        resposta = Integer.parseInt(scanner.next());

                        if (resposta == 1) {

                            System.out.println(consultaService.delete(idConsulta));

                        } else {

                            do {

                                System.out.println("Buscar novamente?" + "\n" + "1 - Sim" + "\n" + "2 - Não");
                                resposta = Integer.parseInt(scanner.next());

                                if (resposta == 1) {

                                    idConsulta = 0;

                                } else if (resposta == 2) {

                                    break;

                                } else {

                                    System.err.println("Opção inválida");
                                }

                            } while (resposta != 1 && resposta != 2);
                        }
                    }
                }

            } catch (Exception e) {

                System.err.println(e.getMessage());
            }

        } while (idConsulta == 0);

        menu(usuario);
    }

    private void buscarConsulta(Usuario usuario) {

        Scanner scanner = new Scanner(System.in);
        ConsultaService consultaService = new ConsultaService();
        PacienteService pacienteService = new PacienteService();
        Integer opcao = -1, idPaciente = 0;

        System.out.println("--------------------------------------------------------------");
        System.out.println("** Buscar Consultas **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();

        try {

            do {

                System.out.println("1 - Hoje");
                System.out.println();
                System.out.println("2 - Por paciente");
                System.out.println();
                System.out.println("3 - Por data");
                System.out.println();
                System.out.println("0 - Voltar");
                System.out.println();

                opcao = Integer.parseInt(scanner.next());

            } while (opcao < 0 || opcao > 3);

            if (opcao == 1) {

                List<Consulta> consultas = consultaService.findConsultasHoje(usuario);

                if (consultas.isEmpty()) {

                    System.out.println("\n" + "Sem consultas para hoje" + "\n");

                    buscarConsulta(usuario);

                } else {

                    System.out.println();
                    consultas.forEach(System.out::println);
                    System.out.println();

                    buscarConsulta(usuario);
                }

            } else if (opcao == 2) {

                do {

                    System.out.println("Nome do paciente: ");
                    String nome = scanner.nextLine();

                    List<Paciente> pacientes = pacienteService.findByNome(nome);

                    if (pacientes.isEmpty()) {

                        System.out.println("\n" + "Paciente não encontrado" + "\n");

                        buscarConsulta(usuario);

                    } else {

                        System.out.println();
                        pacientes.forEach(System.out::println);
                        System.out.println();

                        System.out.println("Digite o ID do paciente ou 0 para procurar novamente");
                        idPaciente = Integer.parseInt(scanner.next());

                        List<Consulta> consultas = consultaService.findConsultasByPacienteAndMedico(idPaciente, usuario);

                        if (consultas.isEmpty()) {

                            System.out.println("\n" + "Você não tem consultas com esse paciente" + "\n");

                        } else {

                            System.out.println();
                            consultas.forEach(System.out::println);
                            System.out.println();

                            buscarConsulta(usuario);
                        }
                    }

                } while (idPaciente == 0);

            } else if (opcao == 3) {

                System.out.println("Data Inicial (dd/mm/aaaa): ");
                String dataInicial = scanner.next();

                System.out.println("Data Final (dd/mm/aaaa): ");
                String dataFinal = scanner.next();

                List<Consulta> consultas = consultaService.findByPeriodoAndMedico(dataInicial, dataFinal, usuario);

                if (consultas.isEmpty()) {

                    System.out.println("\n" + "Você não tem consultas nesse periodo" + "\n");

                } else {

                    System.out.println();
                    consultas.forEach(System.out::println);
                    System.out.println();

                    buscarConsulta(usuario);
                }

            } else {

                MenuCUI menuCUI = new MenuCUI();
                menuCUI.menu(usuario);
            }

        } catch (Exception e) {

            System.err.println(e.getMessage());
        }

        menu(usuario);
    }
}
