package ar.edu.unlu.poo.escobaDe15.modelo;

import ar.edu.unlu.poo.escobaDe15.Observer.Notificador;
import ar.edu.unlu.poo.escobaDe15.Observer.Observer;
import ar.edu.unlu.poo.escobaDe15.Observer.Subject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Juego implements Subject {
    private static List<Observer> observadores;
    private static Juego unicoJuego;
    private  Mesa mesa;
    private Mazo mazo;
    private int turnoJugador;
    private int ultimoEnHacerBasa;
    private Jugador[] jugadores;

    private Juego(){
        turnoJugador=new Random().nextInt(4)+1;
        ultimoEnHacerBasa=0;
        mesa = new Mesa();
        mazo = new Mazo();
        observadores=new ArrayList<Observer>();

        jugadores=new Jugador[4];
        for(int i=0;i<=3;i++){
            jugadores[i]=new Jugador();
        }
        notifyMessage(new Notificador(turnoJugador,jugadoresSinCartas(),mazo.mazoVacio()));
    }

    public static Juego getInstance() {
        if(unicoJuego == null){
            unicoJuego = new Juego();
        }

        return unicoJuego;
    }

    public void aniadirALaMesa(Carta carta){
        mesa.ponerUnaCarta(carta);
    }

    @Override
    public void attach(Observer anObserver) {
        observadores.add(anObserver);
    }

    @Override
    public void detach(Observer anObserver) {
        Iterator<Observer> iterador = observadores.iterator();

        while (iterador.hasNext()) {
            Observer elemento = iterador.next();

            if (elemento.equals(anObserver)) {
                iterador.remove();
                return; // Puedes quitar esto si quieres seguir buscando más ocurrencias del objeto en la lista
            }
        }
    }

    @Override
    public void notifyMessage(Notificador notificador) {
        for(Observer observer:observadores){
            observer.update(notificador);
        }

    }

    public Carta getCarta(int numeroDeJugador, Integer indice) {
        return jugadores[numeroDeJugador-1].getCarta(indice);
    }

    public void SetNombre(String nombre,int indice){
        this.jugadores[indice-1].setNombre(nombre);
    }
    public String getNombre(int indice){return jugadores[indice-1].getNombre();}

    public int getTurnoJugador(){return turnoJugador;}

    public void cambiarDeTurno(){
        if(!mazoVacio() || !jugadoresSinCartas()){
            if(turnoJugador>=4){
                turnoJugador=1;
            }else{
                turnoJugador++;
            }
        }

        notifyMessage(new Notificador(turnoJugador,jugadoresSinCartas(),mazo.mazoVacio()));
    }

    public String nombreDeJugadorEnTurno() {
        return jugadores[turnoJugador-1].getNombre();
    }

    public String mostrarCartasEnMesa() {
        return mesa.mostrarCartas();
    }

    //metodos para iniciar el juego o una nueva ronda de este
    public void iniciarJuego(){
        pasarCartasAJugadores();
        CargarMesa();
    }

    private void CargarMesa() {
        for (int i=0;i<=3;i++) {
            mesa.ponerUnaCarta(mazo.sacarCarta());
        }
    }

    private void pasarCartasAJugadores() {
        //a cada uno de los 4 jugadores
        for(int i=0;i<=3;i++) {
            //se le pone cada una de las 3 cartas
            for (int j = 0; j <= 2; j++) {
                jugadores[i].aniadirCarta(mazo.sacarCarta());
            }
        }
    }

    public String mostrarEstadisticasDeJugador(int numeroDeJugador) {
        numeroDeJugador=numeroDeJugador-1;
        return jugadores[numeroDeJugador].mostrarEstadisticas();
    }

    public String mostrarCartasJugador(int numeroDeJugador){
        return jugadores[numeroDeJugador-1].mostrarCartas();
    }

    public Carta getCartaDeLaMesa(int indice) {return mesa.seleccionarUnaCarta(indice);}

    public Integer getcantCartasMesa() {return mesa.cantCartasMesa();}

    public void formarBasa(int numeroDeJugador, Carta cartaDelJugador, ArrayList<Carta> cartasDeLaMesa) {
        //remueve la carta del jugador
        jugadores[numeroDeJugador-1].removerCarta(cartaDelJugador);

        //remueve las cartas de la mesa
        for(int i=0;i<cartasDeLaMesa.size();i++) {
            mesa.sacarUnaCarta(cartasDeLaMesa.get(i));
        }

        //pone la carta del jugador con las otras
        cartasDeLaMesa.add(cartaDelJugador);

        //añade como basa todas las cartas al jugador [numeroDeJugador-1]
        jugadores[numeroDeJugador-1].aniadirBasa(cartasDeLaMesa);
    }

    public void formarBasa(int numeroDeJugador, ArrayList<Carta> cartasDeLaMesa) {
        //remueve las cartas de la mesa
        for(int i=0;i<cartasDeLaMesa.size();i++) {
            mesa.sacarUnaCarta(cartasDeLaMesa.get(i));
        }

        //añade como basa todas las cartas al jugador [numeroDeJugador-1]
        jugadores[numeroDeJugador-1].aniadirBasa(cartasDeLaMesa);
    }

    public void tirarCarta(int numeroDeJugador, int numero) {
        Carta c=jugadores[numeroDeJugador-1].getCarta(numero);
        jugadores[numeroDeJugador-1].removerCarta(c);
        mesa.ponerUnaCarta(c);
    }

    public Boolean jugadoresSinCartas(){return jugadores[0].SinCartas() && jugadores[1].SinCartas() && jugadores[2].SinCartas() && jugadores[3].SinCartas();}

    public void pasarCartaAUnJugador(int numeroDeJugador){
        for (int j = 0; j <= 2; j++) {
            jugadores[numeroDeJugador-1].aniadirCarta(mazo.sacarCarta());
        }
    }

    public Boolean mazoVacio(){return mazo.mazoVacio();}
    //******
    public int calcularJugadorConPuntajeMasAlto(){
        Integer[] puntajeJugadores=new Integer[4];
        puntajeJugadores[0]=0;
        puntajeJugadores[1]=0;
        puntajeJugadores[2]=0;
        puntajeJugadores[3]=0;

        int n=jugadorConMasOro();
        System.out.println(Integer.valueOf(n));
        //suma los puntos de todos los jugadores
        puntajeJugadores[n]++;//oros

        n=masCartas();
        System.out.println(Integer.valueOf(n));
        puntajeJugadores[n]++;//cartas

        n=masSietes();
        System.out.println(Integer.valueOf(n));
        puntajeJugadores[n]++;//7's

        //escobas
        puntajeJugadores[0]=puntajeJugadores[0] + jugadores[0].getCantidadDeEscobas();
        puntajeJugadores[1]=puntajeJugadores[1] + jugadores[1].getCantidadDeEscobas();
        puntajeJugadores[2]=puntajeJugadores[2] + jugadores[2].getCantidadDeEscobas();
        puntajeJugadores[3]=puntajeJugadores[3] + jugadores[3].getCantidadDeEscobas();

        //los 4 sietes
        n=unicoJuego.jugadorConLosCuatroSietes();
        System.out.println(Integer.valueOf(n));
        if(n!=-1){puntajeJugadores[n]++;}

        n=jugadorConSieteDeOro();
        System.out.println(Integer.valueOf(n));
        if(n!=-1){puntajeJugadores[n]++;}//7 de oro


        //todos los oros
        n=unicoJuego.jugadorConTodosLosOros();
        System.out.println(Integer.valueOf(n));
        if(n!=1){puntajeJugadores[n+1]++;}

        int mayor=-1;
        int jugadorConMayorPuntaje=0;

        for (int i=0;i<puntajeJugadores.length;i++){
            if(puntajeJugadores[i]>mayor){
                mayor=puntajeJugadores[i];
                jugadorConMayorPuntaje=i;
            }
        }

        return jugadorConMayorPuntaje;
    }
    private int jugadorConMasOro(){//devuelve el numero de jugador con mas oros
        int mayor=-1;
        int jugadorConMasOros=-1;

        for(int i=0;i<jugadores.length;i++){

            Integer oros=jugadores[i].calcularCantOros();

            if(oros>mayor){
                mayor=oros;
                jugadorConMasOros=i;
            }
        }
        return jugadorConMasOros;
    }
    private int masCartas(){//devuelve el numero de jugador con mas cartas
        int mayor=0;
        int jugadorConMasCartas=0;

        for(int i=0;i<jugadores.length;i++){
            Integer cartas=jugadores[i].calcularCantCartas();
            if(cartas>mayor){
                mayor=cartas;
                jugadorConMasCartas=i;
            }
        }
        return jugadorConMasCartas;
    }
    private int masSietes(){
        int mayor=-1;
        int jugadorConMasSietes=-1;

        for(int i=0;i<jugadores.length;i++){
            Integer sietes=jugadores[i].calcularCantSietes();
            if(sietes>mayor){
                mayor=sietes;
                jugadorConMasSietes=i;
            }
        }
        return jugadorConMasSietes;
    }
    private int jugadorConSieteDeOro(){

        for (int i=0;i<jugadores.length;i++){
            if(jugadores[i].comprobarSieteDeOro()){return i;}
        }

        return 0;
    }
    private int jugadorConLosCuatroSietes(){
        for (int i=0;i<jugadores.length;i++){
            if(jugadores[i].comprobarCuatroSietes()){return i;}
        }
        return 0;
    }
    private int jugadorConTodosLosOros(){
        for (int i=0;i<jugadores.length;i++){
            if(jugadores[i].comprobarTodosLosOros()){return i;}
        }
        return 0;
    }
    public Boolean mesaVacia() {
        return mesa.estaVacio();
    }
    public void sumarEscoba(int numeroDeJugador) {jugadores[numeroDeJugador-1].sumarEscoba();}

    public int getCantCartas(int numeroDeJugadores) {
        return jugadores[numeroDeJugadores-1].getCantCartas();
    }

    public void pasarCartasDeLaMesaAlUltimoJugador() {
        if(ultimoEnHacerBasa>=-1){
            jugadores[ultimoEnHacerBasa].aniadirBasa(mesa.basaFaltante());
        }
    }

    public void setUltimoEnHacerBasa(int ultimoEnHacerBasa) {
        this.ultimoEnHacerBasa = ultimoEnHacerBasa;
    }
}
