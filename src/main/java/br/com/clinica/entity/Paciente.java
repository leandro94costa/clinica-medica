package br.com.clinica.entity;

import java.time.LocalDate;

public class Paciente {

    private Integer id;

    private String nome;

    private Long cpf;

    private LocalDate dataNascimento;

    private Endereco endereco;

    public Paciente() {
    }

    public Paciente(Integer id, String nome, Long cpf, LocalDate dataNascimento, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {

        if (nome.length() <= 60) {

            this.nome = nome;

        } else {

            this.nome = null;
        }
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {

        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf=" + cpf +
                ", dataNascimento=" + dataNascimento +
                ", endereco=" + endereco +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paciente paciente = (Paciente) o;

        if (!id.equals(paciente.id)) return false;
        if (!nome.equals(paciente.nome)) return false;
        if (!cpf.equals(paciente.cpf)) return false;
        if (!dataNascimento.equals(paciente.dataNascimento)) return false;
        return endereco.equals(paciente.endereco);
    }

    @Override
    public int hashCode() {

        int result = id.hashCode();
        result = 31 * result + nome.hashCode();
        result = 31 * result + cpf.hashCode();
        result = 31 * result + dataNascimento.hashCode();
        result = 31 * result + endereco.hashCode();
        return result;
    }
}
