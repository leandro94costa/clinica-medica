package br.com.clinica.dao;

import br.com.clinica.entity.Paciente;
import br.com.clinica.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO implements GenericDAO<Paciente> {

    public int insert(Paciente paciente) {

        int generatedKey = 0;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "INSERT INTO pacientes (NOME, CPF, DATA_NASCIMENTO, ID_ENDERECO) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, paciente.getNome());
            preparedStatement.setLong(2, paciente.getCpf());
            preparedStatement.setDate(3, Date.valueOf(paciente.getDataNascimento()));
            preparedStatement.setInt(4, paciente.getEndereco().getId());

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

    public void update(Paciente paciente) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "UPDATE pacientes SET NOME = ?, CPF = ?, DATA_NASCIMENTO = ?, ID_ENDERECO = ? WHERE ID_PACIENTE = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, paciente.getNome());
            preparedStatement.setLong(2, paciente.getCpf());
            preparedStatement.setDate(3, Date.valueOf(paciente.getDataNascimento()));
            preparedStatement.setInt(4, paciente.getEndereco().getId());
            preparedStatement.setInt(5, paciente.getId());

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Paciente find(Integer id) {

        Paciente paciente = new Paciente();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM pacientes WHERE ID_PACIENTE = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                paciente.setId(resultSet.getInt(1));
                paciente.setNome(resultSet.getString(2));
                paciente.setCpf(resultSet.getLong(3));
                paciente.setDataNascimento(resultSet.getDate(4).toLocalDate());
                paciente.setEndereco(enderecoDAO.find(resultSet.getInt(5)));
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return paciente;
    }

    public List<Paciente> findAll() {

        List<Paciente> pacientes = new ArrayList<Paciente>();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM pacientes";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            pacientes = createPacientes(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return pacientes;
    }

    public void delete(Integer id) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "DELETE FROM pacientes WHERE ID_PACIENTE = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Paciente> createPacientes(ResultSet resultSet) {

        List<Paciente> pacientes = new ArrayList<Paciente>();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try {

            while (resultSet.next()) {

                Paciente paciente = new Paciente();

                paciente.setId(resultSet.getInt(1));
                paciente.setNome(resultSet.getString(2));
                paciente.setCpf(resultSet.getLong(3));
                paciente.setDataNascimento(resultSet.getDate(4).toLocalDate());
                paciente.setEndereco(enderecoDAO.find(resultSet.getInt(5)));

                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }

    public List<Paciente> findPacientesByNome(String nome) {

        List<Paciente> pacientes = new ArrayList<Paciente>();

        nome = "%" + nome + "%";

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM pacientes WHERE nome LIKE ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nome);

            ResultSet resultSet = preparedStatement.executeQuery();

            pacientes = createPacientes(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return pacientes;
    }
}
