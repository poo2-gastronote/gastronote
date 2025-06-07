package model;

//Netbeans IDE23
//Isadora Costa Baía - RA2614685

public class Refeicao extends Receita {
    private int porcoes;
    private int temAcomp;
    
    //getters
    public int getPorcoes() {
        return this.porcoes;
    }

    public int getTemAcomp() {
        return this.temAcomp;
    }

    //setters
    public void setPorcoes(int porcoes) {
        this.porcoes = porcoes;
    }

    public void setTemAcomp(int temAcomp) {
        this.temAcomp = temAcomp;
    }

}