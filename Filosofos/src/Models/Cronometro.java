package Models;

import java.util.concurrent.TimeUnit;

/**
 * Created by adeborja on 10/06/19.
 */
public class Cronometro implements Runnable {

    private int minuto;
    private int segundo;
    private int centesimas;
    private boolean parar;

    public Cronometro()
    {
        minuto=0;
        segundo=0;
        centesimas = 0;
        parar = false;
    }

    public void detener()
    {
        this.parar=true;
    }

    private void iniciar() throws InterruptedException {
        this.parar = false;

        while (!parar)
        {
            TimeUnit.MILLISECONDS.sleep(10);
            sumaCentesima();
        }
    }

    private void sumaCentesima()
    {
        centesimas++;

        if(centesimas == 100)
        {
            centesimas = 0;
            segundo++;

            if(segundo==60)
            {
                segundo=0;
                minuto++;
            }
        }
    }

    public String obtenerTiempo()
    {
        String s = " "+minuto+":"+segundo+":"+centesimas+" ";

        return s;
    }

    @Override
    public void run() {
        try {
            iniciar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
