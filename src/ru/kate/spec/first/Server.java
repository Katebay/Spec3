package ru.kate.spec.first;

import ru.kate.spec.util.Configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by KateBay on 31.01.17.
 */
public final class Server extends Thread {

    private final Configuration configuration;
    private final long endTime;
    private final long resourcesInTotal;
    private long resources;
    private PrintWriter log;
    private boolean acceptingNewRequests = true;

    public Server() {
        this.configuration = new Configuration("server");
        this.resources = this.resourcesInTotal = configuration.getLong("resources_limit", 1000l);
        try {
            this.log = new PrintWriter(new FileWriter(configuration.getString("logfile_name", "server.log")));
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        this.endTime = System.currentTimeMillis() + configuration.getLong("work_time_in_seconds", 10) * 1000l;
        start();
    }

    private void log(String s, Object... args) {
        s = String.format(s, args);
        log.println(s);
        System.out.println(s);
    }

    private void onClose() {
        log.close();
    }

    /**
     * Method that tries to allocate resources for the given request and to run it lately.
     * @param request the request itself.
     */
    public synchronized void process(IRequest request) {
        if(!acceptingNewRequests)
            return;
        if(request.resourcesRequired() > resources) {
            log("%d > Request %s was ignored due to impossibility of resources allocation (%d > %d).",
                    System.currentTimeMillis(), request.identifier(), request.resourcesRequired(), resources);
            return;
        }
        resources -= request.resourcesRequired();
        new RequestRunnable(request).start();
        log("%d > Request %s has been launched (%d resources left)!", System.currentTimeMillis(), request.identifier(), resources);
    }

    private synchronized void release(IRequest request) {
        resources += request.resourcesRequired();
        log("%d > Request %s has been processed (%d resources as for now).", System.currentTimeMillis(), request.identifier(), resources);
    }

    @Override
    public void run() {
        //Waiting to the end
        while(System.currentTimeMillis() < this.endTime) {
            try {
                Thread.sleep(100l);
            }catch(InterruptedException ex) {}
        }
        acceptingNewRequests = false;
        //Waiting for resources to be released
        while(this.resources != this.resourcesInTotal) {
            try {
                Thread.sleep(10l);
            }catch(InterruptedException ex) {}
        }
        onClose();
    }

    //Thread wrapper for requests
    private class RequestRunnable extends Thread {

        private final IRequest request;

        private RequestRunnable(IRequest request) {
            this.request = request;
            setDaemon(true);
        }

        @Override
        public void run() {
            request.process();
            release(request);
        }
    }

    public static void main(String[] args) {
        new RequestsGenerator(new Server());
    }

}
