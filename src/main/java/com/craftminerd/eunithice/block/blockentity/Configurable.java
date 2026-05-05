package com.craftminerd.eunithice.block.blockentity;

import java.util.Collection;

public interface Configurable<T> {
    void setProperty(String name, T value);
    T getProperty(String name);

    Collection<String> getProperties();
}
