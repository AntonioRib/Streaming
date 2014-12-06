package player;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Loader{
	
	public static final int DEFAULT_SEGMENT_SIZE = 10;
	
	private ConcurrentLinkedDeque<Frame> frames;
	private String filename;
	private ArrayList<String> moviesNames;
	private int segmentSize;
	private long playoutDelay;

	public Loader(String filename, long playoutDelay, int segmentSize, ConcurrentLinkedDeque<Frame> frames) {
		this.frames = frames;
		this.filename = filename;
		this.segmentSize = segmentSize;
		this.playoutDelay = playoutDelay;
		this.moviesNames = new ArrayList<String>();
	}

	public void load() throws IOException, InterruptedException {
		
		DataInputStream dis = new DataInputStream(new FileInputStream("data\\" + filename));	
		
		while (dis.available() > 0) {
			int length = dis.readInt();
			long ts = dis.readLong();
			byte[] buf = new byte[length];
			dis.readFully(buf, 0, length);
			frames.add(new Frame(buf, ts, System.nanoTime()));
		}
		dis.close();
	}	
}
