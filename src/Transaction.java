
import java.util.Random;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PID# 6224488
 */
public class Transaction extends Node {

    String TxID;
    int nodeID;
    String data;
    long timeStamp;

    //constructor
    public Transaction() {
        this.TxID = createTxID();
        this.timeStamp = new Date().getTime();
        this.nodeID = super.nodeID;
    }

    public static String createTxID() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();
        
        //Create random string 20 char long for Tx ID
        String TxID = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        //System.out.println("Test TxID output: " + TxID);
        return TxID;
    }
}
