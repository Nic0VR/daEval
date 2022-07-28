package fr.dawan.evalnico.tools;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashTools {

	//chaine =hachage=> chaine non déchiffrable
	public static String hashSHA512(String input) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-512");//singleton unique
		md.reset(); //réinitialiser le contenu du msg digest
		
		//application de l'algorithme choisi
		byte[] hachedArray = md.digest(input.getBytes("utf-8"));
		
		//conversion du message haché (tab de bytes) en une représentation numérique signée
		BigInteger bi = new BigInteger(1, hachedArray);
		return String.format("%0128x", bi);	
	}
	
}
