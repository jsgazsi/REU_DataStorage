
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



public class Block {

    //Blocks contain previous hash and data of transaction
    public String hash;
    public String previousHash;
    public String data; //Tx data stub <- Turn into a Transaction object List
    public long timeStamp;

    //Block Constructor
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }
    
    //Function to calculate Hash
    public String calculateHash() {
        //Calling crypt class
        String hash = crypt.sha256(data + Long.toString(timeStamp) + previousHash);
        
        return hash;
    }
    



}
