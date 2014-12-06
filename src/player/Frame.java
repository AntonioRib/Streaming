package player;

public class Frame {
	
	private byte[] buf;
	private long ts;
	private long rc;
	

	public Frame(byte[] buf, long ts, long rc) {
		this.buf = buf;
		this.ts = ts;
		this.rc = rc;
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

	public long getReceivedTime() {
		return rc;
	}
	
}
