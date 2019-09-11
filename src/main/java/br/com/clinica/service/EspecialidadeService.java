package br.com.clinica.service;

import br.com.clinica.dao.EspecialidadeDAO;
import br.com.clinica.entity.Especialidade;

import java.util.List;

public class EspecialidadeService {

    public List<Especialidade> findByNome(String nome) {

        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

        return especialidadeDAO.findEspecialidadesByNome(nome);
    }

    public Especialidade findById(Integer id) {

        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

        return especialidadeDAO.find(id);
    }

    public Integer save(Especialidade especialidade) {

        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
        Integer result = 0;

        if (especialidade.getId() == null) {

            result = especialidadeDAO.insert(especialidade);

        } else {

            especialidadeDAO.update(especialidade);

            if (isPersisted(especialidade)) {

                result = 1;
            }
        }

        return result;
    }

    private boolean isPersisted(Especialidade especialidade) {

        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

        Especialidade especialidadePersisted = especialidadeDAO.find(especialidade.getId());

        if (especialidade.equals(especialidadePersisted)) {

            return true;

        } else {

            return false;
        }
    }
}
