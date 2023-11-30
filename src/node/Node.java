package node;


abstract class Node<T>  {

    private Node<T> parent;
    private int depth;


    public Node(Node<T> parent , int depth) {
        this.depth = depth;
        this.parent = parent;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
