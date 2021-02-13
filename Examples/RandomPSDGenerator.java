import java.util.*;
import java.lang.Math;
import java.util.regex.*;

public class RandomPSDGenerator{
	public static void main (String args[]){
		String Password="";
		for(int i=0;i<1000;i++){
			do{
				Password=GenerateRandom();
			}while(!CheckStrength(Password));
			System.out.println(Password);
		}
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
}