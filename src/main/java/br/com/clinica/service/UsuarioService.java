package br.com.clinica.service;

import br.com.clinica.dao.FuncionarioDAO;
import br.com.clinica.dao.UsuarioDAO;
import br.com.clinica.entity.Perfil;
import br.com.clinica.entity.Usuario;

public class UsuarioService {

    public Perfil findPerfil(String perfil) {

        if (perfil.equals(Perfil.ADMIN.toString())) {

            return Perfil.ADMIN;

        } else if (perfil.equals(Perfil.MEDICO.toString())) {

            return Perfil.MEDICO;

        } else {

            return Perfil.USUARIO;
        }
    }

    public Integer save(Integer idUsuario, String email, String senha, Integer perfil, Integer idFuncionario) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Integer result = 0;

        Usuario usuario = create(idUsuario, email, senha, perfil, idFuncionario);

        if (usuario.getId() == null) {

            result = usuarioDAO.insert(usuario);

        } else {

            usuarioDAO.update(usuario);

            if (isPersisted(usuario)) {

                result = 1;
            }
        }

        return result;
    }

    private boolean isPersisted(Usuario usuario) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuarioPersisted = usuarioDAO.find(usuario.getId());

        if (usuario.equals(usuarioPersisted)) {

            return true;

        } else {

            return false;
        }
    }

    private Usuario create(Integer idUsuario, String email, String senha, Integer perfil, Integer idFuncionario) {

        Usuario usuario = new Usuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		
		if (idUsuario != null) {
			
			usuario = usuarioDAO.find(idUsuario);
		}

		if (!email.equals("0")) {

            usuario.setEmail(email);
        }

        if (!senha.equals("0")) {

            usuario.setSenha(senha);
        }

		if (perfil != 0) {

            if (perfil == 1) {

                usuario.setPerfil(findPerfil("ADMIN"));

            } else if (perfil == 2) {

                usuario.setPerfil(findPerfil("MEDICO"));

            } else {

                usuario.setPerfil(findPerfil("USUARIO"));
            }
        }

        if (idFuncionario != 0) {

            usuario.setFuncionario(funcionarioDAO.find(idFuncionario));
        }

        return usuario;
    }

    public Usuario findByFuncionario(Integer idFuncionario) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        return usuarioDAO.findUsuarioByFuncionario(idFuncionario);
    }
}
