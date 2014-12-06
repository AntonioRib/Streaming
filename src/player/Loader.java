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

public class Loader implements Runnable {
	
	public static final int SEGMENT_SIZE = 10;
	
	private ConcurrentLinkedDeque<Frame> frames;
	private String filename;
	private ArrayList<String> moviesNames;

	public Loader(String filename, ConcurrentLinkedDeque<Frame> frames) {
		this.frames = frames;
		this.filename = filename;
		this.moviesNames = new ArrayList<String>();
	}

	public void load(String filename, int offset) throws IOException, InterruptedException {
		
		DataInputStream dis = new DataInputStream(new FileInputStream("data\\" + filename));		
		int segmentNumber = 0;
		while (dis.available() > 0 && segmentNumber < SEGMENT_SIZE) {
			int length = dis.readInt();
			long ts = dis.readLong();
			byte[] buf = new byte[length];
			dis.readFully(buf, 0, length);
			frames.add(new Frame(buf, ts, System.nanoTime()));
			segmentNumber++;
		}
		
		dis.close();
	}

	public void loadFile() throws NumberFormatException, IOException, InterruptedException{
		Scanner s = new Scanner(new BufferedReader(new FileReader(filename)));
			String a;
			while (s.hasNextLine()) {
				a = s.nextLine();
				System.out.println(a);
				moviesNames.add(a);
				if(a.equalsIgnoreCase(""))
					break;
			}
			int startLine = moviesNames.size()+2;
			int currentLine = moviesNames.size()+2 ;
			while(s.hasNextLine()){
				System.out.println("Filme");
				a = s.nextLine();
				if(((currentLine-startLine)%moviesNames.size() == 0)){
					String splits[] = a.split("([ ])+");
					load(splits[0], Integer.parseInt(splits[2]));
				}
				currentLine++;
			}
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	
}
