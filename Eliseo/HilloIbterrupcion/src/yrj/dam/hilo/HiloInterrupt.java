package yrj.dam.hilo;

public class HiloInterrupt implements Runnable{

	private Thread miHilo;
	private volatile boolean vivo; //Volatile = garantiza lectura no cacheada
	
	public HiloInterrupt() {
		miHilo = new Thread(this,"hilo hijo");
		this.vivo = true;
		miHilo.start();
	}
	
	@Override
	public void run() {
		System.out.println("Corriendo el hilo ahora...");
		while(this.vivo){
			System.out.println("El hijo va a dormir un rato...");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("El hilo hijo ha sido interrumpido...");
			}
		}
		System.out.println("Hilo finalizado y saliendo de forma ordenada");
	}
	
	public void detener() {
		this.vivo = false;
	}
	
	public void interrumpir() {
		if (this.miHilo != null) {
			this.miHilo.interrupt();
		}
	}
	
	public boolean vive() {
		//return this.miHilo.isAlive(); La variable volátil es más fiable
		return this.vivo;
	}
	
	public void esperar() throws InterruptedException {
		this.miHilo.join();
		
	}
}
