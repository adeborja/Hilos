package Models;

/**
 * Created by adeborja on 10/06/19.
 */
public class MyWaitNotify {
    Object objetoMonitorizado;
    //boolean sePuedeCoger;

    public MyWaitNotify()
    {
        objetoMonitorizado = new Object();
        //sePuedeCoger = false;
    }

    public void doWaitCoger(long milis)
    {
        synchronized (objetoMonitorizado)
        {
            //sePuedeCoger = false;

            //while (!sePuedeCoger)
            //{
                try
                {
                    objetoMonitorizado.wait(milis);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            //}

            //sePuedeCoger = false;
        }
    }

    public void doNotifyCoger()
    {
        synchronized (objetoMonitorizado)
        {
            //sePuedeCoger=true;
            objetoMonitorizado.notify();
        }
    }
}
