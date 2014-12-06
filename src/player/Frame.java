package player;

public class Frame {
	
	private byte[] buf;
	private long ts;
	
	public Frame(byte[] buf, long ts) {
		this.buf = buf;
		this.ts = ts;
	}
	
	public long getTimeStamp() {
		return ts;
	}
	
	public byte[] getBytes() {
		return buf;
	}
	
	public int getLength() {
		return buf.length;
	}

}
