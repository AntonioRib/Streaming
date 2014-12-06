package player;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Player implements Runnable {

	private Viewer viewer;
	private ConcurrentLinkedDeque<Frame> frames;
	private String fileName;

	public Player(Viewer viewer, ConcurrentLinkedDeque<Frame> frames, String fileName) {
		this.viewer = viewer;
		this.frames = frames;
		this.fileName = fileName;
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
	
	public void sendRequest() throws Exception{
		String ask = "GET data/"+fileName+" HTTP/1.0";
		
		InetAddress server = InetAddress.getByName("localhost");

		// Cria uma conexao para o servidor
		Socket socket = new Socket(server, 80);
		// Obtem o canal de escrita associado ao socket.
		OutputStream os = socket.getOutputStream();

		os.write(ask.getBytes()); // envia nome do ficheiro

		socket.close();
	}
	
	public void run() {
		try {
			sendRequest();
			play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
