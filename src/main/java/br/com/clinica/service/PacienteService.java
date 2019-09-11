package br.com.clinica.service;

import br.com.clinica.dao.PacienteDAO;
import br.com.clinica.entity.Endereco;
import br.com.clinica.entity.Paciente;
import br.com.clinica.util.FormatterUtil;

import java.util.List;

public class PacienteService {

    public List<Paciente> findByNome(String nome) {

        PacienteDAO pacienteDAO = new PacienteDAO();

        return pacienteDAO.findPacientesByNome(nome);
    }

    public Paciente findById(Integer id) {

        PacienteDAO pacienteDAO = new PacienteDAO();

        return pacienteDAO.find(id);
    }

    public Integer save(Integer id, String nome, Long cpf, String dataNascimento, Endereco endereco) {

        Paciente paciente = create(id, nome, cpf, dataNascimento, endereco);

        PacienteDAO pacienteDAO = new PacienteDAO();
        Integer result = 0;

        if (paciente.getId() == null) {

            result = pacienteDAO.insert(paciente);

        } else {

            pacienteDAO.update(paciente);

            if (isPersisted(paciente)) {

                result = 1;
            }
        }

        return result;
    }

    private boolean isPersisted(Paciente paciente) {

        PacienteDAO pacienteDAO = new PacienteDAO();

        Paciente pacientePersisted = pacienteDAO.find(paciente.getId());

        if (paciente.equals(pacientePersisted)) {

            return true;

        } else {

            return false;
        }
    }

    private Paciente create(Integer id, String nome, Long cpf, String dataNascimento, Endereco endereco) {

        FormatterUtil formatterUtil = new FormatterUtil();
        Paciente paciente = new Paciente();
        PacienteDAO pacienteDAO = new PacienteDAO();

        if (id != null) {

            paciente = pacienteDAO.find(id);
        }

        if (!nome.equals("0")) {

            paciente.setNome(nome);
        }

        if (cpf != 0) {

            paciente.setCpf(cpf);
        }

        if (!dataNascimento.equals("0")) {

            paciente.setDataNascimento(formatterUtil.formatDate(dataNascimento));
        }

        if (endereco != null) {

            paciente.setEndereco(endereco);
        }

        return paciente;
    }
}
