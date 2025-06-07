package model;

//Netbeans IDE23

import model.Receita;

//Isadora Costa Baía - RA2614685

public class Lanche extends Receita { 
    private String tipoLanche;
    private int portabilidade;

    //getters
    public String getTipoLanche() {
        return this.tipoLanche;
    }

    public int getPortabilidade() {
        return this.portabilidade;
    }

    //setters
    public void setTipoLanche(String tipoLanche) {
        this.tipoLanche = tipoLanche;
    }

    public void setPortabilidade(int portabilidade) {
        this.portabilidade = portabilidade;
    }

}  