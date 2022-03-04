package ru.dvsokolov.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPrinter {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPrinter.class);
    private int counter = 1;
    private int increment = 1;

    public static void main(String[] args) throws InterruptedException  {
        var threadPrinter = new ThreadPrinter();
        threadPrinter.go();
    }

    private void go() throws InterruptedException {
        Thread thread1 = new Thread(() -> this.action(1));
        Thread thread2 = new Thread(() -> this.action(0));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        logger.info("ActionFinished: {}", counter);
    }

    private synchronized void action(int mod){
        while(!Thread.currentThread().isInterrupted()) {
            try {
                while (mod != counter % 2) {
                    this.wait();
                }

                var result = counter / 2;

                if ((mod == 1) && (increment == 1)){
                    result =  result + 1;
                }
                logger.info(String.valueOf(result));

                counter = counter + increment;

                if (counter > 19){
                    increment = -1;
                }
                if ((counter < 3) && (increment == -1)){
                    Thread.currentThread().interrupt();
                }

                notifyAll();
                logger.info("after notify");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }


}
