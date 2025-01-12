package org.example;

import java.util.Arrays;

public class PriorityQueueMinHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public PriorityQueueMinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = (T[]) new Comparable[capacity];
    }

    public void enqueue(T value) {
        if (size == capacity) {
            expandHeap();
        }
        heap[size] = value;
        int index = size;
        int parent = (size - 1) / 2;
        while(heap[index].compareTo(heap[parent]) < 0) {
            swap(parent, index);
            index = parent;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Pusta kolejka");
        }
        T min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        minHeapify(0);
        return min;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private void minHeapify(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int smallest = index;

        if(leftChild < size && heap[leftChild].compareTo(heap[smallest]) < 0) {
            smallest = leftChild;
        }
        if (rightChild < size && heap[rightChild].compareTo(heap[smallest]) < 0) {
            smallest = rightChild;
        }
        if (smallest != index) {
            swap(index, smallest);
            minHeapify(smallest);
        }
    }

    private void swap(int indexOne, int indexTwo){
        T temp = heap[indexOne];
        heap[indexOne] = heap[indexTwo];
        heap[indexTwo] = temp;
    }


    private void expandHeap() {
        capacity *= 2;
        heap = Arrays.copyOf(heap, capacity);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Kolejka jest pusta";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < size; i++){
            sb.append(heap[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public int getSize() {
        return size;
    }
}