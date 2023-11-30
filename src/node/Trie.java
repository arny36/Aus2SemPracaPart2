package node;



import data.IData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;


public class Trie<T extends IData> extends Hash<T> {

    private final Internal<T> root;

    private int blockFactor;

    private ArrayList<Integer> freeIndexes = new ArrayList<>();


    private int maxIndex=0;


    public Trie(int blockFactor, String fileName) {
        super(blockFactor,fileName);
        this.root = new Internal<>(null,0);
        this.root.setLeftSon(new External<>(this.root,1));
        this.root.setRightSon(new External<>(this.root,1));
        this.blockFactor=blockFactor;
    }


    public boolean insert(T data) throws IOException {
        Node<T> node = findNodeExternal(data);

        if (((External<T>) node).getCountData() < this.blockFactor) {
            changeIndex((External<T>) node);
            if (insertHash(data,((External<T>) node).getIndex())){
                ((External<T>) node).incCountData();
                return true;
            }
        }
        else {
            ArrayList<T> currentData ;
            LinkedList<T> allData = new LinkedList<>();
            allData.push(data);
            Internal<T> trieParent;
            Internal<T> newParent;
            T actualData=null;
            int index= ((External<T>) node).getIndex();
            boolean isRightSon;

            while (allData.size()>0) {

                currentData = getCurrentData(data.getClass(), index);
                for (T item : currentData) {
                    allData.push(item);
                }


                trieParent = (Internal<T>) node.getParent();
                isRightSon = (trieParent.getRightSon().equals(node));
                newParent = createSons(node, isRightSon);
                clearIndexData(data.getClass(),index);



                while(allData.size()>0) {
                    if (actualData==null) {actualData=allData.pop();}
                    BitSet itemHash = actualData.getHash();
                    node = itemHash.get(newParent.getDepth()) ? newParent.getRightSon() : newParent.getLeftSon();
                    changeIndex((External<T>) node);
                    if (((External<T>) node).getCountData() < this.blockFactor) {
                        if (insertHash(actualData, ((External<T>) node).getIndex())) {
                            ((External<T>) node).incCountData();
                            actualData = null;
                        }
                    } else {
                        index = ((External<T>) node).getIndex();
                        break;
                    }
                }
            }
        }
        return false;

    }

    public boolean find(T data) throws IOException {
        Node<T> node = findNodeExternal(data);
        int index = ((External<T>) node).getIndex();
        return findSpecificData(data,index);
    }
    private Internal<T> createSons(Node<T> node,boolean isRightSon) {
        int depth = node.getDepth();
        Internal<T> newInternal = new Internal<>(node.getParent(),depth);
        newInternal.setLeftSon(new External<>(newInternal, depth + 1));
        newInternal.setRightSon(new External<>(newInternal, depth + 1));
        this.freeIndexes.add(((External<T>) node).getIndex());
        if (isRightSon) {
            ((Internal<T>) node.getParent()).setRightSon(newInternal);
        } else {
            ((Internal<T>) node.getParent()).setLeftSon(newInternal);
        }
        ((External<T>) node).clear();
        return  newInternal;

    }

    private Node<T> findNodeExternal(T data) {
        Node<T> nodeToFind = this.root;
        BitSet bites =data.getHash();
        int index = 0;
        boolean found = false;

        while (!found) {
            if (bites.get(index)) {
                if (nodeToFind instanceof Internal<T>) {
                    nodeToFind = ((Internal) nodeToFind).getRightSon();
                } else {
                    found=true;
                }
            } else {
                if (nodeToFind instanceof Internal<T>) {
                    nodeToFind = ((Internal) nodeToFind).getLeftSon();
                } else {
                    found=true;
                }
            }
            index++;
        }
        return nodeToFind;
    }

    private void  changeIndex(External<T> node) {
        if (node.getIndex() == -1) {
            if (freeIndexes.size() > 0) {
                node.setIndex(freeIndexes.get(0));
                freeIndexes.remove(freeIndexes.get(0));
            } else {
                node.setIndex(this.maxIndex);
                this.maxIndex++;
            }
        }
    }

    public int getMaxIndex() {
        return maxIndex;
    }
}
