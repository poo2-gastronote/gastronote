package model;
import exceptions.NivelDifException;

public class Sobremesa extends Receita {
    private int nivelDoce;
    private String categoria;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNivelDoce() {
        return this.nivelDoce;
    }

    public void setNivelDoce(int nivelDoce) throws NivelDifException {
        if(nivelDoce >= 1 && nivelDoce <= 5)
            this.nivelDoce = nivelDoce;
        else
            throw new NivelDifException();
    }
}