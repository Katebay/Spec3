package ru.kate.spec.first;

/**
 * Created by KateBay on 31.01.17.
 */
public interface IRequest {

    /**
     * Method returns identifier of this request (all identifier must be unique).
     * @return identifier of this request.
     */
    String identifier();

    /**
     * Method returns amount of resources required for this request.
     * @return amount of resources required for this request to proceed.
     */
    int resourcesRequired();

    /**
     * Method that will be run whether there're enough resources to process the request.
     */
    void process();

}
