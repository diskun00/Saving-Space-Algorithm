package com.github.diskun;

/**
 * Created by Diskun on Nov 2019
 */
public class Counter<T> {

    private T element;
    private long error;
    private Counter<T> nextCounter;
    private DoubleLinkedList.Node<Bucket<T>> parentBucket;

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public long getError() {
        return error;
    }

    public void setError(long error) {
        this.error = error;
    }

    public Counter<T> getNextCounter() {
        return nextCounter;
    }

    public void setNextCounter(Counter<T> nextCounter) {
        this.nextCounter = nextCounter;
    }

    public DoubleLinkedList.Node<Bucket<T>> getParentBucket() {
        return parentBucket;
    }

    public void setParentBucket(DoubleLinkedList.Node<Bucket<T>> parentBucket) {
        this.parentBucket = parentBucket;
    }
}
