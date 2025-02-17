package com.solovev;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntegerNode implements Node {
    private final int value;
    private final Tree<Node> tree;

    @Override
    public String print() {
        String part = tree.isLeaf(this) ? "leaf" : "branch";
        return part + " value " + value;
    }
}
