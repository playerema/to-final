package ar.edu.unlu.poo.escobaDe15.controlador;

import ar.edu.unlu.poo.escobaDe15.JugadorNoEncontradoException;
import ar.edu.unlu.poo.escobaDe15.modelo.Carta;
import ar.edu.unlu.poo.escobaDe15.modelo.Juego;
import ar.edu.unlu.poo.escobaDe15.modelo.Jugador;
import ar.edu.unlu.poo.escobaDe15.vista.VistaVentana;

public class ControladorDeVentana{
    private Juego modelo;
    private VistaVentana vista;


    public ControladorDeVentana(Juego modelo, VistaVentana vista){
        this.modelo=modelo;
        this.vista=vista;
        vista.setControlador(this);
    }


    public Carta getCarta(Jugador jugador, Integer indice) throws JugadorNoEncontradoException,IndexOutOfBoundsException{
        return null;

    }

}
