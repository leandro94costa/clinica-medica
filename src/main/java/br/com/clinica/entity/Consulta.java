package br.com.clinica.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {

    private Integer id;

    private LocalDate data;

    private LocalTime horario;

    private Float valor;

    private Paciente paciente;

    private Medico medico;

    public Consulta() {
    }

    public Consulta(Integer id, LocalDate data, LocalTime horario, Float valor, Paciente paciente, Medico medico) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.valor = valor;
        this.paciente = paciente;
        this.medico = medico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {

        if (valor >= 0) {

            this.valor = valor;

        } else {

            this.valor = null;
        }
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", data=" + data +
                ", horario=" + horario +
                ", valor=" + valor +
                ", paciente=" + paciente +
                ", medico=" + medico +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Consulta consulta = (Consulta) o;

        if (!id.equals(consulta.id)) return false;
        if (!data.equals(consulta.data)) return false;
        if (!horario.equals(consulta.horario)) return false;
        if (!valor.equals(consulta.valor)) return false;
        if (!paciente.equals(consulta.paciente)) return false;
        return medico.equals(consulta.medico);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + data.hashCode();
        result = 31 * result + horario.hashCode();
        result = 31 * result + valor.hashCode();
        result = 31 * result + paciente.hashCode();
        result = 31 * result + medico.hashCode();
        return result;
    }
}
