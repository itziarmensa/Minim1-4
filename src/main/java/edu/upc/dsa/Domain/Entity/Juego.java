package edu.upc.dsa.Domain.Entity;

public class Juego {
    String idJuego;
    String descrpcionJuego;
    int numNiveles;

    public Juego(String idJuego, String descrpcionJuego, int numNiveles) {
        this.idJuego = idJuego;
        this.descrpcionJuego = descrpcionJuego;
        this.numNiveles = numNiveles;
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
}
