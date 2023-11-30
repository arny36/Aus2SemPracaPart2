package node;

import data.Block;
import data.IData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class Hash<T extends IData> {

    private int blockFactor;


    private RandomAccessFile file;

    public Hash(int blockFactor,String fileName) {
        this.blockFactor = blockFactor;
        try {
            this.file = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    public boolean insertHash(T data ,int index) throws IOException {
        Block<T> block = new Block<T>(blockFactor, (Class<T>) data.getClass());
        byte[] blockBytes = new byte[block.getSize()];

        file.seek(block.getSize()*index);
        file.read(blockBytes);
        block.fromByteArray(blockBytes);



        if (block.canBeInserted(data)) {
            file.seek( block.getSize() * index);
            file.write(block.toByteArray());
            return true;
        }



        return false;
    }

    public ArrayList<T> getCurrentData(Class TClass , int index) throws IOException {
        ArrayList<T> currentData = new ArrayList<>();
        Block<T> block = new Block<>(blockFactor,TClass);
        byte[] blockBytes = new byte[block.getSize()];

        file.seek(block.getSize() * index);
        file.read(blockBytes);


        block.fromByteArray(blockBytes);

        double currentDataFromBlock = block.getCurrentDataInBlock();
        for (int i = 0; i < currentDataFromBlock; i++) {
            currentData.add(block.getRecords().get(i));
        }



        return currentData;
    }



    public void showMeData(Class TClass , int index) throws IOException {
        ArrayList<T> finalArray = getCurrentData(TClass, index);
        for (T item:finalArray) {
            item.getAllData();
        }
    }


    public void clearIndexData(Class TClass , int index) throws IOException {
        Block<T> block = new Block<>(blockFactor,TClass);
        byte[] blockBytes = new byte[block.getSize()];
        for (int i = 0; i < blockBytes.length; i++) {
            blockBytes[i] = 0;
        }
        file.seek(block.getSize() * index);

        file.write(blockBytes);

    }

    public boolean findSpecificData(T data ,int index) throws IOException {
        Block<T> block = new Block<>(blockFactor,(Class<T>) data.getClass());
        byte[] blockBytes = new byte[block.getSize()];

        file.seek(block.getSize() * index);
        file.read(blockBytes);
        block.fromByteArray(blockBytes);

        double currentDataFromBlock = block.getCurrentDataInBlock();
        for (int i = 0; i < currentDataFromBlock; i++) {
            if(block.getRecords().get(i).ownEquals(data)) {
                return true;
            }
        }
        return false;
    }
}
