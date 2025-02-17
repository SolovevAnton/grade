package com.solovev;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Nested
    @DisplayName("Node Tree Tests")
    class NodeTreeTests {
        @Test
        public void valuesDisplayNameCorrectly() {
            var tree = createNodeTree();
            var expectedValues =
                    List.of("branch value 1", "branch value 2", "branch value 3", "leaf value 4", "leaf value 5",
                            "leaf value 6", "leaf value 7");
            //when
            assertEquals(expectedValues, collectIterator(tree.BFS()));
        }

        @Test
        public void consumeLeafOrBranch() {
            //given
            var tree = createNodeTree();

            var result = new ArrayList<String>();
            Consumer<Node> ifLeaf = _ -> result.add("I found leaf");
            Consumer<Node> ifBranch = _ -> result.add("I found branch");
            //when
            tree.iterate(ifLeaf, ifBranch);
            assertEquals(List.of("I found branch", "I found branch", "I found branch", "I found leaf", "I found leaf",
                    "I found leaf", "I found leaf"), result);
        }

        private Tree<Node> createNodeTree() {
            Tree<Node> tree = new Tree<>();
            Node root = new IntegerNode(1, tree);

            Node child1 = new IntegerNode(2, tree);
            Node child2 = new IntegerNode(3, tree);
            Node child3 = new IntegerNode(4, tree);

            Node grandChild1 = new IntegerNode(5, tree);
            Node grandChild2 = new IntegerNode(6, tree);
            Node grandChild3 = new IntegerNode(7, tree);

            tree.add(root, child1, List.of(grandChild1, grandChild2));
            tree.add(root, child2, List.of(grandChild3));
            tree.add(root, child3);
            return tree;
        }
    }

    @Nested
    @DisplayName("Tree Creation Tests")
    class TreeCreationTests {

        private final Class<? extends Throwable> expectedException = IllegalArgumentException.class;

        @Test
        @DisplayName("Create tree with only root")
        void testTreeWithOnlyRoot() {
            Node root = () -> "root";
            var tree = new Tree<>(root);
            assertNotNull(tree);
        }

        @Test
        @DisplayName("addRoot test")
        void testTreeAddRootRoot() {
            Node root = () -> "root";
            var tree = new Tree<>();

            assertDoesNotThrow(() -> tree.addRoot(root));
        }

        @Test
        @DisplayName("existing root throws test")
        void testTreeExistingRootThrows() {
            Node root = () -> "root";
            var tree = new Tree<>(root);
            assertThrows(expectedException, () -> tree.addRoot(root));
        }

        @Test
        @DisplayName("Tree with multiple branches and leaves")
        void testTreeWithBranchesAndLeaves() {
            assertDoesNotThrow(TreeTest.this::buildGenericTree);
        }

        @Test
        @DisplayName("Adding node to non-existent parent should fail")
        void testAddingNodeToInvalidParent() {
            var tree = buildGenericTree();
            Node invalidParent = () -> "99";
            Node newNode = () -> "100";
            assertThrows(expectedException, () -> tree.add(invalidParent, newNode));
        }

        @Test
        @DisplayName("Loop to tree should fail")
        void testAddingLoopedChildren() {
            Node root = () -> "root";
            var tree = new Tree<>(root);
            Node child1 = () -> "99";
            Node loopedChild = () -> "100";
            tree.add(root, loopedChild);
            assertThrows(expectedException, () -> tree.add(root, child1, List.of(loopedChild)));
            assertThrows(expectedException, () -> tree.add(child1, child1));
            assertThrows(expectedException, () -> tree.add(child1, loopedChild));
            assertThrows(expectedException, () -> tree.add(loopedChild, child1));
        }
    }

    @Nested
    @DisplayName("BFS Traversal Tests")
    class BFSTests {

        @Test
        @DisplayName("Breadth-first traversal returns correct order")
        void testBFSOrder() {
            var tree = buildGenericTree();
            Iterator<Node> bfsIterator = tree.BFS();
            List<String> bfsOrder = collectIterator(bfsIterator);
            assertEquals(List.of("1", "2", "3", "4", "5", "6", "7"), bfsOrder);
        }

        @Test
        @DisplayName("For Each possible test")
        void testDefaultIterable() {
            var tree = buildGenericTree();
            List<String> defaultOrder = new ArrayList<>();
            for (Node node : tree) {
                defaultOrder.add(node.print());
            }
            assertEquals(List.of("1", "2", "3", "4", "5", "6", "7"), defaultOrder);
        }

        @Test
        @DisplayName("BFS on single node tree")
        void testBFSWithOnlyRoot() {
            Node root = () -> "root";
            var tree = new Tree<>(root);
            Iterator<Node> bfsIterator = tree.BFS();
            assertEquals(List.of("root"), collectIterator(bfsIterator));
        }
    }

    @Nested
    @DisplayName("DFS Traversal Tests")
    class DFSTests {

        @Test
        @DisplayName("Depth-first traversal returns correct order")
        void testDFSOrder() {
            var tree = buildGenericTree();
            Iterator<Node> dfsIterator = tree.DFS();
            List<String> dfsOrder = collectIterator(dfsIterator);
            assertEquals(List.of("1", "4", "3", "7", "2", "6", "5"), dfsOrder);
        }

        @Test
        @DisplayName("DFS on single node tree")
        void testDFSWithOnlyRoot() {
            Node root = () -> "root";
            var tree = new Tree<>(root);
            Iterator<Node> dfsIterator = tree.DFS();
            assertEquals(List.of("root"), collectIterator(dfsIterator));
        }
    }

    private List<String> collectIterator(Iterator<Node> iterator) {
        List<String> list = new ArrayList<>();
        iterator.forEachRemaining(n -> list.add(n.print()));
        return list;
    }

    private Tree<Node> buildGenericTree() {
        Node root = () -> "1";
        Tree<Node> tree = new Tree<>(root);

        Node child1 = () -> "2";
        Node child2 = () -> "3";
        Node child3 = () -> "4";

        Node grandChildLeaf1 = () -> "5";
        Node grandChildLeaf2 = () -> "6";
        Node grandChildLeaf3 = () -> "7";


        tree.add(root, child1, List.of(grandChildLeaf1, grandChildLeaf2));
        tree.add(root, child2, List.of(grandChildLeaf3));
        tree.add(root, child3);

        return tree;
    }


}