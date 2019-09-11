package br.com.clinica.service;

import br.com.clinica.dao.MedicoEspecialidadeDAO;
import br.com.clinica.entity.Especialidade;
import br.com.clinica.entity.Medico;

import java.util.List;

public class MedicoEspecialidadeService {

    public Integer cadastrar(List<Especialidade> especialidades, Medico medico) {

        MedicoEspecialidadeDAO medicoEspecialidadeDAO = new MedicoEspecialidadeDAO();
        Integer result = 0;

        for (Especialidade especialidade : especialidades) {

            medicoEspecialidadeDAO.insert(especialidade, medico);

            result++;
        }

        return result;
    }
}
