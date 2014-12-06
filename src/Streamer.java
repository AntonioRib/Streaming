import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

import player.*;


public class Streamer {

	public static void main(String[] args) {
		final ConcurrentLinkedDeque<Frame> frames = new ConcurrentLinkedDeque<Frame>();

		new Thread(){
			public void run() {
				new Player(new Viewer(1280, 720), frames, 5).run();
			}
		}.start();
		
		try {
			new Loader("data/Lifted-index.dat", frames).loadFile();
		} catch (NumberFormatException e) {
			System.out.println("Ficheiro dat mal formatado amigo.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Janela fechada o programa vai terminar.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
