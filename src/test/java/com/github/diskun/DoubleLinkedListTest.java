package com.github.diskun;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Diskun on Nov 2019
 */
public class DoubleLinkedListTest {

    @Test
    public void addAfterNode() {

        List<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>(nums);
        DoubleLinkedList.Node<Integer> nodeOf2 = list.getHead().getNext();

        int insertNum = 10;
        list.addAfterNode(nodeOf2, new DoubleLinkedList.Node<>(insertNum));
        ArrayList<Integer> itemList = new ArrayList<>();
        list.forEach(itemList::add);
        nums.add(2,insertNum);
        Assert.assertEquals(itemList,nums);

    }

    @Test
    public void addBeforeNode() {
        List<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>(nums);
        DoubleLinkedList.Node<Integer> nodeOf3 = list.getHead().getNext().getNext();

        int insertNum = 10;
        list.addBeforeNode(nodeOf3, new DoubleLinkedList.Node<>(insertNum));
        ArrayList<Integer> itemList = new ArrayList<>();
        list.forEach(itemList::add);
        nums.add(2,insertNum);
        Assert.assertEquals(itemList,nums);
    }

    @Test
    public void remove() {
        List<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>(nums);
        DoubleLinkedList.Node<Integer> nodeOf3 = list.getHead().getNext().getNext();

        list.remove(nodeOf3);
        ArrayList<Integer> itemList = new ArrayList<>();
        list.forEach(itemList::add);
        nums.remove(2);
        Assert.assertEquals(itemList,nums);
    }
}