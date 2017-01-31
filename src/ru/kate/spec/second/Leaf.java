package ru.kate.spec.second;

import java.util.function.Function;

/**
 * Created by KateBay on 31.01.17.
 */
public class Leaf<V, T> implements Node<V, T> {

    private V leafInfo;

    public Leaf(V leaf) {
        leafInfo = leaf;
    }

    public <R> R process(Function<V,R> leafProcessor, TreeFunction<T,R> biNodeProcessor) {
        return leafProcessor.apply(leafInfo);
    }
}
