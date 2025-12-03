package com.library.util;

@FunctionalInterface
public interface IdentityExtractor<T> {
    Object getIdentity(T obj);
}
