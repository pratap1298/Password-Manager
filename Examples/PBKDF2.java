import java.util.*;
import java.security.*;
import java.security.spec.*;
import java.math.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

public class PBKDF2{
	public static void main(String args[]) throws NoSuchAlgorithmException,InvalidKeySpecException{
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter Password: ");
		String Password=sc.nextLine();
		String HashedPassword=GeneratePSD(Password);
		String A[]=HashedPassword.split(":");
		System.out.println("Iterations: "+A[0]);
		System.out.println("Salt: "+A[1]);
		System.out.println("Encrypted Password: "+A[2]);
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