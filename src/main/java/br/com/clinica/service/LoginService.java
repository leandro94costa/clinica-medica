package br.com.clinica.service;

import br.com.clinica.cui.MenuCUI;
import br.com.clinica.dao.UsuarioDAO;
import br.com.clinica.entity.Usuario;

public class LoginService {

    public boolean Logar(String login, String senha) {

        Boolean logado = false;

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuario = usuarioDAO.findByEmail(login);

        if (usuario.getId() != null && usuario.getEmail().equals(login) && usuario.getSenha().equals(senha)
                && usuario.getFuncionario().getDataDemissao() == null) {

            MenuCUI menuCUI = new MenuCUI();

            menuCUI.menu(usuario);

            logado = true;

        } else if (usuario.getId() != null && usuario.getFuncionario().getDataDemissao() != null) {
		
			System.out.println("\n" + "Acesso negado" + "\n");
		
		} else {

            System.out.println("\n" + "Usuário ou senha incorretos, tente novamente" + "\n");
        }

        return logado;
    }
}
