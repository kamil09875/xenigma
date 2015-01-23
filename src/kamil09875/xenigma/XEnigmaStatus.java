package kamil09875.xenigma;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class XEnigmaStatus{
	private final Random random;
	private final int rotors;
	private final boolean multirotation;
	
	/**
	 * Constructs a new status with given seed and rotors.
	 * 
	 * @param seed seed
	 * @param rotors number of rotors
	 * @param multirotation if set, one byte change will affect
	 *                      each subsequent
	 */
	public XEnigmaStatus(final long seed, final int rotors, final boolean multirotation){
		this(new Random(seed), rotors, multirotation);
	}
	
	/**
	 * Constructs a new status with given random generator and rotors.
	 * 
	 * @param random random generator
	 * @param rotors number of rotors
	 * @param multirotation if set, one byte change will affect
	 *                      each subsequent
	 */
	public XEnigmaStatus(final Random random, final int rotors, final boolean multirotation){
		if(rotors <= 0){
			throw new IllegalArgumentException("rotors num <= 0: " + rotors);
		}
		
		if(random == null){
			throw new NullPointerException("random = null");
		}
		
		this.random = random;
		this.rotors = rotors;
		this.multirotation = multirotation;
	}
	
	boolean getMultirotation(){
		return multirotation;
	}
	
	private XRotor rotor;
	XRotor getRotor(){
		if(rotor != null){
			return rotor;
		}
		
		int rotors = this.rotors;
		for(int i = 0; i < rotors; ++i){
			Set<Integer> generated = new HashSet<>();
			generated.add(-1);
			byte[] trans = new byte[XRotor.LENGTH];
			
			for(int j = 0; j < XRotor.LENGTH; ++j){
				int random = -1;
				while(generated.contains(random)){
					random = this.random.nextInt(XRotor.LENGTH);
				}
				
				generated.add(random);
				trans[j] = (byte)random;
			}
			
			rotor = new XRotor(rotor, trans);
		}
		
		return rotor;
	}
	
	// ==============================================
	
	/**
	 * Computes number of rotors needed to make the best
	 * cipher for string with specified maximum length.
	 * 
	 * @param len maximum length of the string to encrypt
	 * @return
	 */
	public static int getRotorsFor(final int len){
		return (int)(Math.log(len) / 5.545177444479562) + 1;
		//                           ^ log(256)
	}
	
	public static XEnigmaStatus random(){
		Random rand;
		return new XEnigmaStatus(rand = new Random(), rand.nextInt(20), rand.nextBoolean());
	}
}
