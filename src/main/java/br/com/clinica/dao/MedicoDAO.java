package br.com.clinica.dao;

import br.com.clinica.entity.Medico;
import br.com.clinica.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO implements GenericDAO<Medico> {

    public int insert(Medico medico) {

        int generatedKey = 0;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "INSERT INTO medicos (CRM, ID_FUNCIONARIO) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, medico.getCrm());
            preparedStatement.setInt(2, medico.getFuncionario().getId());;

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

    public void update(Medico medico) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "UPDATE medicos SET CRM = ?, ID_FUNCIONARIO = ? WHERE ID_MEDICO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, medico.getCrm());
            preparedStatement.setInt(2, medico.getFuncionario().getId());
            preparedStatement.setInt(3, medico.getId());

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Medico find(Integer id) {

        Medico medico = new Medico();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM medicos WHERE id_medico = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            medico = createMedico(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return medico;
    }

    public List<Medico> findAll() {

        List<Medico> medicos = new ArrayList<Medico>();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM medicos";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            medicos = createMedicos(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return medicos;
    }

    public void delete(Integer id) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "DELETE FROM medicos WHERE ID_MEDICO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Medico createMedico(ResultSet resultSet) {

        Medico medico = new Medico();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        try {

            if (resultSet.next()) {

                medico.setId(resultSet.getInt(1));
                medico.setCrm(resultSet.getString(2));
                medico.setFuncionario(funcionarioDAO.find(resultSet.getInt(3)));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return medico;
    }

    public List<Medico> createMedicos(ResultSet resultSet) {

        List<Medico> medicos = new ArrayList<Medico>();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        try {

            while (resultSet.next()) {

                Medico medico = new Medico();

                medico.setId(resultSet.getInt(1));
                medico.setCrm(resultSet.getString(2));
                medico.setFuncionario(funcionarioDAO.find(resultSet.getInt(3)));

                medicos.add(medico);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return medicos;
    }

    public List<Medico> findMedicosByNome(String nome) {

        List<Medico> medicos = new ArrayList<Medico>();

        nome = "%" + nome + "%";

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM medicos WHERE id_funcionario = ANY (SELECT id_funcionario FROM funcionarios " +
                    "WHERE nome LIKE ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nome);

            ResultSet resultSet = preparedStatement.executeQuery();

            medicos = createMedicos(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return medicos;
    }

    public Medico findMedicoByFuncionario(Integer id) {

        Medico medico = null;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM medicos WHERE id_funcionario = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            medico = createMedico(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return medico;
    }
}
