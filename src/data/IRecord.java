package data;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface IRecord<T>{


    int getSize();
    byte[] toByteArray();
    void fromByteArray(byte[] array) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    void getAllData();
}
