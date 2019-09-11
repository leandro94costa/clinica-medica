package br.com.clinica.service;

import br.com.clinica.dao.FuncionarioDAO;
import br.com.clinica.entity.Endereco;
import br.com.clinica.entity.Funcionario;
import br.com.clinica.util.FormatterUtil;

import java.util.List;

public class FuncionarioService {

    public List<Funcionario> findByNome(String nomeFuncionario) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        return funcionarioDAO.findFuncionariosByNome(nomeFuncionario);
    }

    public Funcionario findById(Integer idFuncionario) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        return funcionarioDAO.find(idFuncionario);
    }

    public Boolean demitir(Integer id, String dataDemissao) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        FormatterUtil formatterUtil = new FormatterUtil();

        funcionarioDAO.demitir(id, formatterUtil.formatDate(dataDemissao));

        Boolean result = false;

        if (isDismissed(id)) {

            result = true;
        }

        return result;
    }

    public boolean isDismissed(Integer id) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        Boolean dismissed = false;

        Funcionario funcionario = funcionarioDAO.find(id);

        if (funcionario.getDataDemissao() != null) {

            dismissed = true;
        }

        return dismissed;
    }

    public Funcionario save(Integer idFuncionario, String nome, Long cpf, String dataNascimento, String dataAdmissao,
                            String dataDemissao, Float salario, String funcao, Endereco endereco) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        Funcionario funcionario = create(idFuncionario, nome, cpf, dataNascimento, dataAdmissao, dataDemissao, salario,
                funcao, endereco);

        if (funcionario.getId() == null) {

            funcionario = funcionarioDAO.find(funcionarioDAO.insert(funcionario));

        } else {

            funcionarioDAO.update(funcionario);

            if (isPersisted(funcionario)) {

                funcionario = funcionarioDAO.find(funcionario.getId());
            }
        }

        return funcionario;
    }

    private boolean isPersisted(Funcionario funcionario) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        Funcionario funcionarioPersisted = funcionarioDAO.find(funcionario.getId());

        if (funcionario.equals(funcionarioPersisted)) {

            return true;

        } else {

            return false;
        }
    }

    private Funcionario create(Integer id, String nome, Long cpf, String dataNascimento, String dataAdmissao,
                               String dataDemissao, Float salario, String funcao, Endereco endereco) {

        FormatterUtil formatterUtil = new FormatterUtil();
        Funcionario funcionario = new Funcionario();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        if (id != null) {

            funcionario = funcionarioDAO.find(id);
        }

        if (!nome.equals("0")){

            funcionario.setNome(nome);
        }

        if (cpf != 0) {

            funcionario.setCpf(cpf);
        }

        if (!dataNascimento.equals("0")) {

            funcionario.setDataNascimento(formatterUtil.formatDate(dataNascimento));
        }

        if (!dataAdmissao.equals("0")) {

            funcionario.setDataAdmissao(formatterUtil.formatDate(dataAdmissao));
        }

        if (dataDemissao != null) {

            funcionario.setDataDemissao(formatterUtil.formatDate(dataDemissao));
        }

        if (salario != 0) {

            funcionario.setSalario(salario);
        }

        if (!funcao.equals("0")) {

            funcionario.setFuncao(funcao);
        }

        if (endereco != null) {

            funcionario.setEndereco(endereco);
        }

        return funcionario;
    }
}
