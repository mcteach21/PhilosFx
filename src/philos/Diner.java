package philos;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

enum State{
    Thinking,
    Starving,
    Eating
}
public class Diner {
    private static final int NB_PHILOS=8;

    private static Thread[] philos= new Thread[NB_PHILOS];
    private static State[] states= new State[NB_PHILOS];
    private static Boolean[] forks= new Boolean[NB_PHILOS];

    private static boolean alwaysRunning=true;

    private static Semaphore semaphore = new Semaphore(NB_PHILOS);

    private static int[] stats= new int[NB_PHILOS];

    private static int stillRunning=NB_PHILOS;

    public static void start() {

        System.out.println("******************************************");
        System.out.println("********* Problème Philosophes! **********");
        System.out.println("******************************************");

        //création threads philos + init states + ress

        for (int i = 0; i < NB_PHILOS; i++) {
            final int n=i;
            philos[i] = new Thread(()->diner(n), "philo_"+i);
            states[i] = State.Thinking;
            forks[i] = true;

            stats[i] = 0;
        }

       /* new Thread(()->{
            try {
                System.out.println("wait..before stop!");
                Thread.sleep(30000);
                System.out.println("stop!!!");
                alwaysRunning=false;
            } catch (InterruptedException e) {}
        }).start();*/

        //..lancer
        for (Thread t : philos) {
            t.start();
        }

        System.out.println("******************************************");
    }

    private static void diner(int num) {
        while(alwaysRunning) {
            //System.out.println("Diner..philo : "+num);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {}

            thinking(num);
            starving(num);
            eating(num);
        }
        System.out.println("philo "+num+" ==>I've finished!");

        if(--stillRunning==0)
            show_stats();
    }

    private static void show_stats() {
        System.out.println("=================================");
        System.out.println(Arrays.toString(stats));
        System.out.println("=================================");
    }

    private static void thinking(int num) {
        states[num]=State.Thinking;
        System.out.println("philo "+num+"..thinking..");

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("==>"+e);
        }
    }


    private static void starving(int num) {
        states[num] = State.Starving;

        int[] wanted = {num, (num+1)%NB_PHILOS};

        System.out.println("philo "+num+"..waiting for forks : "+Arrays.toString(wanted));
        getForks(num, wanted);
    }
    private static synchronized void getForks(int num, int[] wanted) {

        while(!forks[wanted[0]] || !forks[wanted[1]]) {
            try {
                //System.out.println("philo "+num+"..waiting for forks : "+Arrays.toString(wanted));
                semaphore.acquire();
            } catch (InterruptedException e) {
                System.out.println("==>"+e);
            }
        }
        forks[wanted[0]]=forks[wanted[1]] = false;
    }
    private static void letForks(int num, int[] wanted) {
        forks[wanted[0]] = forks[wanted[1]]=true;
        System.out.println("philo "+num+"..finished eating! release forks : "+Arrays.toString(wanted));

        semaphore.release();
    }
    private static void eating(int num) {
        states[num]=State.Eating;
        System.out.println("=========> philo "+num+"..eating.. with ("+num+","+(num+1)%NB_PHILOS +")");

        try {
            Thread.sleep(2000);
            letForks(num, new int[]{num, (num+1)%NB_PHILOS});

            stats[num]++;
        } catch (InterruptedException e) {
            System.out.println("==>"+e);
        }
    }

    public static void stop() {
        new Thread(()->{
            try {
                System.out.println("wait..before stop!");
                Thread.sleep(10000);
                System.out.println("stop!!!");
                alwaysRunning=false;
            } catch (InterruptedException e) {}
        }).start();
    }
}
