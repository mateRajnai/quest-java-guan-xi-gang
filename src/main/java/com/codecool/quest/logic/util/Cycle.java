package com.codecool.quest.logic.util;

public class Cycle<T> {

    private static class Node<T> {
        private T value;
        private Node<T> next;

        protected Node(T object) {
            this.value = object;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T value() {
            return value;
        }

        public Node<T> next() {
            return next;
        }
    }

    private Node<T> head = null;
    private Node<T> tail = null;
    private Node<T> current = null;

    public void add(T value) {
        if (head == null) {
            head = new Node<>(value);
            head.setNext(null);
        } else if (tail == null) {
            tail = new Node<>(value);
            head.setNext(tail);
            tail.setNext(head);
        } else {
            Node<T> temp = head;
            while (temp != tail)
                temp = temp.next();
            Node<T> newNode = new Node<>(value);
            tail.setNext(newNode);
            newNode.setNext(head);
            tail = newNode;
        }
    }

    public void forward() {
        if (current == null)
            current = head;
        else
            current = current.next();
    }

    public T getNext() {
        if (current == null)
            return head.value();
        else
            return current.next().value();
    }

    public T current() {
        if (current == null)
            return null;
        return current.value();
    }

}
