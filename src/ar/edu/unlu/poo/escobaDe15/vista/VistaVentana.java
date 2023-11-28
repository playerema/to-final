package ar.edu.unlu.poo.escobaDe15.vista;

import ar.edu.unlu.poo.escobaDe15.Observer.Observer;
import ar.edu.unlu.poo.escobaDe15.controlador.ControladorDeVentana;
import ar.edu.unlu.poo.escobaDe15.modelo.Jugador;
import ar.edu.unlu.poo.escobaDe15.Observer.Notificador;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class VistaVentana extends JFrame implements Observer {

    private ControladorDeVentana controlador;

    private Jugador jugador;
    private JPanel panelPrincipal;
    private JPanel panelEste;
    private JTable tablaPuntaje;
    private JPanel panelSur;
    private JPanel panelDeDatosDelJugador;
    private JButton botonJugar;
    private JPanel panelNombres;
    private JPanel panelVariables;
    private JTextPane marcadorDeSietes;
    private JTextPane marcadorDeCartas;
    private JTextPane marcadorDeOros;
    private JTextPane marcadorDeEscobas;
    private JPanel panelDeCartaJugador1;
    private JPanel panelDeCartaJugador2;
    private JPanel panelDeCartaJugador3;
    private JLabel cartaDelJugador1;
    private JLabel cartaDelJugador2;
    private JLabel cartaDelJugador3;
    private JPanel panelCentro;

    private Integer variableDeCartas=0;
    private Integer variableDeOros=0;
    private Integer variableDeEscobas=0;
    private Integer variableDeSietes=0;

    public VistaVentana(){
        iniciarVentana();
    }

    public void iniciarVentana(){
        setVisible(true);
        setSize(new Dimension(800,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panelPrincipal);
        iniciarPuntaje();
        iniciarPanelDeDatos();
        iniciarCartasJugador();
        iniciarCartasMesa();
    }

    private void iniciarCartasMesa() {

    }

    private void iniciarCartasJugador() {
        //cartaDelJugador1.setIcon(controlador.getCarta(jugador,1).getIcon());
        //cartaDelJugador2.setIcon(controlador.getCarta(jugador,2).getIcon());
        //cartaDelJugador3.setIcon(controlador.getCarta(jugador,3).getIcon());
    }

    private void iniciarPanelDeDatos(){
        panelDeDatosDelJugador.setLayout(new BoxLayout(panelDeDatosDelJugador,BoxLayout.X_AXIS));
        panelNombres.setLayout(new BoxLayout(panelNombres,BoxLayout.Y_AXIS));
        panelVariables.setLayout(new BoxLayout(panelVariables,BoxLayout.Y_AXIS));
        //inicia marcadores con los valores de las variables
        marcadorDeCartas.setText(variableDeCartas.toString());
        marcadorDeSietes.setText(variableDeSietes.toString());
        marcadorDeOros.setText(variableDeOros.toString());
        marcadorDeEscobas.setText(variableDeEscobas.toString());
        //
    }

    private void iniciarPuntaje(){
        tablaPuntaje.addColumn(new TableColumn());
        tablaPuntaje.addColumn(new TableColumn());


    }

    @Override
    public void update(Notificador notificador) {

    }

    public void setControlador(ControladorDeVentana controlador) {
        this.controlador=controlador;
    }
}
