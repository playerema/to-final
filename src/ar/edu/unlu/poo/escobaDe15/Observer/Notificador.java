package ar.edu.unlu.poo.escobaDe15.Observer;

public class Notificador {
    private int turno;
    private Boolean mazoVacio;
    private Boolean jugadoresSinCartas;

    public Notificador(int turno,Boolean jugadoresSinCartas,Boolean mazoVacio){
        this.turno = turno;
        this.mazoVacio=mazoVacio;
        this.jugadoresSinCartas=jugadoresSinCartas;
    }

    public int getTurno() {
        return turno;
    }

    public Boolean getMazoVacio(){return mazoVacio;}

    public Boolean getJugadoresSinCartas(){return jugadoresSinCartas;}
}
