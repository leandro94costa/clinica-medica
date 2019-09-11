package br.com.clinica.cui;

import br.com.clinica.entity.Especialidade;
import br.com.clinica.entity.Medico;
import br.com.clinica.entity.Usuario;
import br.com.clinica.service.EspecialidadeService;
import br.com.clinica.service.MedicoEspecialidadeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EspecialidadeCUI extends GenericCUI {

    public void menu(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        Integer opcao = -1;
        System.out.println("--------------------------------------------------------------");
        System.out.println("** Especialidades **");
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
            } while (opcao < 0 && opcao > 2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void cadastrarOuAlterar(Usuario usuario, String metodo) {
        Scanner scanner = new Scanner(System.in);
        Especialidade especialidade = new Especialidade();
        EspecialidadeService especialidadeService = new EspecialidadeService();
        Integer resultado;
        System.out.println("--------------------------------------------------------------");
        System.out.println("** " + metodo + " Especialidade **");
        System.out.println("--------------------------------------------------------------");
        System.out.println();
        if (metodo.equals("Alterar")) {
            especialidade = buscarEspecialidade();
            System.out.println("\n" + "Novas informações" + "\n");
        }
        try {
            do {
                System.out.println("Descrição: ");
                String descricao = scanner.nextLine();
                especialidade.setDescricao(descricao);
                resultado = especialidadeService.save(especialidade);
                metodo = metodo.equals("Alterar") ? "alterar" : "cadastrar";
                if (resultado == 0) {
                    System.err.println("Erro ao " + metodo + " especialidade");
                    System.out.println("Digite:" + "\n" + "0 - Tentar novamente" + "\n" + "1 - Voltar");
                    resultado = Integer.parseInt(scanner.next());
                } else {
                    System.out.println();
                    System.out.println("Especialidade " + metodo + " com sucesso");
                    System.out.println();
                }
            } while (resultado == 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        menu(usuario);
    }

    public Especialidade buscarEspecialidade() {
        Scanner scanner = new Scanner(System.in);
        EspecialidadeService especialidadeService = new EspecialidadeService();
        List<Especialidade> especialidades;
        Integer idEspecialidade = 0;
        try {
            do {
                System.out.println("Digite o nome da especialidade para buscar ou 0 para sair: ");
                String nomeEspecialidade = scanner.nextLine();
                if (!nomeEspecialidade.equals("0")) {
                    especialidades = especialidadeService.findByNome(nomeEspecialidade);
                } else {
                    break;
                }
                if (especialidades.isEmpty()) {
                    System.out.println("\n" + "Especialidade não encontrada" + "\n");
                } else {
                    System.out.println();
                    especialidades.forEach(System.out::println);
                    System.out.println();
                    System.out.println("Digite o ID da especialidade ou 0 para buscar novamente: ");
                    idEspecialidade = Integer.parseInt(scanner.next());
                }
            } while (idEspecialidade == 0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Especialidade especialidade = especialidadeService.findById(idEspecialidade);
        return especialidade;
    }

    public Boolean cadastrarEspecialidadesMedico(Medico medico) {
        Scanner scanner = new Scanner(System.in);
        Boolean sair = false, result = false;
        List<Especialidade> especialidades = new ArrayList<>();
        EspecialidadeService especialidadeService = new EspecialidadeService();
        MedicoEspecialidadeService medicoEspecialidadeService = new MedicoEspecialidadeService();
        try {
            do {
                Especialidade especialidade = buscarEspecialidade();
                if (especialidade != null) {
                    System.out.println("Especialidade: " + especialidade.getDescricao() + "\n" + "1 - Adicionar" + "\n" + "0 - Sair");
                    Integer opcao = Integer.parseInt(scanner.next());
                    if (opcao == 1) {
                        especialidades.add(especialidadeService.findById(especialidade.getId()));
                    } else {
                        sair = true;
                    }
                }
            } while (!sair);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Integer especialidadesCadastradas = medicoEspecialidadeService.cadastrar(especialidades, medico);
        if (especialidades.size() == especialidadesCadastradas) {
            result = true;
        }
        return result;
    }
}
