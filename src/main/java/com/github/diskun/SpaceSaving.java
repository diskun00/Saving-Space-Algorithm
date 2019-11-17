package com.github.diskun;


import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Space Saving Algorithm
 * Any element with (frequency - error) > epsilon * N is guaranteed to be in the count result
 * Created by Diskun on Nov 2019
 */
public class SpaceSaving<T> {
    private int capacity;
    /* bucket in descending order based on count value */
    private DoubleLinkedList<Bucket<T>> bucketList;
    private Map<T, Counter<T>> counterMap = new HashMap<>();

    public SpaceSaving(float epsilon) {
        if(epsilon <= 0 && epsilon > 1) {
            throw new InvalidParameterException("Epsilon needs to be in range(0,1]");
        }
        this.capacity = (int) Math.ceil(1.0 / epsilon);
        //init bucket list
        bucketList = new DoubleLinkedList<>();
        Bucket<T> headBucket = new Bucket<>();
        headBucket.setValue(0);
        DoubleLinkedList.Node<Bucket<T>> headNode = new DoubleLinkedList.Node<>(headBucket);
        bucketList.addAfterTail(headNode);
        //init counter
        for (int i = 0; i < this.capacity - 1; i++) {
            headBucket.getChildrenCounter().add(new Counter<>());
        }
    }

    /**
     * read single item
     *
     * @param item item
     */
    public void readItem(T item) {
        Counter<T> counter = counterMap.get(item);
        if (counter != null) {
            DoubleLinkedList.Node<Bucket<T>> currentBucketNode = counter.getParentBucket();
            increaseCounter(counter, currentBucketNode);
        } else {
            DoubleLinkedList.Node<Bucket<T>> minBucketNode = bucketList.getTail();
            Bucket<T> minBucket = minBucketNode.getItem();
            long currentCount = minBucket.getValue();
            Counter<T> minCounter = minBucket.getFirstCounter();
            minCounter.setError(currentCount);
            minCounter.setElement(item);
            increaseCounter(minCounter, minBucketNode);
            counterMap.put(item, minCounter);
        }
    }

    /**
     * increase counter by one to corresponding bucket
     *
     * @param counter           counter to move
     * @param currentBucketNode current bucket node
     */
    private void increaseCounter(Counter<T> counter, DoubleLinkedList.Node<Bucket<T>> currentBucketNode) {
        Bucket<T> currentBucket = currentBucketNode.getItem();
        long newCount = currentBucket.getValue() + 1;
        DoubleLinkedList.Node<Bucket<T>> previousBucketNode = currentBucketNode.getPrevious();

        if (previousBucketNode == null || previousBucketNode.getItem().getValue() != newCount) {
            //if no corresponding bucket exists or if it's head node, then create a new bucket
            new Bucket<>(newCount);
            previousBucketNode = new DoubleLinkedList.Node<>(new Bucket<>(newCount));
            bucketList.addBeforeNode(currentBucketNode, previousBucketNode);

        }
        //move counter to previous bucket
        counter.setParentBucket(previousBucketNode);
        Bucket<T> previousBucket = previousBucketNode.getItem();
        previousBucket.getChildrenCounter().add(counter);
        currentBucket.getChildrenCounter().remove(counter);
        if (currentBucket.getChildrenCounter().isEmpty()) {
            //remove empty bucket
            bucketList.remove(currentBucketNode);
        }
    }

    /**
     * get top k items
     *
     * @param k number of items to return
     * @return list of k item in order
     */
    public List<T> getTopK(int k) {
        return bucketList.stream()
                .flatMap(e -> e.getChildrenCounter().stream())
                .map(Counter::getElement)
                .limit(k)
                .collect(Collectors.toList());
    }


}
