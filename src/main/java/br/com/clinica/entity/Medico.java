package br.com.clinica.entity;

import java.util.List;

public class Medico {

    private Integer id;

    private String crm;

    private Funcionario funcionario;

    private List<Especialidade> especialidades;

    public Medico() {
    }

    public Medico(Integer id, String crm, Funcionario funcionario, List<Especialidade> especialidades) {
        this.id = id;
        this.crm = crm;
        this.funcionario = funcionario;
        this.especialidades = especialidades;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {

        if (crm.length() <= 20) {

            this.crm = crm;

        } else {

            this.crm = null;
        }
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", crm='" + crm + '\'' +
                ", funcionario=" + funcionario +
                ", especialidades=" + especialidades +
                '}';
    }

    public String toString(String perfil) {
        return "Medico{" +
                ", crm='" + crm + '\'' +
                ", especialidades=" + especialidades +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medico medico = (Medico) o;

        if (!id.equals(medico.id)) return false;
        if (!crm.equals(medico.crm)) return false;
        if (!funcionario.equals(medico.funcionario)) return false;
        return especialidades != null ? especialidades.equals(medico.especialidades) : medico.especialidades == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + crm.hashCode();
        result = 31 * result + funcionario.hashCode();
        result = 31 * result + (especialidades != null ? especialidades.hashCode() : 0);
        return result;
    }
}
