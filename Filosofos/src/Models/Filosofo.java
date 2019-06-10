package Models;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by adeborja on 10/06/19.
 */
public class Filosofo implements Runnable {

    private String nombre;
    private Palillo palilloIzq;
    private Palillo palilloDer;
    private Random random;
    private boolean usandoPalilloIzq;
    private boolean usandoPalilloDer;
    private MyWaitNotify palilloIzqWait;
    private MyWaitNotify palilloDerWait;
    private Cronometro cronometro;

    public Filosofo(String n, Palillo pi, Palillo pd, MyWaitNotify mwnIzq, MyWaitNotify mwnDer, Cronometro c)
    {
        this.nombre=n;
        this.palilloIzq = pi;
        this.palilloDer = pd;
        this.random = new Random();
        this.usandoPalilloIzq = false;
        this.usandoPalilloDer = false;
        this.palilloIzqWait = mwnIzq;
        this.palilloDerWait = mwnDer;
        this.cronometro=c;
    }

    public void Filosofar() throws InterruptedException {
        //Pensar durante un tiempo aleatorio
        long tiempoFilosofando = random.nextInt(3000);

        System.out.println("El filosofo "+this.nombre+" esta filosofando durante "+(double)tiempoFilosofando/1000+" segundos. == "+cronometro.obtenerTiempo()+" ==");

        TimeUnit.MILLISECONDS.sleep(tiempoFilosofando);

        //Coger palillos
        System.out.println("El filosofo "+this.nombre+" va a comer. == "+cronometro.obtenerTiempo()+" ==");

        IntentarComer();
    }

    public void IntentarComer() throws InterruptedException {

        boolean haComido = false;
        long tiempoEsperarPalillo;

        while (!haComido)
        {
            //Intenta coger los palillos para comer
            if(!usandoPalilloIzq) usandoPalilloIzq = palilloIzq.utilizarPalillo(this.nombre);
            if(!usandoPalilloDer) usandoPalilloDer = palilloDer.utilizarPalillo(this.nombre);

            //Si esta usando los dos palillos, come
            if(usandoPalilloIzq && usandoPalilloDer)
            {
                Comer();

                haComido = true;
            }
            else
            {
                if(usandoPalilloIzq)
                {
                    //Esperar para intentar coger el otro palillo
                    tiempoEsperarPalillo = random.nextInt(8000);

                    System.out.println("El filosofo "+this.nombre+" tiene el palillo "+palilloIzq.getDenominacion()+
                            ". Va a esperar "+(double)tiempoEsperarPalillo/1000+" segundos para intentar coger el palillo "+palilloDer.getDenominacion()+
                            ". == "+cronometro.obtenerTiempo()+" ==");


                    palilloDerWait.doWaitCoger(tiempoEsperarPalillo);

                    usandoPalilloDer = palilloDer.utilizarPalillo(this.nombre);

                    //Si el palillo sigue ocupado, suelta el palillo que tenia y espera un tiempo
                    if(!usandoPalilloDer)
                    {
                        palilloIzq.soltarPalillo();

                        usandoPalilloIzq = false;

                        palilloIzqWait.doNotifyCoger();

                        System.out.println("El filosofo "+this.nombre+" suelta el palillo "+this.palilloIzq.getDenominacion()+". == "+cronometro.obtenerTiempo()+" ==");

                        tiempoEsperarPalillo = random.nextInt(8000);

                        TimeUnit.MILLISECONDS.sleep(tiempoEsperarPalillo);
                    }
                }
                else
                if(usandoPalilloDer)
                {
                    //Esperar para intentar coger el otro palillo
                    tiempoEsperarPalillo = random.nextInt(8000);

                    System.out.println("El filosofo "+this.nombre+" tiene el palillo "+palilloDer.getDenominacion()+
                            ". Va a esperar "+(double)tiempoEsperarPalillo/1000+" segundos para intentar coger el palillo "+palilloIzq.getDenominacion()+
                            ". == "+cronometro.obtenerTiempo()+" ==");

                    //TODO: Cambiar esto por wait(milisegundos).
                    //TimeUnit.MILLISECONDS.sleep(tiempoEsperarPalillo);
                    palilloIzqWait.doWaitCoger(tiempoEsperarPalillo);

                    usandoPalilloIzq = palilloIzq.utilizarPalillo(this.nombre);

                    //Si el palillo sigue ocupado, suelta el palillo que tenia y espera un tiempo
                    if(!usandoPalilloIzq)
                    {
                        palilloDer.soltarPalillo();

                        usandoPalilloDer = false;

                        palilloDerWait.doNotifyCoger();

                        System.out.println("El filosofo "+this.nombre+" suelta el palillo "+this.palilloDer.getDenominacion()+". == "+cronometro.obtenerTiempo()+" ==");

                        tiempoEsperarPalillo = random.nextInt(8000);

                        TimeUnit.MILLISECONDS.sleep(tiempoEsperarPalillo);
                    }
                }
                else
                {
                    tiempoEsperarPalillo = random.nextInt(10000);

                    System.out.println("Los palillos "+this.palilloIzq.getDenominacion()+" y "+this.palilloDer.getDenominacion()+" estan siendo utilizados. Se esperara "+(double)tiempoEsperarPalillo/1000+" segundos. == "+cronometro.obtenerTiempo()+" ==");

                    TimeUnit.MILLISECONDS.sleep(tiempoEsperarPalillo);
                }
            }
        }
    }

    public void Comer() throws InterruptedException {

        long tiempoComiendo;

        tiempoComiendo = random.nextInt(10000);
        System.out.println("El filosofo "+this.nombre+" esta comiendo durante "+(double)tiempoComiendo/1000+" segundos. Esta usando los palillos "+this.palilloIzq.getDenominacion()+" y "+this.palilloDer.getDenominacion()+". == "+cronometro.obtenerTiempo()+" ==");

        TimeUnit.MILLISECONDS.sleep(tiempoComiendo);

        System.out.println("El filosofo "+this.nombre+" deja libre los palillos "+this.palilloIzq.getDenominacion()+" y "+this.palilloDer.getDenominacion()+". == "+cronometro.obtenerTiempo()+" ==");

        palilloIzq.soltarPalillo();
        palilloDer.soltarPalillo();

        palilloIzqWait.doNotifyCoger();
        palilloDerWait.doNotifyCoger();

        usandoPalilloIzq = false;
        usandoPalilloDer = false;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Filosofar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
