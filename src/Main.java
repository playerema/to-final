import ar.edu.unlu.poo.escobaDe15.controlador.ControladorDeConsola;
import ar.edu.unlu.poo.escobaDe15.modelo.Juego;
import ar.edu.unlu.poo.escobaDe15.vista.VistaConsola;

import javax.naming.ldap.Control;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                Juego.getInstance().iniciarJuego();

                ControladorDeConsola c1 = new ControladorDeConsola("j1",1);
                ControladorDeConsola c2 = new ControladorDeConsola("j2",2);
                ControladorDeConsola c3 = new ControladorDeConsola("j3",3);
                ControladorDeConsola c4 = new ControladorDeConsola("j4",4);

            }
        });



    }
}