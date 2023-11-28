package ar.edu.unlu.poo.escobaDe15.controlador;

import ar.edu.unlu.poo.escobaDe15.Observer.Observer;
import ar.edu.unlu.poo.escobaDe15.modelo.Carta;
import ar.edu.unlu.poo.escobaDe15.modelo.Juego;
import ar.edu.unlu.poo.escobaDe15.Observer.Notificador;
import ar.edu.unlu.poo.escobaDe15.vista.VistaConsola;

import java.util.ArrayList;

public class ControladorDeConsola {

    private VistaConsola consola;
    private Juego modelo;


    public ControladorDeConsola(String nombre,int indice) {
        this.modelo = Juego.getInstance();
        this.consola = new VistaConsola(nombre, this, indice);
        this.consola.setControlador(this);
        this.modelo.attach(consola);
        consola.update(new Notificador(modelo.getTurnoJugador(),modelo.jugadoresSinCartas(),modelo.mazoVacio()));
    }

    public void setNombre(String nombre, int indice) {
        modelo.SetNombre(nombre,indice);
    }

    public String getNombre(int indice){return modelo.getNombre(indice);}

    public String getJugador(int indice){
        return modelo.getNombre(indice);
    }

    public int getTurno(){return modelo.getTurnoJugador();}

    public String nombreDeJugadorEnTurno() {
        return modelo.nombreDeJugadorEnTurno();
    }

    public String MostrarCartasEnMesa() {
        return modelo.mostrarCartasEnMesa();
    }

    public String mostrarEstadisticasDeJugador(int numeroDeJugador) {
        return modelo.mostrarEstadisticasDeJugador(numeroDeJugador);
    }

    public int getIndiceDeJugador() {return Juego.getInstance().getTurnoJugador();}

    public String mostrarCartasJugador(int indice) {
        return modelo.mostrarCartasJugador(indice);
    }

    public Carta getCartaDeJugador(int numeroDeJugador, String cadena){
        Integer numero=0;

        try {
            numero=Integer.parseInt(cadena);
        }catch(NumberFormatException e){
            consola.texto("entrada incorrecta,intente de nuevo: ");
            return null;
        }

        if (numero<0 || numero>(modelo.getCantCartas(numeroDeJugador)-1)){
            consola.texto("entrada incorrecta,intente de nuevo: ");
            return null;
        }

        return modelo.getCarta(numeroDeJugador,numero);
    }

    public Carta getCartaDeMesa(String cadena) {
        Integer numero=0;

        try {
            numero=Integer.parseInt(cadena);
        }catch(NumberFormatException e){
            consola.texto("entrada incorrecta,intente de nuevo: ");
            return null;
        }

        if (numero<0 || numero>=modelo.getcantCartasMesa()){
            consola.texto("entrada incorrecta,intente de nuevo: ");
            return null;
        }
        return modelo.getCartaDeLaMesa(numero);

    }

    public void formarBasa(int numeroDeJugador,Carta cartaDelJugador, ArrayList<Carta> cartasDeLaMesa) {

        int n=cartaDelJugador.getValor();//suma el valor de la carta del jugador

        for (int i=0;i<cartasDeLaMesa.size();i++){//suma el valor de las cartas de la mesa
            n=n+cartasDeLaMesa.get(i).getValor();
        }

        if(n==15){
            modelo.formarBasa(numeroDeJugador,cartaDelJugador,cartasDeLaMesa);
            modelo.setUltimoEnHacerBasa(numeroDeJugador-1);

            if(modelo.mesaVacia()){
                modelo.sumarEscoba(numeroDeJugador);
            }

            modelo.cambiarDeTurno();
            consola.texto("basa formada con exito\n");
        }else{
            consola.texto("la basa no suma 15\n");
            consola.texto("presione enter para continuar...");
            consola.setEstado(VistaConsola.enumEstado.TURNO_DE_JUGADOR);
        }

        consola.vaciarParametros();

    }

    public void soplarCartas(int numeroDeJugador, ArrayList<Carta> cartasDeLaMesa) {

        int n = 0;

        for (int i=0;i<cartasDeLaMesa.size();i++){//suma el valor de las cartas de la mesa
            n=n+cartasDeLaMesa.get(i).getValor();
        }

        if(n==15){
            modelo.formarBasa(numeroDeJugador,cartasDeLaMesa);
            modelo.setUltimoEnHacerBasa(numeroDeJugador);

            if(modelo.mesaVacia()){
                modelo.sumarEscoba(numeroDeJugador);
            }

            consola.texto("cartas sopladas con exito\n");
        }else{
            consola.texto("el soplo no suma 15\n");
            consola.texto("presione enter para continuar...");
        }

    }

    public void tirarCarta(int numeroDeJugador, String cadena) {
        Integer numero=0;

        try {
            numero=Integer.parseInt(cadena);
        }catch(NumberFormatException e){
            consola.texto("entrada incorrecta,intente de nuevo: ");
            return;
        }

        if (numero<0 || numero>(modelo.getCantCartas(numeroDeJugador)-1)){
            consola.texto("entrada incorrecta,intente de nuevo: ");
            return;
        }

        modelo.tirarCarta(numeroDeJugador,numero);
        modelo.cambiarDeTurno();
        consola.texto("carta tirada con exito");

    }

    public void pasarCartaAUnJugador(int numeroDeJugador){
        modelo.pasarCartaAUnJugador(numeroDeJugador);
    }

    public int buscarJugadorConMejorPuntaje() {
        return modelo.calcularJugadorConPuntajeMasAlto();
    }

    public boolean comprobarSiLaMesaEstavacia() {
        return modelo.mesaVacia();
    }

    public void pasarCartasDeLaMesaAlUltimoJugador() {
        modelo.pasarCartasDeLaMesaAlUltimoJugador();
    }
}
