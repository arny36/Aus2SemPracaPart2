import tester.Tester;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try (FileOutputStream fos = new FileOutputStream("fileToHash.dat", false)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tester tester = new Tester();
        if(tester.testInsert(10000)){
            System.out.println("Insert úspešne prebehol");
        }
//        try (FileOutputStream fos = new FileOutputStream("fileToHash.dat", false)) {
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if(tester.testSearch(10000)){
//            System.out.println("Search úspešne prebehol");
//        }



//        GPS g1 ;
//        GPS g2;
//        g1=new GPS(0, 10);
//        g2=new GPS(0, 50);
//        Property property = new Property(1,g1,g2,"123456789112546");
//        Property property2 = new Property();
//        byte[] b = property.toByteArray();
//        property.getAllData();
//        property2.fromByteArray(b);
//        property2.getAllData();




    }
}