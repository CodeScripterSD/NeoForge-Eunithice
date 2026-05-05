package com.craftminerd.eunithice.util;

import com.google.common.collect.Iterators;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Objects;

public class ModUtil {

    public static <T> T findNextInIterable(Iterable<T> iterable, @Nullable T element) {
        Iterator<T> iterator = iterable.iterator();
        T t = iterator.next();
        if (element != null) {
            T t1 = t;

            while (!Objects.equals(t1, element)) {
                if (iterator.hasNext()) {
                    t1 = iterator.next();
                }
            }

            if (iterator.hasNext()) {
                return iterator.next();
            }
        }

        return t;
    }

    public static <T> T findPreviousInIterable(Iterable<T> iterable, @Nullable T current) {
        Iterator<T> iterator = iterable.iterator();
        T t = null;

        while (iterator.hasNext()) {
            T t1 = iterator.next();
            if (Objects.equals(t1, current)) {
                if (t == null) {
                    t = iterator.hasNext() ? Iterators.getLast(iterator) : current;
                }
                break;
            }

            t = t1;
        }

        return t;
    }
}
