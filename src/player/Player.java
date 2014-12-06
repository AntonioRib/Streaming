package player;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Player implements Runnable {

	private Viewer viewer;
	private ConcurrentLinkedDeque<Frame> frames;

	public Player(Viewer viewer, ConcurrentLinkedDeque<Frame> frames, long playoutDelay) {
		this.viewer = viewer;
		this.frames = frames;
	}

	public void play() throws InterruptedException {
		long startTime = System.nanoTime();
		Thread.sleep(1);
		while (true) {
			if (!frames.isEmpty()) {
				Frame f = frames.pollFirst();

				long wait = f.getTimeStamp() - (System.nanoTime() - startTime);
				if (wait > 0)
					Thread.sleep( wait / 1000000 );
				
				viewer.updateFrame(f.getBytes(), f.getLength());
			}
		}
	}
	
	public void run() {
		try {
			play();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
