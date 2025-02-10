package com.solovev;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class MyList<T> extends ArrayList<T> {

    public <A> List<A> map(Function<? super T, A> function) {
        List<A> result = new ArrayList<>();
        for (T it : this) {
            result.add(function.apply(it));
        }
        return result;
    }

    /**
     * @param function to use for reduction
     * @return reduced value. Will be NULL for empty stream
     */
    public T reduce(BinaryOperator<T> function) {
        if (isEmpty()) {
            return null;
        }
        T result = get(0);
        for (int i = 1; i < size(); i++) {
            result = function.apply(result, get(i));
        }
        return result;
    }

}
