package edu.upc.dsa.Domain.Entity.TO;

public class JuegoInfo {
    String id;
    String desc;
    int numNiveles;

    public JuegoInfo(){}
    public JuegoInfo(String id, String desc, int numNiveles) {
        this.id = id;
        this.desc = desc;
        this.numNiveles = numNiveles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNumNiveles() {
        return numNiveles;
    }

    public void setNumNiveles(int numNiveles) {
        this.numNiveles = numNiveles;
    }
}
