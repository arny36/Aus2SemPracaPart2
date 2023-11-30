package data;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Block  <T extends IData> implements IRecord{
    private final Class<T> classType;
    private final int blockFactor;

    private int currentDataInBlock;
    private final ArrayList<T> records;

    public Block(int blockFactor, Class<T> classType) {
        this.classType = classType;
        this.blockFactor = blockFactor;
        records = new ArrayList<T>(blockFactor);
        for (int i = 0; i < blockFactor; i++) {
            try {
                this.records.add((T) this.classType.getDeclaredConstructor().newInstance().createClass());
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }




    }




    public boolean canBeInserted (T data){


        if (this.currentDataInBlock >= this.blockFactor ) {
            return false;
        }

        for(int i = 0; i < this.currentDataInBlock; i++) {
            if(this.records.get(i).ownEquals(data)) {
                return false;
            }
        }

        records.set(this.currentDataInBlock, data);
        this.currentDataInBlock++;
        return true;

    }

    @Override
    public int getSize() {
        try {
            return ((classType.getDeclaredConstructor().newInstance().getSize() * blockFactor) + Integer.BYTES);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        DataOutputStream opS = new DataOutputStream(bAOS);
        try  {
            opS.writeInt(this.currentDataInBlock);
            for (int i = 0; i < currentDataInBlock; i++) {
                opS.write(records.get(i).toByteArray());

            }
            return bAOS.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize block", e);
        }
    }

    @Override
    public void fromByteArray(byte[] array) throws IOException {
        ByteArrayInputStream bAIS = new ByteArrayInputStream(array);
        DataInputStream ipS = new DataInputStream(bAIS);

        this.currentDataInBlock = ipS.readInt();
        for (int i = 0; i < this.currentDataInBlock; i++) {
            byte[] bytes = Arrays.copyOfRange(array, Integer.BYTES+((this.getSize()-Integer.BYTES)/blockFactor)*i,
                    Integer.BYTES+((this.getSize()-Integer.BYTES)/blockFactor)*(i+1));
            try {
                records.get(i).fromByteArray(bytes);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void getAllData() {

        System.out.println("Validne udaje: " + currentDataInBlock);
        for (int i = 0; i < currentDataInBlock; i++) {
            records.get(i);
        }
    }

    public Class<T> getClassType() {
        return classType;
    }

    public int getBlockFactor() {
        return blockFactor;
    }


    public ArrayList<T> getRecords() {
        return records;
    }

    public void getAllRecords() {
        for (T item:this.records) {
            System.out.println(item.getHash());
        }

    }

    public int getCurrentDataInBlock() {
        return currentDataInBlock;
    }
}
