package kamil09875.xenigma;

public class XRotor{
	static final int LENGTH = 256;
	
	private final XRotor parent;
	private final byte[] translation;
	private int rotation = 0;
	
	public XRotor(final XRotor parent, final byte[] translation){
		if(translation.length != LENGTH){
			throw new IllegalArgumentException("Expected translation to be " + LENGTH + " long");
		}
		
		this.parent = parent;
		this.translation = translation;
	}
	
	public byte encrypt(byte b){
		if(parent != null){
			b = parent.encrypt(b);
		}
		
		return translation[((b & 0xFF) + rotation) % LENGTH];
	}
	
	public byte decrypt(final byte translated){
		byte[] translation = this.translation;
		int rotation = this.rotation;
		XRotor parent = this.parent;
		byte b = 0;
		
		for(int i = 0; i < LENGTH; ++i){
			if(translation[i] == translated){
				b = (byte)((i - rotation) % LENGTH);
			}
		}
		
		if(parent != null){
			b = parent.decrypt(b);
		}
		
		return b;
	}
	
	public void rotate(){
		rotate(1);
	}
	
	public void rotate(final int times){
		assert times >= 0;
		
		rotation += times;
		
		if(parent != null){
			parent.rotate(rotation / LENGTH);
		}
		
		rotation %= LENGTH;
	}
	
	public void reset(){
		rotation = 0;
		
		if(parent != null){
			parent.reset();
		}
	}
}
