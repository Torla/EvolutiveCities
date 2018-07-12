import world.World;
import city.Action;
import evolutiveAutomaton.EvolutiveAutomaton;
import genetic.Pool;
import graphics.Graphics;

import java.io.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {

	private static final int threadNum = 4;
	private static Thread[] threadPool = new Thread[threadNum];

	static Pool<EvolutiveAutomaton> pool = new Pool<>();
	static final Lock poolLock = new ReentrantLock();


	static private void save(){
		FileOutputStream fileOut=null;
		ObjectOutputStream out;

		try {
			fileOut = new FileOutputStream("pool");
		}catch (FileNotFoundException e){
			e.printStackTrace();
		};

		try {
			out = new ObjectOutputStream(fileOut);
			out.writeObject(pool);
			out.close();
			fileOut.close();
		}catch (IOException e){
			e.printStackTrace();
		}

		try {
			fileOut = new FileOutputStream("poolbackup");
		}catch (FileNotFoundException e){
			e.printStackTrace();
		};

		try {
			out = new ObjectOutputStream(fileOut);
			out.writeObject(pool);
			out.close();
			fileOut.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	static private void load() {
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		try {
			fileIn = new FileInputStream("pool");
			in = new ObjectInputStream(fileIn);
			pool = (Pool<EvolutiveAutomaton>) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			try {
				e.printStackTrace();
				fileIn = new FileInputStream("poolbackup");
				in = new ObjectInputStream(fileIn);
				pool = (Pool<EvolutiveAutomaton>) in.readObject();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		pool.resetUsed();
	}
	public static void main(String[] args) throws InterruptedException, IOException {
		World world=new World();
		Graphics.setWorld(world);
		Graphics.start();

		for (int i = 0; i < 1000; i++) pool.add(new EvolutiveAutomaton(Action.values()));

		load();


		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(240000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					poolLock.lock();
					try {
						System.out.println("Saving...");
						save();
						System.out.println("Done");
					}
					finally {
						poolLock.unlock();
					}
				}
			}
		}).start(); //save routine

		threadPool[0]=new Thread(new Cicle(world));
		for(int i=1;i<threadNum;i++){
			threadPool[i]=new Thread(new Cicle(new World()));
		}
		for(int i=0;i<threadNum;i++){
			threadPool[i].start();
		}
		return;
	}



}
