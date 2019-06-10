package Models;

import java.util.concurrent.Semaphore;

/**
 * Created by adeborja on 10/06/19.
 */
public class Palillo {

    private String denominacion;
    private String filosofoUsandolo;
    private boolean estaOcupado;
    private Semaphore semaphore;
    private MyWaitNotify myWaitNotify;

    public Palillo(String denominacion)//, MyWaitNotify mwn)
    {
        this.denominacion = denominacion;
        this.filosofoUsandolo = "";
        this.estaOcupado=false;
        this.semaphore = new Semaphore(1, true);
        //this.myWaitNotify = mwn;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public boolean utilizarPalillo(String nombreFilosofo) throws InterruptedException {

        boolean seUtiliza = false;

        if(!estaOcupado)
        {
            semaphore.acquire();

            estaOcupado = true;
            seUtiliza = true;
            filosofoUsandolo = nombreFilosofo;

            System.out.println("Ahora el palillo "+this.denominacion+" está siendo utilizado por "+nombreFilosofo);

            //TODO: tiempo limite

            semaphore.release();
        }
        else
        {
            System.out.println("El palillo "+this.denominacion+" ya está siendo utilizado por "+this.filosofoUsandolo+". "+nombreFilosofo+" tiene que esperar su turno.");
        }

        return seUtiliza;
    }

    public void soltarPalillo()
    {
        estaOcupado = false;
    }
}
