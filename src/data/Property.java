package data;

import java.io.*;
import java.util.BitSet;

public class Property implements IData<Property> {

    private GPS first, second;

    private String notes;
    private int identifier;
    private int maxNoteLength = 15;

    public Property(int identifier,GPS first, GPS second, String notes) {
        this.first = first;
        this.second = second;
        this.notes = notes;
        this.identifier = identifier;
        this.generateMaxChars();
    }

    public Property() {

    }

    public GPS getFirst() {
        return first;
    }

    public GPS getSecond() {
        return second;
    }

    public String getNotes() {
        return notes;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void generateMaxChars() {
        if (this.notes.length() > this.maxNoteLength) {
            this.notes = this.notes.substring(0, this.maxNoteLength);
        } else if (this.notes.length() < this.maxNoteLength){
            while (this.notes.length() < this.maxNoteLength) {
                char randomChar = (char) ('A' + Math.random() * 26);
                this.notes += randomChar;
            }
        }
    }

    @Override
    public BitSet getHash() {
        return BitSet.valueOf(new long[]{(long) (this.identifier%997)});
    }

    @Override
    public boolean ownEquals(Property data) {
        return ((this.first.getX()==data.getFirst().getX())
                && (this.first.getY()==data.getFirst().getY())
                && (this.second.getX()==data.getSecond().getX())
                && (this.second.getY()==data.getSecond().getY())
                && (this.identifier == data.getIdentifier()));
    }

    @Override
    public Property createClass() {
        return new Property();
    }

    @Override
    public int getSize() {
        return Integer.BYTES + Double.BYTES + Double.BYTES + Double.BYTES + Double.BYTES + Character.BYTES*(this.maxNoteLength) ;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        DataOutputStream opS = new DataOutputStream(bAOS);
        try {
            opS.writeInt(this.identifier);
            opS.writeDouble(this.first.getX());
            opS.writeDouble(this.first.getY());
            opS.writeDouble(this.second.getX());
            opS.writeDouble(this.second.getY());
            opS.writeChars(this.getNotes());
            return bAOS.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Error while convert to byte");
        }

    }

    @Override
    public void fromByteArray(byte[] array) throws IOException  {
        ByteArrayInputStream bAIS = new ByteArrayInputStream(array);
        DataInputStream ipS = new DataInputStream(bAIS);

        try {
            this.identifier = ipS.readInt();
            double f1 = ipS.readDouble();
            double f2 = ipS.readDouble();
            this.first = new GPS(f1,f2);
            double s1 = ipS.readDouble();
            double s2 = ipS.readDouble();
            this.second = new GPS(s1,s2);
            this.notes="";
            for (int i = 0; i < this.maxNoteLength; i++) {
                this.notes+=ipS.readChar();
            }
        } catch (EOFException e ){
            throw new RuntimeException("Error while converting from byte:" + e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalStateException("Error while converting from byte: " + e.getMessage(), e);
        }
    }

    @Override
    public void getAllData() {
        System.out.println(this.identifier + " " + this.getFirst().getX()
                +" " + this.getFirst().getY()
                +" " + this.getSecond().getX()
                +" " + this.getSecond().getY()
                +" " + this.notes);
    }
}

