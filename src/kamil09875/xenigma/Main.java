package kamil09875.xenigma;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

public class Main{
	public static void main(final String[] args){
		System.out.println("XEnigma application");
		System.out.println("Copyright (c)2013-2015 Kamil Jarosz");
		System.out.println();
		
		if(args.length == 0 || args[0].equals("-h")){
			System.out.println("Usage:");
			System.out.println("    -h           -- print this help message");
			System.out.println("    -s <seed>    -- seed (default random)");
			System.out.println("    -r <num>     -- number of rotors by default");
			System.out.println("                    it's ln(<length of message>)/ln(256)");
			System.out.println("    -mlt         -- allow multirotation: if set,");
			System.out.println("                    one byte change will affect each subsequent");
			System.out.println("                    (disabled by default)");
			System.out.println("    -m <message> -- the message");
			System.out.println("    -d           -- decrypt");
			System.out.println("    -h           -- hexadecimal input");
			System.out.println();
			System.out.println("Sample usage:");
			System.out.println("    xenigma -r 4 -s 3476235876 -m \"the message to encrypt\"");
			return;
		}
		
		Random rnd = new Random();
		
		Long seed = null;
		Integer rotors = null;
		boolean mult = false;
		String message = null;
		boolean decrypt = false;
		boolean hex = false;
		byte[] input = null;
		
		for(int i = 0; i < args.length; ++i){
			switch(args[i]){
				case "-s":
					seed = Long.parseLong(args[++i]);
					break;
					
				case "-r":
					rotors = Integer.parseInt(args[++i]);
					break;

				case "-mlt":
					mult = true;
					break;
					
				case "-m":
					message = args[++i];
					break;
					
				case "-d":
					decrypt = true;
					break;
					
				case "-h":
					hex = true;
					break;
					
				default:
					System.err.println("Unknown argument: " + args[i]);
					System.err.println("Type -h for help");
					return;
			}
		}
		
		if(message == null){
			System.err.println("No message specified");
			System.err.println("Type -h for help");
			return;
		}
		
		if(hex){
			input = DatatypeConverter.parseHexBinary(message);
		}else{
			input = message.getBytes();
		}
		
		if(seed == null){ seed = rnd.nextLong(); }
		if(rotors == null){ rotors = XEnigmaStatus.getRotorsFor(input.length); }
		
		XEnigmaStatus status = new XEnigmaStatus(seed, rotors, mult);
		XEnigma enigma = new XEnigma(status);
		
		byte[] output;
		if(decrypt){
			output = enigma.decrypt(input);
		}else{
			output = enigma.encrypt(input);
		}

		System.out.println("Done.");
		System.out.println("ASCII output: " + new String(output, StandardCharsets.US_ASCII));
		System.out.println("UTF-8 output: " + new String(output, StandardCharsets.UTF_8));
		System.out.println("Hexadecimal output: " + new BigInteger(1, output).toString(16));
		System.out.println("Seed: " + seed);
		System.out.println("Rotors: " + rotors);
		System.out.println("Multirotation: " + mult);
		System.out.println(":)");
	}
}
