package ar.edu.unlu.poo.escobaDe15.modelo;

import java.util.*;

public class Mazo {
    private ArrayList<Carta> cartasMazo;

    public Mazo(){
        cartasMazo = new ArrayList<Carta>();
        Carta.Palo[] palos = Carta.Palo.values();

        for(Carta.Palo palo:palos){
            for(int j=1;j<=10;j++){
                cartasMazo.add(new Carta(palo,j));
            }
        }

        Collections.shuffle(cartasMazo);
    }


    /*public ArrayList<Carta> getcartasMazo() {
        return cartasMazo;
    }*/

    public boolean mazoVacio(){return cartasMazo.size()==0;}

    public Carta sacarCarta(){
        return cartasMazo.remove(cartasMazo.size()-1);
    }
}
