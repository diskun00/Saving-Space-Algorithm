package com.github.diskun;

import java.util.LinkedList;

/**
 * bucket to store count value
 * Created by Diskun on Nov 2019
 */
public class Bucket<T> {
    private long value;
    private LinkedList<Counter<T>> childrenCounter = new LinkedList<>();

    public Bucket() {
    }

    public Bucket(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public LinkedList<Counter<T>> getChildrenCounter() {
        return childrenCounter;
    }

    public void setChildrenCounter(LinkedList<Counter<T>> childrenCounter) {
        this.childrenCounter = childrenCounter;
    }

    public Counter<T> getFirstCounter() {
        return childrenCounter.getFirst();
    }
}
