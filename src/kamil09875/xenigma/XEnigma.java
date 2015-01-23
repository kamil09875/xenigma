package kamil09875.xenigma;

public class XEnigma{
	private final XRotor main;
	private final boolean multirotation;
	
	public XEnigma(final XEnigmaStatus status){
		main = status.getRotor();
		multirotation = status.getMultirotation();
	}
	
	public byte[] encrypt(final byte[] bytes){
		return encrypt(bytes, true);
	}
	
	public byte[] encrypt(final byte[] bytes, final boolean reset){
		int byteslen = bytes.length;
		byte[] ret = new byte[bytes.length];
		
		for(int i = 0; i < byteslen; ++i){
			ret[i] = main.encrypt(bytes[i]);
			main.rotate(multirotation ? bytes[i] & 0xFF : 1);
		}
		
		if(reset){
			reset();
		}
		
		return ret;
	}
	
	public byte[] decrypt(final byte[] bytes){
		return decrypt(bytes, true);
	}
	
	public byte[] decrypt(final byte[] bytes, final boolean reset){
		int byteslen = bytes.length;
		byte[] ret = new byte[bytes.length];
		
		for(int i = 0; i < byteslen; ++i){
			ret[i] = main.decrypt(bytes[i]);
			main.rotate(multirotation ? ret[i] & 0xFF : 1);
		}
		
		if(reset){
			reset();
		}
		
		return ret;
	}
	
	public void reset(){
		main.reset();
	}
}
