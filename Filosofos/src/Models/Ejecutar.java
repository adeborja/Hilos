package Models;

import java.util.concurrent.TimeUnit;

/**
 * Created by adeborja on 10/06/19.
 */
public class Ejecutar {

    public static void main(String[] args)
    {

        Cronometro crono = new Cronometro();
        Thread cronometro = new Thread(crono);

        MyWaitNotify mwnPalilloA = new MyWaitNotify();
        MyWaitNotify mwnPalilloB = new MyWaitNotify();
        MyWaitNotify mwnPalilloC = new MyWaitNotify();
        MyWaitNotify mwnPalilloD = new MyWaitNotify();

        Palillo palilloA = new Palillo("A");
        Palillo palilloB = new Palillo("B");
        Palillo palilloC = new Palillo("C");
        Palillo palilloD = new Palillo("D");

        Filosofo Aristoteles = new Filosofo("Aristoteles", palilloA, palilloB, mwnPalilloA, mwnPalilloB, crono);
        Filosofo Platon = new Filosofo("Platon", palilloB, palilloC, mwnPalilloB, mwnPalilloC, crono);
        Filosofo Socrates = new Filosofo("Socrates", palilloC, palilloD, mwnPalilloC, mwnPalilloD, crono);
        Filosofo Pitagoras = new Filosofo("Pitagoras", palilloD, palilloA, mwnPalilloD, mwnPalilloA, crono);

        Thread t1 = new Thread(Aristoteles);
        Thread t2 = new Thread(Platon);
        Thread t3 = new Thread(Socrates);
        Thread t4 = new Thread(Pitagoras);


        System.out.println("===== COMIENZO =====");

        cronometro.start();

        /*while (true)
        {
            try {
                TimeUnit.MILLISECONDS.sleep(35);

                String s = crono.obtenerTiempo();

                System.out.println(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
