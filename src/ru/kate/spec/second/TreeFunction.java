package ru.kate.spec.second;

/**
 * Created by KateBay on 31.01.17.
 */
@FunctionalInterface
public interface TreeFunction<T, R> {

    R apply(T value, R left, R right);

}
