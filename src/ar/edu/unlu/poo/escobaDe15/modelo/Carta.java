package ar.edu.unlu.poo.escobaDe15.modelo;

public class Carta {
    private Palo palo;
    private Integer valor;

    public String getPaloString() {
        switch(palo){
            case espada:
                return "espada";
            case copa:
                return "copa";
            case basto:
                return "basto";
            case oro:
                return "oro";
        }
        return "";
    }

    enum Palo{
        oro,
        espada,
        basto,
        copa
    };

    public Carta(Palo palo,Integer valor){
        this.palo=palo;
        this.valor=valor;
    }

    public Palo getPalo() {
        return palo;
    }

    public Integer getValor() {
        return valor;
    }
}
