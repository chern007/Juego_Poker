/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos H C
 */
public class Servidor implements Runnable {

    Socket sc;
    String[] corazonesServer = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] rombosServer = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] trebolesServer = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] picasServer = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};

    String[][] barajaServer = {corazonesServer, rombosServer, trebolesServer, picasServer};

//    int saldoBanca = 0;
    boolean finJuego = false;

    public Servidor(Socket sc) {

        this.sc = sc;

    }

    @Override
    public void run() {

        DataInputStream loQueEntra = null;
        DataOutputStream loQueSale = null;

        String entrada;
        int apuestaCliente;
        //int[] carta = new int[2];

        try {

            loQueEntra = new DataInputStream(sc.getInputStream());
            loQueSale = new DataOutputStream(sc.getOutputStream());

            entrada = loQueEntra.readUTF();//R1

            System.out.println("Se ha atendido la peticion del cliente " + entrada);
            loQueSale.writeUTF("Hola " + entrada + ", bienvenido al juego de cartas.");//W1  

            while (!finJuego) {

                loQueSale.writeUTF("�Cu�nto dinero quieres apostar?");//W2

                apuestaCliente = loQueEntra.readInt();//R2

                if (apuestaCliente > Main.banca.saldo()) {

                    //se termina el juego porque no tienme dinero la banca
                    loQueSale.writeUTF("No se ha aceptado tu apuesta porque no hay dinero suficiente en la banca.\nPrueba mas tarde.");//W3
                    System.out.println(Main.banca.saldo() + " euros en la banca");
                    sc.close();
                    return;

                } else {

                    loQueSale.writeUTF("Se ha aceptado tu apuesta.");//W3

                }
                
                int [] cartaCliente = sacaCarta();
                int indiceCartaCliente = cartaCliente[1];

//                //entrada = loQueEntra.readUTF();            
//                carta[0] = loQueEntra.readInt();//R3
//                carta[1] = loQueEntra.readInt();//R4
//
//                int indiceCartaCliente = carta[1];

                int [] cartaServer = sacaCarta();
                int indiceCartaServer = cartaServer[1];

                if (indiceCartaServer >= indiceCartaCliente) {

                    loQueSale.writeUTF("Has sacado la carta " + traduceCarta(cartaCliente) + " y la m�quina ha sacado la carta " + traduceCarta(cartaServer) + ".\nOhh, has perdido la apuesta de " + apuestaCliente  + " euros");//W4
                    
                    Main.banca.meterDinero(apuestaCliente);//sumamos la cantidad a la banca
                    System.out.println(Main.banca.saldo() + " euros en la banca");
                    loQueSale.writeUTF("�Quieres volver a jugar? (si/no)");//W5
                    entrada = loQueEntra.readUTF();//R5

                    if (!entrada.contains("si")) {
                        loQueSale.writeUTF("�Hasta Pronto!");//W6
                        sc.close();
                        finJuego = true;
                    }

                } else {

                    loQueSale.writeUTF("Has sacado la carta " + traduceCarta(cartaCliente) + " y la m�quina ha sacado la carta " + traduceCarta(cartaServer) + ".\nEnhorabuena has ganado " + apuestaCliente  + " euros");//W4
                    
                    Main.banca.sacarDinero(apuestaCliente);//restamos la cantidad a la banca
                    System.out.println(Main.banca.saldo() + " euros en la banca");
                    loQueSale.writeUTF("�Quieres volver a jugar? (si/no)");//W5
                    entrada = loQueEntra.readUTF();//R5

                    if (!entrada.contains("si")) {
                        loQueSale.writeUTF("�Hasta Pronto!");//W6
                        sc.close();
                        finJuego = true;
                    }

                }

            }

        } catch (IOException ex) {

            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private int[] sacaCarta() {

        String carta = "";
        int[] posicion = new int[2];

        while (carta.equals("")) {

            posicion[0] = new Random().nextInt(4);
            posicion[1] = new Random().nextInt(13);

            carta = barajaServer[posicion[0]][posicion[1]];
            barajaServer[posicion[0]][posicion[1]] = "";//lo vaciamos, ya que hemos sacado la carta
        }

        return posicion;
    }

    private String traduceCarta(int[] indicesCarta) {

        String palo = "";
        String numero = "";

        switch (indicesCarta[0]) {

            case 0:

                palo = "Corazones";

                break;

            case 1:

                palo = "Rombos";

                break;

            case 2:

                palo = "Tr�boles";

                break;

            case 3:

                palo = "Picas";

                break;

            default:

                palo = "";

                break;
        }

        //----------------------------------------------------------------------
        switch (indicesCarta[1]) {

            case 0:

                numero = "2";

                break;

            case 1:

                numero = "3";

                break;

            case 2:

                numero = "4";

                break;

            case 3:

                numero = "5";

                break;

            case 4:

                numero = "6";

                break;

            case 5:

                numero = "7";

                break;

            case 6:

                numero = "8";

                break;

            case 7:

                numero = "9";

                break;

            case 8:

                numero = "10";

                break;

            case 9:

                numero = "J";

                break;

            case 10:

                numero = "Q";

                break;

            case 11:

                numero = "K";

                break;

            case 12:

                numero = "AS";

                break;

            default:

                numero = "";

                break;
        }

        if (palo != "" && numero != "") {

            return numero + " de " + palo;

        } else {
            return "";
        }

    }

}
