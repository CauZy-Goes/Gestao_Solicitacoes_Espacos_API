package ucsal.cauzy.rest.dto;

public class UsuarioDTO {

    private Integer idUsuario;
    private String nomeUsuario;
    private String email;
    private String senha;
    private Integer idCargo;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "idUsuario=" + idUsuario +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", idCargo=" + idCargo +
                '}';
    }
}
