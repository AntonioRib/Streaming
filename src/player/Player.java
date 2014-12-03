package player;

import java.io.DataInputStream;
import java.io.FileInputStream;

public class Player {

	public static void main(String[] args) throws Exception {

		Viewer viewer = new Viewer(1280, 720);
		DataInputStream dis = new DataInputStream(new FileInputStream("Lifted-480p.dat"));

		byte[] buf = new byte[128 * 1024];
		while (dis.available() > 0) {
			int length = dis.readInt();
			long ts = dis.readLong();
			dis.readFully(buf, 0, length);
			Thread.sleep(0);
			viewer.updateFrame(buf, length);
		}

		dis.close();
	}
}
