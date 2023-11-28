package ar.edu.unlu.poo.escobaDe15.modelo;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> mano;
    private ArrayList<ArrayList<Carta>> cartas;

    private int cartidadDeEscobas;

    public Jugador(){
        this.nombre = "jugador";
        this.mano = new ArrayList<Carta>();
        this.cartas = new ArrayList<ArrayList<Carta>>();
    }
    public String getNombre(){return this.nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
    public Integer CalcularCantBasas(){//
        Integer puntajeJugador= cartas.size();

        return puntajeJugador;
    }
    public Integer calcularCantOros(){
        Integer resultado=0;

        for(int i = 0; i< cartas.size(); i++) {
            for (int j = 0; j < cartas.get(i).size(); j++) {
                Carta carta = cartas.get(i).get(j);
                if (carta.getPalo() == Carta.Palo.oro) {
                    resultado++;
                }
            }
        }
        return resultado;
    }
    public Integer calcularCantCartas() {
        Integer resultado = 0;

        for (int i = 0; i < cartas.size(); i++) {
            for (int j = 0; j < cartas.get(i).size(); j++) {
                    resultado++;
            }
        }

        return resultado;
    }
    public Integer calcularCantSietes(){
        Integer resultado=0;

        for(int i = 0; i< cartas.size(); i++) {
            for (int j = 0; j < cartas.get(i).size(); j++) {
                Carta carta = cartas.get(i).get(j);
                if (carta.getValor().equals(Integer.valueOf(7))) {
                    resultado++;
                }
            }
        }

        return resultado;
    }

    public Integer getCantidadDeEscobas(){return cartidadDeEscobas;}
    public Boolean comprobarSieteDeOro(){
        for(int i = 0; i< cartas.size(); i++){
            for(int j = 0; j< cartas.size(); j++) {
                Carta carta = cartas.get(i).get(j);

                if (carta.getValor().equals(Integer.valueOf(7)) && carta.getPalo() == Carta.Palo.oro) {
                    return true;
                }
            }
        }
        return false;
    }
    public Boolean comprobarCuatroSietes(){
        int sietes=0;

        for(int i = 0; i< cartas.size(); i++){
            for(int j = 0; j< cartas.size(); j++) {
                Carta carta = cartas.get(i).get(j);

                if (carta.getValor().equals(Integer.valueOf(7))) {
                    sietes++;
                }
            }
        }

        if(sietes==4){return true;}

        return false;
    }
    public Boolean comprobarTodosLosOros(){
        int oros=0;

        for(int i = 0; i< cartas.size(); i++){
            for(int j = 0; j< cartas.size(); j++) {
                Carta carta = cartas.get(i).get(j);

                if (carta.getPalo() == Carta.Palo.oro) {
                    oros++;
                }
            }
        }
        if(oros == 10){return true;}
        return false;
    }
    public Carta getCarta(Integer indice) {
        return mano.get(indice);
    }
    public void aniadirCarta(Carta carta){
        mano.add(carta);
    }
    public boolean removerCarta(Carta carta){return mano.remove(carta);}
    public String mostrarCartas() {
        String cadena="";
        for(Integer i=0;i<mano.size();i++){
            cadena=cadena+"opcion " + i.toString() + " -> " + mano.get(i).getValor() + " de " + mano.get(i).getPaloString()+"\n";
        }
        return cadena;
    }
    public void aniadirBasa(ArrayList<Carta> cartasNuevas){
        this.cartas.add(cartasNuevas);
    }
    public Boolean SinCartas(){return mano.size()==0;};
    public void sumarEscoba(){cartidadDeEscobas++;}
    public String mostrarEstadisticas(){
        String cadena="";
        cadena = cadena + "cartas: " + calcularCantCartas().toString() + "\n";
        cadena = cadena +  "basas: " + CalcularCantBasas().toString() + "\n";
        cadena = cadena + "escobas: " + Integer.valueOf(cartidadDeEscobas) + "\n";
        cadena = cadena +  "oros: " + calcularCantOros().toString() + "\n";
        cadena = cadena +  "sietes: " + calcularCantSietes().toString() + "\n";

        if(comprobarSieteDeOro()){
        cadena = cadena + " tiene el 7 de oro"+ "\n ";
        }else{
        cadena = cadena + " no tiene el 7 de oro"+ "\n ";
        }

        if(comprobarCuatroSietes()){
            cadena = cadena + " tiene los cuatro 7"+ "\n ";
        }else{
            cadena = cadena + " no tiene los cuatro 7"+ "\n ";
        }

        if(comprobarSieteDeOro()){
            cadena = cadena + " tiene todos los oros"+ "\n ";
        }else{
            cadena = cadena + " no tiene todos los oros"+ "\n ";
        }

        return cadena;
    }

    public int getCantCartas() {return mano.size();}
}
