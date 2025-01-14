package yrj.dam;

class Inutil{
	private int a = 0;
	private int b = 0;
	private static final Object mutex = new Object();
	public void marcar5(){
		synchronized (mutex) {
			a = 5;
			System.out.println("Marcando variables");
			b = 5;
		}
	}
	
	public boolean esVerdad(){
		synchronized (mutex) {
			return a == 0 || b == 5;
		}
	}
}

class HiloA extends Thread {
	private Inutil inutil;
	public HiloA(Inutil in){
		this.inutil = in;
	}
	public void run() {
		inutil.marcar5();
	}
}

class HiloB extends Thread {
	private Inutil inutil;
	public HiloB(Inutil in){
		this.inutil = in;
	}
	public void run() {
		if (inutil.esVerdad()) {
			System.out.println("Verdadero");
		} else {
			System.out.println("Falso");
		}
	}
}

public class HilosSincronizadosClaseInutil {
	
	public static void main(String[] args){
		Inutil inutil = new Inutil();
		HiloA hiloA = new HiloA(inutil);
		HiloB hiloB = new HiloB(inutil);
		hiloA.start();
		hiloB.start();
		try {
			hiloA.join();
			hiloB.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
