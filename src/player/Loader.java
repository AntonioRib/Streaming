package player;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Loader{

	public static final int DEFAULT_SEGMENT_SIZE = 10;

	private ConcurrentLinkedDeque<Frame> frames;
	private ArrayList<String> moviesNames;
	private int segmentSize;
	private long playoutDelay;

	public Loader(String filename, long playoutDelay, int segmentSize, ConcurrentLinkedDeque<Frame> frames) {
		this.frames = frames;
		this.segmentSize = segmentSize;
		this.playoutDelay = playoutDelay;
		this.moviesNames = new ArrayList<String>();
	}

	public void load(String filename) throws IOException, InterruptedException {

		DataInputStream dis = new DataInputStream(new FileInputStream(filename));	

		while (dis.available() > 0) {
			int length = dis.readInt();
			long ts = dis.readLong();
			byte[] buf = new byte[length];
			dis.readFully(buf, 0, length);
			frames.add(new Frame(buf, ts, System.nanoTime()));
		}
		dis.close();
	}	

	public void receiveRequest() throws Exception{
		ServerSocket serverSocket = new ServerSocket(80) ;

		for(;;) { 
			Socket clientSocket = serverSocket.accept() ;
			InputStream is = clientSocket.getInputStream();

			String s = HTTPUtilities.readLine(is);
			System.out.println("Receiving: '"+s+"'");
			System.out.println(HTTPUtilities.parseHttpRequest(s)[1]);
			load(HTTPUtilities.parseHttpRequest(s)[1]);

			clientSocket.close();
		}
		//serverSocket.close();
	}
}
