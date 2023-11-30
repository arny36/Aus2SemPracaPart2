package node;


public class Internal<T> extends Node<T> {
    private Node<T> leftSon, rightSon;


    public Internal(Node<T> parent,int depth) {
        super(parent,depth);
    }

    public Node<T> getLeftSon() {
        return leftSon;
    }

    public Node<T> getRightSon() {
        return rightSon;
    }

    public void setLeftSon(Node<T> leftSon) {
        this.leftSon = leftSon;
    }

    public void setRightSon(Node<T> rightSon) {
        this.rightSon = rightSon;
    }
}
