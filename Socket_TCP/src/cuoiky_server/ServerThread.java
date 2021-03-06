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
                System.out.println("V??n b???n m?? h??a ???? nh???n ???????c l??: " + encrypt);
                System.out.println("Kh??a ???? nh???n ???????c l??: " + key);
                System.out.println("V??n b???n ???? ???????c gi???i m?? l??: " + giaima );
                System.out.println("V??n b???n ???? ???????c x??? l?? (vi???t in hoa) l??: "+ UpperCase);
                Decrypt(encrypt, key);
                return giaima;
    }
    public void gui() throws IOException{
        
        this.out = new PrintWriter(this.socket.getOutputStream(),true);
        System.out.println("V??n b???n tr??? v??? client l??: "+decrypted);
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
