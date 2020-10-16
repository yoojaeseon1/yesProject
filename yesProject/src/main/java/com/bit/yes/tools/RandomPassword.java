package com.bit.yes.tools;

import org.springframework.stereotype.Component;

@Component
public class RandomPassword {
	
	private char[] characters = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
			'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z' };

	private int passwordLength = 10;
	
	public String setPassword() {
	
		StringBuilder password = new StringBuilder();
		
		for (int passwordI = 0; passwordI < passwordLength; passwordI++) {
			int charactersI = (int) (characters.length * Math.random());
			password.append(characters[charactersI]);
		}
		
		return password.toString();
	}

}
