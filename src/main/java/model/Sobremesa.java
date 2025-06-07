package model;

//Netbeans IDE23
//Isadora Costa Baía - RA2614685

public class Sobremesa extends Receita {
    private int nivelDoce;
    private String tempIdeal;

    //getters
    public int getNivelDoce() {
        return this.nivelDoce;
    }

    public String getTempIdeal() {
        return this.tempIdeal;
    }

    //setters
    public void setNivelDoce(int nivelDoce) throws NivelDifException {
        if(nivelDoce >= 1 && nivelDoce <= 5)
            this.nivelDoce = nivelDoce;
        else
            throw new NivelDifException();
    }

    public void setTempIdeal(String tempIdeal) {
        this.tempIdeal = tempIdeal;
    }

}