package ar.edu.unlu.poo.escobaDe15.vista;

import ar.edu.unlu.poo.escobaDe15.Observer.Notificador;
import ar.edu.unlu.poo.escobaDe15.Observer.Observer;
import ar.edu.unlu.poo.escobaDe15.controlador.ControladorDeConsola;
import ar.edu.unlu.poo.escobaDe15.modelo.Carta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VistaConsola extends JFrame  implements Observer {
    private ControladorDeConsola controlador;
    private enumEstado estado;
    private int numeroDeJugador;

    private Carta cartaSeleccionadaJugador;

    private ArrayList<Carta> cartasSeleccionadasMesa;
    private JPanel panelPrincipal;
    private JTextArea textoDeSalida;
    private JTextField textoDeEntrada;
    private JButton botonEnviar;

    public enum enumEstado {
        INICIO_DE_JUEGO,
        ESPERAR_TU_TURNO,
        TURNO_DE_JUGADOR,
        ELEGIR_UNA_OPCION,
        SELECCIONAR_CARTA_DE_JUGADOR,
        SELECCIONAR_CARTAS_DE_MESA,
        FORMAR_BASA,
        TIRAR_CARTA,
        SELECCIONAR_CARTAS_PARA_SOPLO, SOPLAR, FIN_DEL_JUEGO,
    }

    public VistaConsola(String nombre,ControladorDeConsola controlador,int numeroDeJugador) {
        this.numeroDeJugador=numeroDeJugador;
        this.controlador=controlador;
        this.cartasSeleccionadasMesa=new ArrayList<Carta>();
        controlador.setNombre(nombre,numeroDeJugador);
        this.estado = enumEstado.INICIO_DE_JUEGO;
        setVisible(true);
        setTitle("escoba de 15 - "+ Integer.toString(numeroDeJugador));
        setSize(new Dimension(600,400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panelPrincipal);

        iniciarVista();
    }

    @Override
    public void update(Notificador notificador) {
        //si el mazo esta vacio y los jugadores no tienen mas cartas termina el juego
        if(notificador.getMazoVacio() && notificador.getJugadoresSinCartas()){
            setEstado(enumEstado.FIN_DEL_JUEGO);
        }else{
            if (notificador.getTurno() == getNumeroDeJugador()){
                setEstado(VistaConsola.enumEstado.TURNO_DE_JUGADOR);
            }else if (notificador.getTurno() != getNumeroDeJugador()){
                setEstado(VistaConsola.enumEstado.ESPERAR_TU_TURNO);
            }

            if(notificador.getJugadoresSinCartas()){
                controlador.pasarCartaAUnJugador(numeroDeJugador);
            }
        }

        gestionarFlujo("");
    }

    public void iniciarVista(){
        //flujoDeInicioDeJuego();
        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarFlujo(procesar_entrada());
            }
        });

        textoDeEntrada.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gestionarFlujo(procesar_entrada());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }
    
    private String procesar_entrada(){
        textoDeSalida.append(textoDeEntrada.getText()+"\n");
        String cadena = textoDeEntrada.getText().trim();
        textoDeEntrada.setText("");
        return cadena;
    }

    public void gestionarFlujo(String cadena){
        switch(estado){
            case INICIO_DE_JUEGO:
                flujoDeInicioDeJuego();
                controlador.setNombre(cadena,numeroDeJugador);
                textoDeSalida.append(controlador.getNombre(numeroDeJugador));
                break;
            case TURNO_DE_JUGADOR:
                limpiarPantalla();
                flujoDeTurnoDeJugador();
                break;
            case ESPERAR_TU_TURNO:
                limpiarPantalla();
                flujoDeEsperarSuTurno();
                break;
            case ELEGIR_UNA_OPCION:
                flujoDeElegirUnaOpcion(cadena);
                break;
            case SELECCIONAR_CARTA_DE_JUGADOR:
                flujoDeSeleccionarUnaCartaDeJugador(cadena);
                break;
            case SELECCIONAR_CARTAS_DE_MESA:
                flujoDeSeleccionarCartasDeLaMesa(cadena,enumEstado.FORMAR_BASA);
                break;
            case FORMAR_BASA:
                flujoDeFormarBasa();
                break;
            case SELECCIONAR_CARTAS_PARA_SOPLO:
                flujoDeSeleccionarCartasDeLaMesa(cadena,enumEstado.SOPLAR);
                break;
            case SOPLAR:
                flujoDeSoplarCartas();
                break;
            case TIRAR_CARTA:
                flujoDeTirarCarta(cadena);
                break;
            case FIN_DEL_JUEGO:
                limpiarPantalla();
                flujoDeFinDelJuego();
                break;
        }
    }

    private void flujoDeFinDelJuego() {
        controlador.pasarCartasDeLaMesaAlUltimoJugador();
        textoDeSalida.append("////////////////////////////////////////////////////////////\n");
        textoDeSalida.append("FIN DEL JUEGO\n");
        textoDeSalida.append("////////////////////////////////////////////////////////////\n");
        textoDeSalida.append("GANADOR: "+ controlador.getNombre(controlador.buscarJugadorConMejorPuntaje()) + "\n");
        textoDeSalida.append("////////////////////////////////////////////////////////////\n");
        textoDeSalida.append(controlador.getNombre(1)+"\n");
        textoDeSalida.append(controlador.mostrarEstadisticasDeJugador(1).replace("\n"," ")+"\n");
        textoDeSalida.append(controlador.getNombre(2)+"\n");
        textoDeSalida.append(controlador.mostrarEstadisticasDeJugador(2).replace("\n"," ")+"\n");
        textoDeSalida.append(controlador.getNombre(3)+"\n");
        textoDeSalida.append(controlador.mostrarEstadisticasDeJugador(3).replace("\n"," ")+"\n");
        textoDeSalida.append(controlador.getNombre(4)+"\n");
        textoDeSalida.append(controlador.mostrarEstadisticasDeJugador(4).replace("\n"," ")+"\n");


    }

    private void flujoDeSoplarCartas() {
        controlador.soplarCartas(numeroDeJugador,cartasSeleccionadasMesa);
        setEstado(VistaConsola.enumEstado.TURNO_DE_JUGADOR);
        vaciarParametros();
        gestionarFlujo("");
    }

    private void flujoDeTirarCarta(String cadena) {
        controlador.tirarCarta(numeroDeJugador,cadena);
    }

    private void flujoDeFormarBasa() {controlador.formarBasa(numeroDeJugador,cartaSeleccionadaJugador,cartasSeleccionadasMesa);}

    private void flujoDeSeleccionarCartasDeLaMesa(String cadena, enumEstado estadoNuevo) {
        //le quito los espacios al string
        cadena=cadena.toUpperCase().trim();

        //si la cadena es basta pasa al siguiente paso
        if(cadena.equals(String.valueOf("BASTA"))){

            estado= estadoNuevo;
            gestionarFlujo("");
        }else{
            //selecciona una carta de la mesa
            cartasSeleccionadasMesa.add(controlador.getCartaDeMesa(cadena));

            if(cartasSeleccionadasMesa.contains(null)){cartasSeleccionadasMesa.remove(null);}

            textoDeSalida.append("carta: ");
        }
    }

    private void flujoDeSeleccionarUnaCartaDeJugador(String cadena) {
        cadena=cadena.trim();
        cartaSeleccionadaJugador=controlador.getCartaDeJugador(numeroDeJugador,cadena);

        if(cartaSeleccionadaJugador!=null){
            estado= enumEstado.SELECCIONAR_CARTAS_DE_MESA;
            textoDeSalida.append("seleccione una carta de la mesa o escriba 'basta' terminar:\n");
            textoDeSalida.append("carta: ");
        }

    }

    private void flujoDeElegirUnaOpcion(String cadena) {
        cadena=cadena.toUpperCase().trim();
        switch(cadena){
            case "B"://basa++++++++++++++++++++++++++++++++++++++++++++++
                if(!controlador.comprobarSiLaMesaEstavacia()){
                    textoDeSalida.append("selecccione una carta suya: ");
                    estado= enumEstado.SELECCIONAR_CARTA_DE_JUGADOR;
                }else{
                    textoDeSalida.append("la mesa esta vacia intente otra opcion: ");
                }
                break;
            case "T"://tirar------------------------------
                textoDeSalida.append("selecccione una carta suya: ");
                estado= enumEstado.TIRAR_CARTA;
                break;
            case "S"://soplar-----------------------------
                if(!controlador.comprobarSiLaMesaEstavacia()){
                    textoDeSalida.append("seleccione una carta de la mesa o escriba 'basta' terminar: ");
                    estado= enumEstado.SELECCIONAR_CARTAS_PARA_SOPLO;
                    break;
                }else{
                    textoDeSalida.append("la mesa esta vacia intente otra opcion: ");
                }
        }
    }

    private void limpiarPantalla() {
        textoDeSalida.setText("");
    }

    private void flujoDeInicioDeJuego() {
        textoDeSalida.append("////////////////////////////////////////////////////////////////////\n");
        textoDeSalida.append("ESCOBA DE 15 PARA 4 JUGADORES\n");
        textoDeSalida.append("////////////////////////////////////////////////////////////////////\n");
        textoDeSalida.append("por favor escriba su nombre: ");

    }

    private void flujoDeEsperarSuTurno(){
        textoDeSalida.append("Espera es turno de "+controlador.nombreDeJugadorEnTurno() + "\n");
    }

    private void flujoDeTurnoDeJugador(){
        textoDeSalida.append("////////////////////////////////////////////////////////////\n");
        textoDeSalida.append(controlador.mostrarEstadisticasDeJugador(numeroDeJugador));
        textoDeSalida.append("////////////////////////////////////////////////////////////\n");
        textoDeSalida.append("CARTAS SOBRE LA MESA\n");
        textoDeSalida.append(controlador.MostrarCartasEnMesa());
        textoDeSalida.append("////////////////////////////////////////////////////////////\n");
        textoDeSalida.append("CARTAS DEL JUGADOR\n");
        textoDeSalida.append(controlador.mostrarCartasJugador(numeroDeJugador));
        textoDeSalida.append("////////////////////////////////////////////////////////////\n");
        textoDeSalida.append("selecciona B/b para formar una basa\n");
        textoDeSalida.append("selecciona T/t para tirar una carta\n");
        textoDeSalida.append("selecciona S/s para soplar\n");
        estado= enumEstado.ELEGIR_UNA_OPCION;

    }

    public int getNumeroDeJugador() {return numeroDeJugador;}

    public void setControlador(ControladorDeConsola controlador) {
        this.controlador=controlador;
    }

    public ControladorDeConsola getControlador() {return controlador;}

    public void setEstado(enumEstado estado) {this.estado = estado;}

    public void texto(String cadena) {textoDeSalida.append(cadena);}

    public void vaciarParametros(){
        cartaSeleccionadaJugador=null;
        cartasSeleccionadasMesa=new ArrayList<Carta>();
    }
}


