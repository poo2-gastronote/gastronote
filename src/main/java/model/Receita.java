package model;

//Netbeans IDE23
//Isadora Costa Baía - RA2614685

import java.util.ArrayList;

public abstract class Receita{
    private String nome;
    private double tempPreparo;
    private int nivelDific;
    private int valorKcal;
    private ArrayList <Ingrediente> ingredientes;
    
    public Receita() {
        this.nome = "";
        this.tempPreparo = 0.0;
        this.nivelDific = 1; 
        this.valorKcal = 0;
        this.ingredientes = new ArrayList<>(); 
    }

    //polimorfismo por sobrecarga 
    public Receita(String nome, double tempPreparo, int nivelDific, int valorKcal, ArrayList<Ingrediente> ingredientes) {
        this.nome = nome;
        this.tempPreparo = tempPreparo;
        this.nivelDific = nivelDific;
        this.valorKcal = valorKcal;
        this.ingredientes = ingredientes;
    }

    //getters
    public ArrayList<Ingrediente> getIngredientes() {
        return this.ingredientes;
    }

    public String getNome(){
        return this.nome;
    }

    public double getTempPreparo(){
        return this.tempPreparo;
    }

    public int getNivelDific(){
        return this.nivelDific;
    }

    public int getValorKcal(){
        return this.valorKcal;
    }

    //setters
    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    public void setNome(String nome) throws NomeException{
        if(nome.length()>=3){
            this.nome = nome;
        }else
            throw new NomeException();
    }

    public void setTempPreparo(double tempPreparo){
        this.tempPreparo = tempPreparo;
    }
    
    public void setNivelDific(int nivelDific) throws NivelDifException {
        if(nivelDific >= 1 && nivelDific <= 5)
            this.nivelDific = nivelDific;
        else
            throw new NivelDifException();
    }

    public void setValorKcal(int valorKcal){
        this.valorKcal = valorKcal;
    }
}