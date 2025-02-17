package com.solovev;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Tree<T> implements Iterable<T> {

    private T root;

    private final Map<T, List<T>> nodes = new HashMap<>();

    public Tree() {
    }

    public Tree(T root) {
        addRoot(root);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return BFS();
    }

    public Iterator<T> BFS() {
        return new Iterator<>() {
            final Queue<T> queue = new ArrayDeque<>();

            {
                queue.add(root);
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                T current = queue.poll();
                queue.addAll(nodes.get(current));
                return current;
            }
        };
    }

    public Iterator<T> DFS() {
        return new Iterator<>() {
            final Deque<T> stack = new ArrayDeque<>();

            {
                stack.push(root);
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                T current = stack.poll();
                nodes.get(current).forEach(stack::push);
                return current;
            }
        };
    }

    public void iterate(Consumer<T> actionIfLeaf, Consumer<T> actionIfBranch) {
        this.forEach(n -> {
            if (isLeaf(n)) {
                actionIfLeaf.accept(n);
            } else {
                actionIfBranch.accept(n);
            }
        });
    }

    public void addRoot(T possibleRoot) {
        if (nonNull(this.root)) {
            throw new IllegalArgumentException("Root is already added");
        }
        this.root = possibleRoot;
        nodes.put(possibleRoot, new ArrayList<>());
    }

    public void add(@NotNull T parent, T child, @NotNull List<T> grandChildren) {
        add(parent, child);
        grandChildren.forEach(n -> add(child, n));
    }

    public void add(@NotNull T parent, T child) {
        if (nodes.containsKey(child)) {
            throw new IllegalArgumentException("Found loop in tree graph, already contains child: " + child);
        }
        addToParent(parent, child);
        nodes.put(child, new ArrayList<>());
    }

    public boolean isLeaf(T node) {
        List<T> children = nodes.get(node);
        if (isNull(children)) {
            throw new IllegalArgumentException("Node does not belong to this tree");
        }
        return children.isEmpty();
    }

    private void addToParent(@NotNull T parent, @NotNull T child) {
        if (!nodes.containsKey(parent)) {
            addRoot(parent);
        }
        nodes.get(parent).add(child);
    }
}
