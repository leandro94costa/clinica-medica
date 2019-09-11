package br.com.clinica.service;

import br.com.clinica.dao.FuncionarioDAO;
import br.com.clinica.dao.MedicoDAO;
import br.com.clinica.entity.Funcionario;
import br.com.clinica.entity.Medico;
import br.com.clinica.entity.Usuario;

import java.util.List;

public class MedicoService {

    public List<Medico> findByNome(String nome) {

        MedicoDAO medicoDAO = new MedicoDAO();

        return medicoDAO.findMedicosByNome(nome);
    }

    public Medico findById(Integer id) {

        MedicoDAO medicoDAO = new MedicoDAO();

        return medicoDAO.find(id);
    }

    public Medico findMedicoByUsuario(Usuario usuario) {

        MedicoDAO medicoDAO = new MedicoDAO();

        return medicoDAO.findMedicoByFuncionario(usuario.getFuncionario().getId());
    }

    public Medico save(Medico medico) {

        MedicoDAO medicoDAO = new MedicoDAO();

        if (medico.getId() == null) {

            medico = medicoDAO.find(medicoDAO.insert(medico));

        } else {

            medicoDAO.update(medico);

            if (isPersisted(medico)) {

                return medico;
            }
        }

        return medico;
    }

    private boolean isPersisted(Medico medico) {

        MedicoDAO medicoDAO = new MedicoDAO();

        Medico medicoPersisted = medicoDAO.find(medico.getId());

        if (medico.equals(medicoPersisted)) {

            return true;

        } else {

            return false;
        }
    }

    public Funcionario findFuncionario(Integer id) {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        return funcionarioDAO.findFuncionarioByMedico(id);
    }
}
