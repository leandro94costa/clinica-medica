package br.com.clinica.entity;

import br.com.clinica.util.ValidationUtils;

import java.time.LocalDate;

public class Funcionario extends Usuario {

    private Integer id;

    private String nome;

    private Long cpf;

    private LocalDate dataNascimento;

    private LocalDate dataAdmissao;

    private LocalDate dataDemissao;

    private Float salario;

    private String funcao;

    private Endereco endereco;

    public Funcionario() {
    }

    public Funcionario(Integer id, String nome, Long cpf, LocalDate dataNascimento, LocalDate dataAdmissao, LocalDate dataDemissao, Float salario, String funcao, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
        this.salario = salario;
        this.funcao = funcao;
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

        ValidationUtils validationUtils = new ValidationUtils();

        if (validationUtils.checkLongLenght(cpf, 11)) {

            this.cpf = cpf;

        } else {

            this.cpf = null;
        }
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public Float getSalario() {
        return salario;
    }

    public void setSalario(Float salario) {

        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {

        if (funcao.length() <= 45) {

            this.funcao = funcao;

        } else {

            this.funcao = null;
        }
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf=" + cpf +
                ", dataNascimento=" + dataNascimento +
                ", dataAdmissao=" + dataAdmissao +
                ", dataDemissao=" + dataDemissao +
                ", salario=" + salario +
                ", funcao='" + funcao + '\'' +
                ", endereco=" + endereco +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Funcionario that = (Funcionario) o;

        if (!id.equals(that.id)) return false;
        if (!nome.equals(that.nome)) return false;
        if (!cpf.equals(that.cpf)) return false;
        if (!dataNascimento.equals(that.dataNascimento)) return false;
        if (!dataAdmissao.equals(that.dataAdmissao)) return false;
        if (dataDemissao != null ? !dataDemissao.equals(that.dataDemissao) : that.dataDemissao != null) return false;
        if (!salario.equals(that.salario)) return false;
        if (!funcao.equals(that.funcao)) return false;
        return endereco.equals(that.endereco);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nome.hashCode();
        result = 31 * result + cpf.hashCode();
        result = 31 * result + dataNascimento.hashCode();
        result = 31 * result + dataAdmissao.hashCode();
        result = 31 * result + (dataDemissao != null ? dataDemissao.hashCode() : 0);
        result = 31 * result + salario.hashCode();
        result = 31 * result + funcao.hashCode();
        result = 31 * result + endereco.hashCode();
        return result;
    }
}
