package tester;

import data.GPS;
import data.Property;
import node.Trie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Tester {

    public boolean testInsert(int counter) throws IOException {
        Property property ;
        GPS g1 ;
        GPS g2;
        Random rand = new Random();
        String note= "";
        ArrayList<Property> allPropertyAdded = new ArrayList<>();
        Trie<Property> trie = new Trie<>(10, "fileToHash.dat");
        int identifier=0;
        for (int i = 0; i < counter; i++) {

            g1=new GPS(rand.nextInt(100), rand.nextInt(100));
            g2=new GPS(rand.nextInt(100), rand.nextInt(100));
            for (int j = 0; j < 15; j++) {
                char randomChar = (char) ('A' + Math.random() * 26);
                note += randomChar;
            }
            identifier++;
            property= new Property(identifier,g1,g2,note);
            note="";
            trie.insert(property);
            allPropertyAdded.add(property);

        }
//        for (int i = 0; i < trie.getMaxIndex(); i++) {
//            System.out.println(i);
//            trie.showMeData(property.getClass(),i);
//
//        }
        for (Property item:allPropertyAdded){
            if (!trie.find(item)){
                return false;
            }
        }
        return true;


    }

    public boolean testSearch(int counter) throws IOException {
        Property property ;
        GPS g1 ;
        GPS g2;
        Random rand = new Random();
        String note= "";
        ArrayList<Property> allPropertyAdded = new ArrayList<>();
        Trie<Property> trie = new Trie<>(10, "fileToHash.dat");
        int identifier=0;
        for (int i = 0; i < counter; i++) {

            g1=new GPS(rand.nextInt(100), rand.nextInt(100));
            g2=new GPS(rand.nextInt(100), rand.nextInt(100));
            for (int j = 0; j < 15; j++) {
                char randomChar = (char) ('A' + Math.random() * 26);
                note += randomChar;
            }
            identifier++;
            property= new Property(identifier,g1,g2,note);
            note="";
            trie.insert(property);
            allPropertyAdded.add(property);

        }
//        for (int i = 0; i < trie.getMaxIndex(); i++) {
//            System.out.println(i);
//            trie.showMeData(property.getClass(),i);
//
//        }

        return trie.find(allPropertyAdded.get(rand.nextInt(allPropertyAdded.size())));
    }
}
