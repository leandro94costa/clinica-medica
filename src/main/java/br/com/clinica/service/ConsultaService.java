package br.com.clinica.service;

import br.com.clinica.dao.ConsultaDAO;
import br.com.clinica.entity.Consulta;
import br.com.clinica.entity.Medico;
import br.com.clinica.entity.Paciente;
import br.com.clinica.entity.Usuario;
import br.com.clinica.util.FormatterUtil;

import java.time.LocalDate;
import java.util.List;

public class ConsultaService {

    public Integer save(Integer idConsulta, String data, String horario, Float valor, Paciente paciente, Medico medico) {

        ConsultaDAO consultaDAO = new ConsultaDAO();
        Integer resultado = 0;

        Consulta consulta = create(idConsulta, data, horario, valor, paciente, medico);

        if (consulta.getId() == null) {

            if (consultaDAO.isAvailable(consulta)) {

                resultado = consultaDAO.insert(consulta);

            } else {

                resultado = -1;
            }

        } else {

            if (consultaDAO.isAvailable(consulta)) {

                consultaDAO.update(consulta);

                if (isChanged(consulta)) {

                    return 1;
                }

            } else {

                resultado = -1;
            }
        }

        return resultado;
    }

    private Consulta create(Integer idConsulta, String data, String horario, Float valor, Paciente paciente, Medico medico) {

        FormatterUtil formatterUtil = new FormatterUtil();
        ConsultaDAO consultaDAO = new ConsultaDAO();
        Consulta consulta = new Consulta();

        if (idConsulta != null) {

            consulta = consultaDAO.find(idConsulta);
        }

        if (!data.equals("0")) {

            consulta.setData(formatterUtil.formatDate(data));
        }

        if (!horario.equals("0")) {

            consulta.setHorario(formatterUtil.formatTime(horario));
        }

        if (valor != 0) {

            consulta.setValor(valor);
        }

        if (paciente != null) {

            consulta.setPaciente(paciente);
        }

        if (medico != null) {

            consulta.setMedico(medico);
        }

        return consulta;
    }

    private boolean isChanged(Consulta consulta) {

        ConsultaDAO consultaDAO = new ConsultaDAO();

        Consulta consultaPersisted = consultaDAO.find(consulta.getId());

        if (consultaPersisted.equals(consulta)) {

            return true;

        } else {

            return false;
        }
    }

    public List<Consulta> findByByNomeAndData(String nome, String data) {

        ConsultaDAO consultaDAO = new ConsultaDAO();
        FormatterUtil formatterUtil = new FormatterUtil();

        return consultaDAO.findConsultasByNomeAndData(nome, formatterUtil.formatDate(data));
    }

    public String delete(Integer idConsulta) {

        ConsultaDAO consultaDAO = new ConsultaDAO();
        String resultado = "Ocorreu um problema ao desmarcar a consulta" + "\n" + "Tente novamente, por favor";

        consultaDAO.delete(idConsulta);

        try {

            Consulta consulta = consultaDAO.find(idConsulta);

            if (consulta.getId() == null) {

                resultado = "Consulta desmarcada com sucesso";
            }

        } catch (Exception e) {

            resultado = e.getMessage();
        }

        return resultado;
    }

    public List<Consulta> findConsultasHoje(Usuario usuario) {

        ConsultaDAO consultaDAO = new ConsultaDAO();
        MedicoService medicoService = new MedicoService();

        Medico medico = medicoService.findMedicoByUsuario(usuario);

        return consultaDAO.findConsultasByMedico(LocalDate.now(), medico.getId());
    }

    public List<Consulta> findConsultasByPacienteAndMedico(Integer idPaciente, Usuario usuario) {

        MedicoService medicoService = new MedicoService();
        ConsultaDAO consultaDAO = new ConsultaDAO();

        Medico medico = medicoService.findMedicoByUsuario(usuario);

        return consultaDAO.findConsultasByPacienteAndMedico(idPaciente, medico.getId());
    }

    public List<Consulta> findByPeriodoAndMedico(String dataInicial, String dataFinal, Usuario usuario) {

        MedicoService medicoService = new MedicoService();
        ConsultaDAO consultaDAO = new ConsultaDAO();
        FormatterUtil formatterUtil = new FormatterUtil();

        Medico medico = medicoService.findMedicoByUsuario(usuario);

        return consultaDAO.findConsultaByPeriodoAndMedico(formatterUtil.formatDate(dataInicial), formatterUtil.formatDate(dataFinal), medico.getId());
    }

    public Consulta find(Integer idConsulta) {

        ConsultaDAO consultaDAO = new ConsultaDAO();

        return consultaDAO.find(idConsulta);
    }
}
