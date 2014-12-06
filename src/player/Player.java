package player;

import java.io.DataInputStream;
import java.io.FileInputStream;

public class Player {
	
	private static long start_time;
	
	private void load() {
		
	}
	
	private void play(Viewer viewer, Frame f) throws InterruptedException {
		
		long wait = f.getTimeStamp() - (System.nanoTime() - start_time);
		
		if (wait > 0)
			Thread.sleep( Math.abs(wait) / 1000000 );
		
		viewer.updateFrame(f.getBytes(), f.getLength());
	}

	public static void main(String[] args) throws Exception {

		Viewer viewer = new Viewer(1280, 720);
		DataInputStream dis = new DataInputStream(new FileInputStream("data/Lifted-small.dat"));
		byte[] buf = new byte[128 * 1024];
		start_time = System.nanoTime();

		while (dis.available() > 0) {
			
			int length = dis.readInt();
			long ts = dis.readLong();
			dis.readFully(buf, 0, length);
			Frame f = new Frame(buf, ts);
			
			play(viewer, f);
		}
		dis.close();
	}
	
}
