package node;


public class External<T> extends Node<T> {

    private int index;
    private int countData;


    public External(Node<T> parent,int depth) {
        super(parent,depth);
        index =  -1;
        countData = 0;

    }

    public int getIndex() {return index;}

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCountData() {return countData;}

    public void incCountData() {countData++;}

    public void decCountData() {countData--;}

    public void clear() {
        this.index = -1;
        setDepth(0);
        setParent(null);
        this.countData=0;
    }


}
