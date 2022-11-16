package edu.upc.dsa.Domain.Entity;

import java.util.LinkedList;
import java.util.List;

public class Juego {
    String idJuego;
    String descrpcionJuego;
    int numNiveles;

    List<Usuario> listaUsuarios;

    public Juego(){
        this.listaUsuarios = new LinkedList<>();
    }

    public Juego(String idJuego, String descrpcionJuego, int numNiveles) {
        this.idJuego = idJuego;
        this.descrpcionJuego = descrpcionJuego;
        this.numNiveles = numNiveles;
        this.listaUsuarios = new LinkedList<>();
    }

    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public String getDescrpcionJuego() {
        return descrpcionJuego;
    }

    public void setDescrpcionJuego(String descrpcionJuego) {
        this.descrpcionJuego = descrpcionJuego;
    }

    public int getNumNiveles() {
        return numNiveles;
    }

    public void setNumNiveles(int numNiveles) {
        this.numNiveles = numNiveles;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
}
