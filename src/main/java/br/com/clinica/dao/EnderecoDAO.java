package br.com.clinica.dao;

import br.com.clinica.entity.Endereco;
import br.com.clinica.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO implements GenericDAO<Endereco> {

    public int insert(Endereco endereco) {

        int generatedKey = 0;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "INSERT INTO enderecos (LOGRADOURO, NUMERO, BAIRRO, COMPLEMENTO, CIDADE, ESTADO, CEP) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, endereco.getLogradouro());
            preparedStatement.setInt(2, endereco.getNumero());
            preparedStatement.setString(3, endereco.getBairro());
            preparedStatement.setString(4, endereco.getComplemento());
            preparedStatement.setString(5, endereco.getCidade());
            preparedStatement.setString(6, endereco.getEstado());

            if (endereco.getCep() != null) {

                preparedStatement.setInt(7, endereco.getCep());

            } else {

                preparedStatement.setNull(7, Types.INTEGER);
            }

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

    public void update(Endereco endereco) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "UPDATE enderecos SET LOGRADOURO = ?, NUMERO = ?, BAIRRO = ?, COMPLEMENTO = ?, CIDADE = ?, " +
                    "ESTADO = ?, CEP = ? WHERE ID_ENDERECO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, endereco.getLogradouro());
            preparedStatement.setInt(2, endereco.getNumero());
            preparedStatement.setString(3, endereco.getBairro());
            preparedStatement.setString(4, endereco.getComplemento());
            preparedStatement.setString(5, endereco.getCidade());
            preparedStatement.setString(6, endereco.getEstado());

            if (endereco.getCep() != null) {

                preparedStatement.setInt(7, endereco.getCep());

            } else {

                preparedStatement.setNull(7, Types.INTEGER);
            }

            preparedStatement.setInt(8, endereco.getId());

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Endereco find(Integer id) {

        Endereco endereco = new Endereco();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM enderecos WHERE ID_ENDERECO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                endereco.setId(resultSet.getInt(1));
                endereco.setLogradouro(resultSet.getString(2));
                endereco.setNumero(resultSet.getInt(3));
                endereco.setBairro(resultSet.getString(4));

                if (resultSet.getString(5) != null) {

                    endereco.setComplemento(resultSet.getString(5));
                }

                endereco.setCidade(resultSet.getString(6));
                endereco.setEstado(resultSet.getString(7));
                endereco.setCep(resultSet.getInt(8));
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return endereco;
    }

    public List<Endereco> findAll() {

        List<Endereco> enderecos = new ArrayList<Endereco>();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM enderecos";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Endereco endereco = new Endereco();

                endereco.setId(resultSet.getInt(1));
                endereco.setLogradouro(resultSet.getString(2));
                endereco.setNumero(resultSet.getInt(3));
                endereco.setBairro(resultSet.getString(4));

                if (resultSet.getString(5) != null) {

                    endereco.setComplemento(resultSet.getString(5));
                }

                endereco.setCidade(resultSet.getString(6));
                endereco.setEstado(resultSet.getString(7));
                endereco.setCep(resultSet.getInt(8));

                enderecos.add(endereco);
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return enderecos;
    }

    public void delete(Integer id) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "DELETE FROM enderecos WHERE ID_ENDERECO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
