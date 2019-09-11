package br.com.clinica.dao;

import br.com.clinica.entity.Funcionario;
import br.com.clinica.util.ConnectionUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements GenericDAO<Funcionario> {

    public int insert(Funcionario funcionario) {

        int generatedKey = 0;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "INSERT INTO funcionarios (NOME, CPF, DATA_NASCIMENTO, DATA_ADMISSAO, DATA_DEMISSAO, SALARIO, " +
                    "FUNCAO, ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, funcionario.getNome());
            preparedStatement.setLong(2, funcionario.getCpf());
            preparedStatement.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            preparedStatement.setDate(4, Date.valueOf(funcionario.getDataAdmissao()));

            if (funcionario.getDataDemissao() != null) {

                preparedStatement.setDate(5, Date.valueOf(funcionario.getDataDemissao()));

            } else {

                preparedStatement.setNull(5, Types.DATE);
            }

            preparedStatement.setFloat(6, funcionario.getSalario());
            preparedStatement.setString(7, funcionario.getFuncao());
            preparedStatement.setInt(8, funcionario.getEndereco().getId());

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

    public void update(Funcionario funcionario) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "UPDATE funcionarios SET NOME = ?, CPF = ?, DATA_NASCIMENTO = ?, DATA_ADMISSAO = ?, " +
                    "DATA_DEMISSAO = ?, SALARIO = ?, FUNCAO = ?, ID_ENDERECO = ? WHERE ID_FUNCIONARIO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, funcionario.getNome());
            preparedStatement.setLong(2, funcionario.getCpf());
            preparedStatement.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            preparedStatement.setDate(4, Date.valueOf(funcionario.getDataAdmissao()));

            if (funcionario.getDataDemissao() != null) {

                preparedStatement.setDate(5, Date.valueOf(funcionario.getDataDemissao()));

            } else {

                preparedStatement.setNull(5, Types.DATE);
            }
            preparedStatement.setFloat(6, funcionario.getSalario());
            preparedStatement.setString(7, funcionario.getFuncao());
            preparedStatement.setInt(8, funcionario.getEndereco().getId());
            preparedStatement.setInt(9, funcionario.getId());

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Funcionario find(Integer id) {

        Funcionario funcionario = null;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM funcionarios WHERE ID_FUNCIONARIO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            funcionario = createFuncionario(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return funcionario;
    }

    public List<Funcionario> findAll() {

        List<Funcionario> funcionarios = new ArrayList<>();

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM funcionarios";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            funcionarios = createFuncionarios(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return funcionarios;
    }

    public void delete(Integer id) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "DELETE FROM funcionarios WHERE ID_FUNCIONARIO = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private Funcionario createFuncionario(ResultSet resultSet) {

        Funcionario funcionario = new Funcionario();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try {

            if (resultSet.next()) {

                funcionario.setId(resultSet.getInt(1));
                funcionario.setNome(resultSet.getString(2));
                funcionario.setCpf(resultSet.getLong(3));
                funcionario.setDataNascimento(resultSet.getDate(4).toLocalDate());
                funcionario.setDataAdmissao(resultSet.getDate(5).toLocalDate());

                if (resultSet.getDate(6) != null) {

                    funcionario.setDataDemissao(resultSet.getDate(6).toLocalDate());
                }

                funcionario.setSalario(resultSet.getFloat(7));
                funcionario.setFuncao(resultSet.getString(8));
                funcionario.setEndereco(enderecoDAO.find(resultSet.getInt(9)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionario;
    }

    private List<Funcionario> createFuncionarios(ResultSet resultSet) {

        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try {

            while (resultSet.next()) {

                Funcionario funcionario = new Funcionario();

                funcionario.setId(resultSet.getInt(1));
                funcionario.setNome(resultSet.getString(2));
                funcionario.setCpf(resultSet.getLong(3));
                funcionario.setDataNascimento(resultSet.getDate(4).toLocalDate());
                funcionario.setDataAdmissao(resultSet.getDate(5).toLocalDate());

                if (resultSet.getDate(6) != null) {

                    funcionario.setDataDemissao(resultSet.getDate(6).toLocalDate());
                }

                funcionario.setSalario(resultSet.getFloat(7));
                funcionario.setFuncao(resultSet.getString(8));
                funcionario.setEndereco(enderecoDAO.find(resultSet.getInt(9)));

                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return funcionarios;
    }

    public List<Funcionario> findFuncionariosByNome(String nome) {

        List<Funcionario> funcionarios = new ArrayList<Funcionario>();

        nome = "%" + nome + "%";

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM funcionarios WHERE nome LIKE ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, nome);

            ResultSet resultSet = preparedStatement.executeQuery();

            funcionarios = createFuncionarios(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return funcionarios;
    }

    public void demitir(Integer id, LocalDate dataDemissao) {

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "UPDATE funcionarios SET data_demissao = ? WHERE id_funcionario = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, Date.valueOf(dataDemissao));
            preparedStatement.setLong(2, id);

            preparedStatement.execute();

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Funcionario findFuncionarioByMedico(Integer id) {

        Funcionario funcionario = null;

        try {

            Connection connection = ConnectionUtil.getInstance().getConnection();

            String sql = "SELECT * FROM funcionarios WHERE id_funcionario = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            funcionario = createFuncionario(resultSet);

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return funcionario;
    }
}
