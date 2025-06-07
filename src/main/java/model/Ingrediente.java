package model;

//Netbeans IDE23
//Isadora Costa Baía - RA2614685

public class Ingrediente{
    private String nome;
    private double quantidade;
    private String unidMedida;

    public Ingrediente() {
    }

    //Polimorfismo por Sobrecarga
    public Ingrediente(String nome, double quantidade, String unidMedida) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidMedida = unidMedida;
    }

    //getters
    public String getNome() {
        return this.nome;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public String getUnidMedida() {
        return this.unidMedida;
    }

    //setters
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

