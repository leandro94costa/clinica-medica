package br.com.clinica.dao;

import br.com.clinica.entity.Especialidade;
import br.com.clinica.entity.Medico;
import br.com.clinica.entity.MedicoEspecialidade;
import br.com.clinica.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MedicoEspecialidadeDAO {

    public int insert(Especialidade especialidade, Medico medico) {

        int generatedKey = 0;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "INSERT INTO medico_especialidade (ID_MEDICO, ID_ESPECIALIDADE) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, medico.getId());
            preparedStatement.setInt(2, especialidade.getId());

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

    public void update(MedicoEspecialidade medicoEspecialidade) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "UPDATE medico_especialidade SET ID_MEDICO = ?, ID_ESPECIALIDADE = ? WHERE ID_MEDICO_ESPECIALIDADE = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, medicoEspecialidade.getMedico().getId());
            preparedStatement.setInt(2, medicoEspecialidade.getEspecialidade().getId());
            preparedStatement.setInt(3, medicoEspecialidade.getId());

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public MedicoEspecialidade find(Integer id) {

        MedicoEspecialidade medicoEspecialidade = new MedicoEspecialidade();
        MedicoDAO medicoDAO = new MedicoDAO();
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM medico_especialidade WHERE ID_MEDICO_ESPECIALIDADE = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                medicoEspecialidade.setId(resultSet.getInt(1));
                medicoEspecialidade.setMedico(medicoDAO.find(resultSet.getInt(2)));
                medicoEspecialidade.setEspecialidade(especialidadeDAO.find(resultSet.getInt(3)));
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return medicoEspecialidade;
    }

    public List<MedicoEspecialidade> findAll() {

        List<MedicoEspecialidade> medicoEspecialidades = new ArrayList<MedicoEspecialidade>();
        MedicoDAO medicoDAO = new MedicoDAO();
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM medico_especialidade";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                MedicoEspecialidade medicoEspecialidade = new MedicoEspecialidade();

                medicoEspecialidade.setId(resultSet.getInt(1));
                medicoEspecialidade.setMedico(medicoDAO.find(resultSet.getInt(2)));
                medicoEspecialidade.setEspecialidade(especialidadeDAO.find(resultSet.getInt(3)));

                medicoEspecialidades.add(medicoEspecialidade);
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return medicoEspecialidades;
    }

    public void delete(Integer id) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "DELETE FROM medico_especialidade WHERE ID_MEDICO_ESPECIALIDADE = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
