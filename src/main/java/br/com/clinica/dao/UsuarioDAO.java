package br.com.clinica.dao;

import br.com.clinica.entity.Usuario;
import br.com.clinica.service.UsuarioService;
import br.com.clinica.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements GenericDAO<Usuario> {

    public int insert(Usuario usuario) {

        int generatedKey = 0;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "INSERT INTO usuarios (EMAIL, SENHA, PERFIL, ID_FUNCIONARIO) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getPerfil().toString());
            preparedStatement.setInt(4, usuario.getFuncionario().getId());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {

                generatedKey = resultSet.getInt(1);
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return generatedKey;
    }

    public void update(Usuario usuario) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "UPDATE usuarios SET EMAIL = ?, SENHA = ?, PERFIL = ?, ID_FUNCIONARIO = ? WHERE ID_USUARIO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getPerfil().toString());
            preparedStatement.setInt(4, usuario.getFuncionario().getId());
            preparedStatement.setInt(5, usuario.getId());

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Usuario find(Integer id) {

        Usuario usuario = null;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM usuarios WHERE ID_USUARIO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            usuario = createUsuario(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return usuario;
    }

    public List<Usuario> findAll() {

        List<Usuario> usuarios = new ArrayList<Usuario>();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        UsuarioService usuarioService = new UsuarioService();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM usuarios";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(resultSet.getInt(1));
                usuario.setEmail(resultSet.getString(2));
                usuario.setSenha(resultSet.getString(3));
                usuario.setPerfil(usuarioService.findPerfil(resultSet.getString("PERFIL")));
                usuario.setFuncionario(funcionarioDAO.find(resultSet.getInt(5)));

                usuarios.add(usuario);
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return usuarios;
    }

    public void delete(Integer id) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "DELETE FROM usuarios WHERE ID_USUARIO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private Usuario createUsuario(ResultSet resultSet) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = new Usuario();

        try {

            if (resultSet.next()) {

                usuario.setId(resultSet.getInt(1));
                usuario.setEmail(resultSet.getString(2));
                usuario.setSenha(resultSet.getString(3));
                usuario.setPerfil(usuarioService.findPerfil(resultSet.getString("PERFIL")));
                usuario.setFuncionario(funcionarioDAO.find(resultSet.getInt(5)));
            }

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return usuario;
    }

    public Usuario findByEmail(String email) {

        Usuario usuario = null;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM usuarios WHERE email = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            usuario = createUsuario(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return usuario;
    }

    public Usuario findUsuarioByFuncionario(Integer idFuncionario) {

        Usuario usuario = null;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM usuarios WHERE id_funcionario = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idFuncionario);

            ResultSet resultSet = preparedStatement.executeQuery();

            usuario = createUsuario(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return usuario;
    }
}
