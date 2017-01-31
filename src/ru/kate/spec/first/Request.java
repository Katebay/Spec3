package ru.kate.spec.first;

/**
 * Created by KateBay on 31.01.17.
 */
public class Request implements IRequest {

    private final String identifier;
    private final int resources;
    private final long sleepingTime;

    Request(String identifier, int resources, long sleepingTime) {
        this.identifier = identifier;
        this.resources = resources;
        this.sleepingTime = sleepingTime;
    }

    @Override
    public String identifier() {
        return identifier;
    }

    @Override
    public int resourcesRequired() {
        return resources;
    }

    @Override
    public void process() {
        try {
            Thread.sleep(sleepingTime);
        }catch(InterruptedException ex) {}
    }
}
