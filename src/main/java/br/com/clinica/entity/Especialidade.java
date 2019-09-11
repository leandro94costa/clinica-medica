package br.com.clinica.entity;

public class Especialidade {

    private Integer id;

    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {

        if (descricao.length() <= 45) {

            this.descricao = descricao;

        } else {

            this.descricao = null;
        }
    }

    @Override
    public String toString() {
        return "Especialidade{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Especialidade that = (Especialidade) o;

        if (!id.equals(that.id)) return false;
        return descricao.equals(that.descricao);
    }

    @Override
    public int hashCode() {

        int result = id.hashCode();
        result = 31 * result + descricao.hashCode();
        return result;
    }
}
