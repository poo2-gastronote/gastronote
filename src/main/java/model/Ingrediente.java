package model;

public class Ingrediente{
    private String nome;
    private double quantidade;
    private String unidMedida;

    public Ingrediente() {
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidMedida = unidMedida;
    }

    public String getNome() {
        return this.nome;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public String getUnidMedida() {
        return this.unidMedida;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public void setUnidMedida(String unidMedida) {
        this.unidMedida = unidMedida;
    }
}

