package com.github.diskun;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Double Linkded List
 * Created by Diskun on Nov 2019
 */
public class DoubleLinkedList<T> implements Iterable<T> {

    public static class Node<T> {
        private Node<T> previous;
        private Node<T> next;
        private T item;

        public Node(T item) {
            this.item = item;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }
    }

    private Node<T> head;

    private Node<T> tail;

    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public DoubleLinkedList(List<T> items) {
        for (T item : items) {
            this.addAfterTail(new Node<>(item));
        }
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setTail(Node<T> tail) {
        this.tail = tail;
    }

    public synchronized void addAfterTail(Node<T> newNode){
        addAfterNode(tail, newNode);
    }

    public synchronized void addBeforeHead(Node<T> newNode){
        addBeforeNode(head, newNode);
    }

    /**
     * add node after specific node
     * @param existedNode existed node
     * @param newNode new node to add
     */
    public synchronized void addAfterNode(Node<T> existedNode, Node<T> newNode) {
        if(head == null){
            //empty list
            head = newNode;
            tail = newNode;
            return;
        }
        Node<T> oldNext = existedNode.getNext();
        existedNode.setNext(newNode);
        newNode.setPrevious(existedNode);
        newNode.setNext(oldNext);
        if (oldNext == null) {
            //existedNode is the tail node
            tail = newNode;
        } else {
            oldNext.setPrevious(newNode);
        }
    }

    /**
     *
     * add node before specific node
     * @param existedNode the existed node
     * @param newNode the node to add
     */
    public synchronized void addBeforeNode(Node<T> existedNode, Node<T> newNode) {
        if(tail == null){
            //empty list
            tail = newNode;
            head = newNode;
            return;
        }
        Node<T> oldPrevious = existedNode.getPrevious();
        existedNode.setPrevious(newNode);
        newNode.setNext(existedNode);
        newNode.setPrevious(oldPrevious);
        if (oldPrevious == null) {
            //existedNode is the head node
            head = newNode;
        } else {
            oldPrevious.setNext(newNode);
        }
    }

    /**
     * remove a node
     * @param node node to remove
     */
    public synchronized void remove(Node<T> node){
        Node<T> next = node.getNext();
        Node<T> previous = node.getPrevious();
        //previous link
        if(previous == null){
            //head node
            head = next;
            next.previous = null;
        }else if(next == null){
            //tail node
            tail = previous;
            previous.next = null;
        }else{
            previous.next = next;
            next.previous = previous;
        }
    }

    public Stream<T> stream(){
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private DoubleLinkedList.Node<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                Node<T> temp = curr;
                curr = curr.next;
                return temp.getItem();
            }

            @Override
            public void remove() {
            }
        };
    }


}
