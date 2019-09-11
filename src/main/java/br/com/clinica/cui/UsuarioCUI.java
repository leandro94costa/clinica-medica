package br.com.clinica.cui;

import br.com.clinica.entity.Medico;
import br.com.clinica.entity.Perfil;
import br.com.clinica.entity.Usuario;
import br.com.clinica.service.MedicoService;
import br.com.clinica.service.UsuarioService;

import java.util.Scanner;

public class UsuarioCUI extends GenericCUI {

    public void menu(Usuario usuario) {
    }

    public void perfil(Usuario usuario) {
        MenuCUI menuCUI = new MenuCUI();
        if (usuario.getPerfil().equals(Perfil.MEDICO)) {
            MedicoService medicoService = new MedicoService();
            Medico medico = medicoService.findMedicoByUsuario(usuario);
            System.out.println();
            System.out.println(usuario.getFuncionario().toString());
            System.out.println(medico.toString(""));
            System.out.println();
            menuCUI.menu(usuario);
        } else {
            System.out.println();
            System.out.println(usuario.getFuncionario().toString());
            System.out.println();
            menuCUI.menu(usuario);
        }
    }

    public Integer cadastrarOuAlterar(String alterando, Integer idFuncionario) {
        Scanner scanner = new Scanner(System.in);
        Integer result = null;
        try {
            UsuarioService usuarioService = new UsuarioService();
            Integer perfil, idUsuario = null;
            System.out.println("\n" + "* Usuário do Sistema *" + "\n");
            System.out.println("E-mail (nome@email.com)" + alterando + ": ");
            String email = scanner.next();
            System.out.println("Senha" + alterando + ": ");
            String senha = scanner.next();
            do {
                System.out.println("Perfil (1 - ADMIN, 2 - MEDICO, 3 - USUARIO)" + alterando + ": ");
                perfil = Integer.parseInt(scanner.next());
            } while (perfil < 0 || perfil > 3);
            Usuario usuario = usuarioService.findByFuncionario(idFuncionario);
            if (usuario != null) {
                idUsuario = usuario.getId();
            }
            result = usuarioService.save(idUsuario, email, senha, perfil, idFuncionario);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
