package br.com.clinica.entity;

import br.com.clinica.util.ValidationUtils;

public class Endereco {

    private Integer id;

    private String logradouro;

    private Integer numero;

    private String bairro;

    private String complemento;

    private String cidade;

    private String estado;

    private Integer cep;

    public Endereco() {
    }

    public Endereco(Integer id, String logradouro, Integer numero, String bairro, String complemento, String cidade, String estado, Integer cep) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {

        if (logradouro.length() <= 60) {

            this.logradouro = logradouro;

        } else {

            this.logradouro = null;
        }
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {

        ValidationUtils validationUtils = new ValidationUtils();

        if (validationUtils.checkIntegerLenght(numero, 5)){

            this.numero = numero;

        } else {

            this.numero = null;
        }
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {

        if (bairro.length() <= 60) {

            this.bairro = bairro;

        } else {

            this.bairro = null;
        }
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {

        if (complemento.length() <= 20) {

            this.complemento = complemento;

        } else {

            this.complemento = null;
        }
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {

        if (cidade.length() <= 30) {

            this.cidade = cidade;

        } else {

            this.cidade = null;
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {

        if (estado.length() <= 20) {

            this.estado = estado;

        } else {

            this.estado = null;
        }
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {

        ValidationUtils validationUtils = new ValidationUtils();

        if (validationUtils.checkIntegerLenght(cep, 8)) {

            this.cep = cep;

        } else {

            this.cep = null;
        }
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", logradouro='" + logradouro + '\'' +
                ", numero=" + numero +
                ", bairro='" + bairro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", cep=" + cep +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endereco endereco = (Endereco) o;

        if (!id.equals(endereco.id)) return false;
        if (!logradouro.equals(endereco.logradouro)) return false;
        if (!numero.equals(endereco.numero)) return false;
        if (!bairro.equals(endereco.bairro)) return false;
        if (complemento != null ? !complemento.equals(endereco.complemento) : endereco.complemento != null)
            return false;
        if (!cidade.equals(endereco.cidade)) return false;
        if (!estado.equals(endereco.estado)) return false;
        return cep.equals(endereco.cep);
    }

    @Override
    public int hashCode() {

        int result = id.hashCode();
        result = 31 * result + logradouro.hashCode();
        result = 31 * result + numero.hashCode();
        result = 31 * result + bairro.hashCode();
        result = 31 * result + (complemento != null ? complemento.hashCode() : 0);
        result = 31 * result + cidade.hashCode();
        result = 31 * result + estado.hashCode();
        result = 31 * result + cep.hashCode();
        return result;
    }
}
