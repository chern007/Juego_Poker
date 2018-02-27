/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chern007
 */
public class Cliente {

    private static Scanner entrada = new Scanner(System.in);

    String[] corazonesCliente = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] rombosCliente = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] trebolesCliente = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] picasCliente = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};

    String[][] barajaCliente = {corazonesCliente, rombosCliente, trebolesCliente, picasCliente};

    public static void main(String[] args) {

        try {

            Socket sc = new Socket("localhost", 6060);

            DataInputStream loQueEntra = new DataInputStream(sc.getInputStream());
            DataOutputStream loQueSale = new DataOutputStream(sc.getOutputStream());

            System.out.println("Por favor introduzca su nombre:");
            String nombre = entrada.nextLine();
            //le mandamos el nombre del usuario
            loQueSale.writeUTF(nombre);//W1

            System.out.println(loQueEntra.readUTF());//R1 imprimimos el saludo

            System.out.println(loQueEntra.readUTF());//R2 imprimimos la pregunta de cuanto dinero quiere apostar

            System.out.println("Introduzca el importe que desea apostar:\n");
            loQueSale.writeInt(entrada.nextInt());//W2

            //vemos si ha aceptado la apuesta si no cerramos
            String aceptacionDinero = loQueEntra.readUTF();//R3
            if (aceptacionDinero.contains("no hay dinero suficiente")) {
                System.out.println(aceptacionDinero);
                return;
            } else {
                System.out.println(aceptacionDinero);
            }

            int[] cartaElegida = eligeCarta();
            
            
            //seguir
            
            
            
            

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static int[] eligeCarta() {

        int[] carta = new int[2];
        String palo = "*";
        String numero = "*";

        System.out.println("Elige tu carta:\n");
        System.out.println("Corazones(C), Rombos(R), Tréboles(T), Picas(P)");

        do {
            if (palo.equals("*")) {
                System.out.println("No has introducido un valor permitido, prueba otra vez.");
            }
            palo = entrada.nextLine().toLowerCase();
        } while (!palo.equals("c") && !palo.equals("r") && !palo.equals("t") && !palo.equals("p"));

        System.out.println("Elige el número de la carta:\n\"2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, AS\"");

        do {
            if (numero.equals("*")) {
                System.out.println("No has introducido un valor permitido, prueba otra vez.");
            }
            numero = entrada.nextLine().toLowerCase();
        } while (!numero.equals("2") && !numero.equals("3") && !numero.equals("4") && !numero.equals("5") && !numero.equals("6") && !numero.equals("7") && !numero.equals("8") && !numero.equals("9") && !numero.equals("10") && !numero.equals("J") && !numero.equals("Q") && !numero.equals("K") && !numero.equals("AS"));

        switch (palo) {
            case "c":

                carta[0] = 0;

                break;
            case "r":

                carta[0] = 1;

                break;
            case "t":

                carta[0] = 2;

                break;
            case "p":

                carta[0] = 3;

                break;

            default:
                throw new AssertionError();
        }

        ////////////////////////////////////////////////////////////////////////
        switch (numero) {
            case "2":

                carta[1] = 0;

                break;
            case "3":

                carta[0] = 1;

                break;
            case "4":

                carta[0] = 2;

                break;
            case "5":

                carta[0] = 3;

                break;
            case "6":

                carta[0] = 4;

                break;
            case "7":

                carta[0] = 5;

                break;
            case "8":

                carta[0] = 6;

                break;
            case "9":

                carta[0] = 7;

                break;
            case "10":

                carta[0] = 8;

                break;
            case "J":

                carta[0] = 9;

                break;
            case "Q":

                carta[0] = 10;

                break;
            case "K":

                carta[0] = 11;

                break;
            case "AS":

                carta[0] = 12;

                break;

            default:
                throw new AssertionError();
        }

        return carta;
    }

}
