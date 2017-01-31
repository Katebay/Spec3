package ru.kate.spec.second;

import java.util.function.Function;

/**
 * Created by KateBay on 31.01.17.
 */
public interface Node<V, T> {

    <R> R process(Function<V,R> leafProcessor, TreeFunction<T,R> biNodeProcessor);
}
