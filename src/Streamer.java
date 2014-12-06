import java.util.concurrent.ConcurrentLinkedDeque;

import player.Frame;
import player.Loader;
import player.Player;
import player.Viewer;


public class Streamer {

	public static void main(String[] args) throws Exception {
		final ConcurrentLinkedDeque<Frame> frames = new ConcurrentLinkedDeque<Frame>();

		new Thread(){
			public void run() {
				try {
					new Loader("Lifted-160p.dat", 10, 5, frames).receiveRequest();
				} catch (Exception e) { e.printStackTrace();}
			}
		}.start();

			new Player(new Viewer(1280, 720), frames, "Lifted-160p.dat").run();
	}

}
