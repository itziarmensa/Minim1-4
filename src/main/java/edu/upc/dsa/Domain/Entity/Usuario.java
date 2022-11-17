package edu.upc.dsa.Domain.Entity;

import java.util.LinkedList;
import java.util.List;

public class Usuario {
    String idUsuario;
    String nombreUsuario;
    String apellidosUsuario;
    int nivelUsuario;
    int puntosUsuario;
    Boolean partida;
    List<String> partidas;
    String partidaActual;
    String juegoActual;
    String fecha;
    int puntosPasoNivel;
    List<String> actividad;

    public Usuario(){
        this.partidas = new LinkedList<>();
    }

    public Usuario(String idUsuario, String nombreUsuario, String apellidosUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidosUsuario = apellidosUsuario;
        this.nivelUsuario = 0;
        this.puntosUsuario = 0;
        this.partida = false;
        this.partidas = new LinkedList<>();
        this.partidaActual = "";
        this.juegoActual = "";
        this.fecha = "";
        this.puntosPasoNivel = 0;
        this.actividad = new LinkedList<>();
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

    public Boolean getPartida() {
        return partida;
    }

    public void setPartida(Boolean partida) {
        this.partida = partida;
    }

    public List<String> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<String> partidas) {
        this.partidas = partidas;
    }

    public void añadirPartida(String idPartida){
        this.partidas.add(idPartida);
    }

    public String getPartidaActual() {
        return partidaActual;
    }

    public void setPartidaActual(String partidaActual) {
        this.partidaActual = partidaActual;
    }

    public String getJuegoActual() {
        return juegoActual;
    }

    public void setJuegoActual(String juegoActual) {
        this.juegoActual = juegoActual;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPuntosPasoNivel() {
        return puntosPasoNivel;
    }

    public void setPuntosPasoNivel(int puntosPasoNivel) {
        this.puntosPasoNivel = puntosPasoNivel;
    }

    public List<String> getActividad() {
        return actividad;
    }

    public void setActividad(List<String> actividad) {
        this.actividad = actividad;
    }
}
