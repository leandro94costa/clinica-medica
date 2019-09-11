package br.com.clinica.entity;

public class Usuario {

    private Integer id;

    private String email;

    private String senha;

    private Perfil perfil;

    private Funcionario funcionario;

    public Usuario() {
    }

    public Usuario(Integer id, String email, String senha, Perfil perfil, Funcionario funcionario) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.funcionario = funcionario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        if (email.length() <= 45) {

            this.email = email;

        } else {

            this.email = null;
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {

        if (senha.length() <= 45) {

            this.senha = senha;

        } else {

            this.senha = null;
        }
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", perfil=" + perfil +
                ", funcionario=" + funcionario +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (!id.equals(usuario.id)) return false;
        if (!email.equals(usuario.email)) return false;
        if (!senha.equals(usuario.senha)) return false;
        if (perfil != usuario.perfil) return false;
        return funcionario.equals(usuario.funcionario);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + senha.hashCode();
        result = 31 * result + perfil.hashCode();
        result = 31 * result + funcionario.hashCode();
        return result;
    }
}
