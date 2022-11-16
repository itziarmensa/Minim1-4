package edu.upc.dsa.Domain.Entity;

public class Usuario {
    String idUsuario;
    String nombreUsuario;
    String apellidosUsuario;
    int nivelUsuario;
    int puntosUsuario;

    public Usuario(){}

    public Usuario(String idUsuario, String nombreUsuario, String apellidosUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.nivelUsuario = 0;
        this.puntosUsuario = 0;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
    }

    public int getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(int nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    public int getPuntosUsuario() {
        return puntosUsuario;
    }

    public void setPuntosUsuario(int puntosUsuario) {
        this.puntosUsuario = puntosUsuario;
    }
}
