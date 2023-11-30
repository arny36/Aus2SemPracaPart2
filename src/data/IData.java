package data;

import java.util.BitSet;

public interface IData<T> extends IRecord<T>{
    BitSet getHash();
    boolean ownEquals(T data);
    T createClass();

}
