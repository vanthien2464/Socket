/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuoiky_server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *
 * @author XPS
 */
public class ServerThread implements Runnable {
     private String encrypt;   
     private String decrypted;
     private String key;
     private byte[] decrypted_byte;
    
    
    private Scanner in = null;
    private PrintWriter out = null;
    private Socket socket;
    private String name;
    
    public ServerThread(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.name = name;
        this.in = new Scanner (this.socket.getInputStream());       
        new Thread(this).start();
    }

    @Override
    public void run() {
        //System.out.println(" encrypt1: "+encrypt);
        //System.out.println(" key2: "+key);
        try{
                String chuoi = in.nextLine().trim();
                Scanner sc = new Scanner(chuoi);
                sc.useDelimiter("@");
                if(encrypt==null)
                    encrypt = sc.next(); 
                if(key==null)
                    key = sc.next(); 
                //System.out.println(" encrypt: "+encrypt);
                //System.out.println(" key: "+key);
            
        }catch(Exception e){
            //System.out.println(name+" has departed");
            System.out.println("======================");
      
        }
    }
    public String GetEncryptString(){
        //System.out.println("Chuoi no la "+encrypt);
        
        return encrypt;
    }
    
    public String GetEncryptKey(){
        return key;
    }
    
    
    public String StringGiaiMa(){
                String giaima = decrypt(encrypt, key);
                String UpperCase = giaima.toUpperCase();   
                System.out.println("Văn bản mã hóa đã nhận được là: " + encrypt);
                System.out.println("Khóa đã nhận được là: " + key);
                System.out.println("Văn bản đã được giải mã là: " + giaima );
                System.out.println("Văn bản đã được xử lý (viết in hoa) là: "+ UpperCase);
                Decrypt(encrypt, key);
                return giaima;
    }
    public void gui() throws IOException{
        
        this.out = new PrintWriter(this.socket.getOutputStream(),true);
        System.out.println("Văn bản trả về client là: "+decrypted);
        System.out.println("======================");
        out.println(new String(decrypted).toUpperCase());
    }
    
    public String decrypt(String encryptText, String key) {
                String s = null;
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
                        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
                        SecretKey desKey = skf.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptText));
			s = (new String(decrypted));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
                return s;
	}
    
    public void Decrypt(String encryptText, String key) {
                //String s = null;
		try {	
			DESKeySpec dks = new DESKeySpec(key.getBytes());
                        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
                        SecretKey desKey = skf.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			byte[] decrypted_byte = cipher.doFinal(Base64.getDecoder().decode(encryptText));
                        decrypted=new String(decrypted_byte).toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
}
