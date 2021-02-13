import java.util.*;
import java.security.*;
import java.security.spec.*;
import java.math.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.lang.Math;
import java.util.regex.*;

public class PSDMGR{
	public static void main(String args[]) throws NoSuchAlgorithmException,InvalidKeySpecException{
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter Website Name: ");
		String WebsiteName=sc.nextLine();
		System.out.print("Enter UserName Name: ");
		String UserName=sc.nextLine();
		System.out.print("Strong Password: ");
		String Password="";
		do{
			Password=GenerateRandom();
		}while(!CheckStrength(Password));
		System.out.print(Password+"\n");
		String HashedPassword=GeneratePSD(Password);
		String A[]=HashedPassword.split(":");
		System.out.println("Iterations: "+A[0]);
		System.out.println("Salt: "+A[1]);
		System.out.println("Encrypted Password: "+A[2]);
		
	}
	private static String GenerateRandom(){
		String PSDString="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*()_+=|<>?{}\\[]~-";
		String PSDChars[]=PSDString.split("");
		int max=PSDChars.length;
		int min=0;
		int range=max-min;
		String Password="";
		for (int i = 0; i < 12; i++) { 
			int rand = (int)(Math.random() * range) + min; 
			Password+=PSDChars[rand];
		}
		return Password;		
	}
	private static boolean CheckStrength(String Password){
		if(Password.length()==12){
			Pattern letter = Pattern.compile("[a-zA-z]");
			Pattern digit = Pattern.compile("[0-9]");
			Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            Matcher hasLetter = letter.matcher(Password);
			Matcher hasDigit = digit.matcher(Password);
			Matcher hasSpecial = special.matcher(Password);
			return hasLetter.find() && hasDigit.find() && hasSpecial.find();
		}
		else{
			return false;
		}
	}
	private static String GeneratePSD(String Password) throws NoSuchAlgorithmException, InvalidKeySpecException{
		int iterations=100000;
		char PSDChars[]=Password.toCharArray();
		byte Salt[]=GenerateSalt();
		
		PBEKeySpec KeySpec=new PBEKeySpec(PSDChars,Salt,iterations,128*8);
		SecretKeyFactory SKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = SKeyFactory.generateSecret(KeySpec).getEncoded();
        return iterations + ":" + toHex(Salt) + ":" + toHex(hash);
	}
	private static byte[] GenerateSalt() throws NoSuchAlgorithmException{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[64];
        sr.nextBytes(salt);
        return salt;
	}
	private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}