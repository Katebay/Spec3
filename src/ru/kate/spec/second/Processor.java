package ru.kate.spec.second;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

/**
 * Created by KateBay on 31.01.17.
 */
public class Processor {

    private final static Map<Character, DoubleBinaryOperator> operations = new HashMap<Character, DoubleBinaryOperator>() {{
        put('+', (a, b) -> a + b);
        put('-', (a, b) -> a - b);
        put('*', (a, b) -> a * b);
        put('/', (a, b) -> a / b);
    }};

    /**
     * Method that evaluates the expression, starting in the given root-node of tree
     * @param root starting (in terms of walking) node of the tree
     */
    public static double evaluate(Node<Double, Character> root) {
        return root.process(Function.identity(), (o, l, r) -> operations.get(o).applyAsDouble(l, r));
    }

    /**
     * Turns tree, starting in given root-node, into mathematical sentence with operations order being saved
     * @param root starting (in terms of walking) node of the tree
     * @return mathematical sentence with saving of operations order
     */
    public static String toString(Node<Double, Character> root) {
        return root.process(Object::toString, (o, l, r) -> String.format("(%s%c%s)", l.toString(), o, r.toString()));
    }

    /**
     * Method that inverts tree, starting in given root node.
     * @param root starting (in terms of walking) node of the three
     * @return tree with inverted leaves
     */
    public static Node<Double, Character> createInvertedTree(Node<Double, Character> root) {
        return root.process(v -> (Node<Double, Character>) new Leaf<Double, Character>(-v), BiNode::new);
    }

}
