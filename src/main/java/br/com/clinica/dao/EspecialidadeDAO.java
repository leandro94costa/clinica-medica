package br.com.clinica.dao;

import br.com.clinica.entity.Especialidade;
import br.com.clinica.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAO implements GenericDAO<Especialidade> {

    public int insert(Especialidade especialidade) {

        int generatedKey = 0;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "INSERT INTO especialidades (DESCRICAO) VALUES (?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, especialidade.getDescricao());

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

    public void update(Especialidade especialidade) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "UPDATE especialidades SET DESCRICAO = ? WHERE ID_ESPECIALIDADE = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, especialidade.getDescricao());
            preparedStatement.setInt(2, especialidade.getId());

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Especialidade find(Integer id) {

        Especialidade especialidade = new Especialidade();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM especialidades WHERE ID_ESPECIALIDADE = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                especialidade.setId(resultSet.getInt(1));
                especialidade.setDescricao(resultSet.getString(2));
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return especialidade;
    }

    public List<Especialidade> findAll() {

        List<Especialidade> especialidades = new ArrayList<>();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM especialidades";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            especialidades = createEspecialidades(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return especialidades;
    }

    public void delete(Integer id) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "DELETE FROM especialidades WHERE ID_ESPECIALIDADE = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private List<Especialidade> createEspecialidades(ResultSet resultSet) {

        List<Especialidade> especialidades = new ArrayList<Especialidade>();

        try {

            while (resultSet.next()) {

                Especialidade especialidade = new Especialidade();

                especialidade.setId(resultSet.getInt(1));
                especialidade.setDescricao(resultSet.getString(2));

                especialidades.add(especialidade);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return especialidades;
    }

    public List<Especialidade> findEspecialidadesByNome(String nome) {

        List<Especialidade> especialidades = new ArrayList<Especialidade>();

        nome = "%" + nome + "%";

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM especialidades WHERE descricao LIKE ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nome);

            ResultSet resultSet = preparedStatement.executeQuery();

            especialidades = createEspecialidades(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return especialidades;
    }
}
