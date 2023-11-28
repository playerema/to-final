package ar.edu.unlu.poo.escobaDe15.modelo;

import java.util.ArrayList;

public class Mesa {
    private ArrayList<Carta> cartasSobreLaMesa;

    public Mesa(){cartasSobreLaMesa=new ArrayList<Carta>();}

    public void ponerUnaCarta(Carta carta){
        cartasSobreLaMesa.add(carta);
    }

    public Carta seleccionarUnaCarta(int i){
        return cartasSobreLaMesa.get(i);
    }

    public boolean sacarUnaCarta(Carta carta){
        return cartasSobreLaMesa.remove(carta);
    }

    public String mostrarCartas() {
        String cadena="";

        for(Integer i = 0; i<this.cartasSobreLaMesa.size(); i++){
            cadena=cadena+"opcion "+i.toString() + " - " + cartasSobreLaMesa.get(i).getValor() + " - " + cartasSobreLaMesa.get(i).getPaloString()+"\n";
        }

        return cadena;
    }

    public Integer cantCartasMesa() {
        return cartasSobreLaMesa.size();
    }

    public Boolean estaVacio(){
        return cartasSobreLaMesa.size()==0;
    }

    public ArrayList<Carta> basaFaltante(){
        ArrayList<Carta> a=cartasSobreLaMesa;
        cartasSobreLaMesa=new ArrayList<Carta>();
        return a;
    }
}
