package ru.kate.spec.first;

/**
 * Asynchronous requests generator
 * Created by KateBay on 31.01.17.
 */
public class RequestsGenerator {

    private final Server server;

    public RequestsGenerator(Server server) {
        this.server = server;
        for(int i = 1; i <= 4; ++i) {
            new LocalGenerator(i);
            sleepGracefully(27l);
        }
    }

    private static void sleepGracefully(long millis) {
        try {
            Thread.sleep(millis);
        }catch(InterruptedException ex) {}
    }

    private class LocalGenerator extends Thread {

        private final int id;

        private LocalGenerator(int id) {
            this.id = id;
            setDaemon(true);
            start();
        }

        @Override
        public void run() {
            for(int i = 1; i <= 100; ++i) {
                server.process(new Request(id + "#" + i, 33, 1000l));
                sleepGracefully(100l);
            }
        }

    }

}
