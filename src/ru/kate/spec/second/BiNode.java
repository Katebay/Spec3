package ru.kate.spec.second;

import java.util.function.Function;

/**
 * Created by KateBay on 31.01.17.
 */
public class BiNode<V, T> implements Node<V, T> {

    private T biInfo;
    private Node<V, T> left, right;

    public BiNode(T biInfo, Node<V, T> left, Node<V, T> right) {
        this.biInfo = biInfo;
        this.left = left;
        this.right = right;
    }

    @Override
    public <R> R process(Function<V, R> leafProcessor, TreeFunction<T, R> biNodeProcessor) {
        return biNodeProcessor.apply(
                biInfo,
                left.process(leafProcessor, biNodeProcessor),
                right.process(leafProcessor, biNodeProcessor)
        );
    }

}
