package br.com.clinica.dao;

import br.com.clinica.entity.Consulta;
import br.com.clinica.util.ConnectionUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO implements GenericDAO<Consulta> {

    public int insert(Consulta consulta) {

        int generatedKey = 0;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "INSERT INTO consultas (DATA, HORARIO, VALOR, ID_PACIENTE, ID_MEDICO) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDate(1, Date.valueOf(consulta.getData()));
            preparedStatement.setTime(2, Time.valueOf(consulta.getHorario()));
            preparedStatement.setFloat(3, consulta.getValor());
            preparedStatement.setInt(4, consulta.getPaciente().getId());
            preparedStatement.setInt(5, consulta.getMedico().getId());

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

    public void update(Consulta consulta) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "UPDATE consultas SET DATA = ?, HORARIO = ?, VALOR = ?, ID_PACIENTE = ?, ID_MEDICO = ? " +
                    "WHERE ID_CONSULTA = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, Date.valueOf(consulta.getData()));
            preparedStatement.setTime(2, Time.valueOf(consulta.getHorario()));
            preparedStatement.setFloat(3, consulta.getValor());
            preparedStatement.setInt(4, consulta.getPaciente().getId());
            preparedStatement.setInt(5, consulta.getMedico().getId());
            preparedStatement.setInt(6, consulta.getId());

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Consulta find(Integer id) {

        Consulta consulta = new Consulta();
        PacienteDAO pacienteDAO = new PacienteDAO();
        MedicoDAO medicoDAO = new MedicoDAO();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM consultas WHERE ID_CONSULTA = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                consulta.setId(resultSet.getInt(1));
                consulta.setData(resultSet.getDate(2).toLocalDate());
                consulta.setHorario(resultSet.getTime(3).toLocalTime());
                consulta.setValor(resultSet.getFloat(4));
                consulta.setPaciente(pacienteDAO.find(resultSet.getInt(5)));
                consulta.setMedico(medicoDAO.find(resultSet.getInt(6)));
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return consulta;
    }

    public List<Consulta> findAll() {

        List<Consulta> consultas = new ArrayList<Consulta>();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM consultas";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            consultas = createConsultas(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return consultas;
    }

    public void delete(Integer id) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "DELETE FROM consultas WHERE id_consulta = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private List<Consulta> createConsultas(ResultSet resultSet) {

        List<Consulta> consultas = new ArrayList<Consulta>();
        PacienteDAO pacienteDAO = new PacienteDAO();
        MedicoDAO medicoDAO = new MedicoDAO();

        try {

            while (resultSet.next()) {

                Consulta consulta = new Consulta();

                consulta.setId(resultSet.getInt(1));
                consulta.setData(resultSet.getDate(2).toLocalDate());
                consulta.setHorario(resultSet.getTime(3).toLocalTime());
                consulta.setValor(resultSet.getFloat(4));
                consulta.setPaciente(pacienteDAO.find(resultSet.getInt(5)));
                consulta.setMedico(medicoDAO.find(resultSet.getInt(6)));

                consultas.add(consulta);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return consultas;
    }

    public List<Consulta> findConsultasByNomeAndData(String nome, LocalDate data) {

        List<Consulta> consultas = new ArrayList<Consulta>();

        nome = "%" + nome + "%";

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM consultas WHERE data = ? AND id_paciente = ANY (SELECT id_paciente FROM pacientes " +
                    "WHERE nome LIKE ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, Date.valueOf(data));
            preparedStatement.setString(2, nome);

            ResultSet resultSet = preparedStatement.executeQuery();

            consultas = createConsultas(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return consultas;
    }

    public List<Consulta> findConsultasByMedico(LocalDate hoje, Integer id) {

        List<Consulta> consultas = new ArrayList<Consulta>();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM consultas WHERE data = ? AND id_medico = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, Date.valueOf(hoje));
            preparedStatement.setInt(2, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            consultas = createConsultas(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return consultas;
    }

    public List<Consulta> findConsultasByPacienteAndMedico(Integer idPaciente, Integer idMedico) {

        List<Consulta> consultas = new ArrayList<Consulta>();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM consultas WHERE id_paciente = ? AND id_medico = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idPaciente);
            preparedStatement.setInt(2, idMedico);

            ResultSet resultSet = preparedStatement.executeQuery();

            consultas = createConsultas(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return consultas;
    }

    public List<Consulta> findConsultaByPeriodoAndMedico(LocalDate sqlDataInicial, LocalDate sqlDataFinal, Integer idMedico) {

        List<Consulta> consultas = new ArrayList<Consulta>();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM consultas WHERE id_medico = ? AND data BETWEEN ? AND ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, idMedico);
            preparedStatement.setDate(2, Date.valueOf(sqlDataInicial));
            preparedStatement.setDate(3, Date.valueOf(sqlDataFinal));

            ResultSet resultSet = preparedStatement.executeQuery();

            consultas = createConsultas(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return consultas;
    }

    public boolean isAvailable(Consulta consulta) {

        Boolean result = true;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT id_consulta FROM consultas WHERE data = ? AND horario = ? and id_medico = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, Date.valueOf(consulta.getData()));
            preparedStatement.setTime(2, Time.valueOf(consulta.getHorario()));
            preparedStatement.setInt(3, consulta.getMedico().getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                result = false;
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }
}
