package me.whiteship.chapter04.item24.adapter;

import java.io.*;
import java.util.*;

public class AdapterInJava {

    public static void main(String[] args) {
        try(InputStream is = new FileInputStream("number.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr)) {
            while(reader.ready()) {
                System.out.println(reader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
