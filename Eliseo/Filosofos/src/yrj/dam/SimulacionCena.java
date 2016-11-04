package yrj.dam;

import java.util.Random;

class Palillo {
	int numero;
	boolean enUso;
	public Palillo(int x) {
		numero = x;
		enUso = false;
	}
	public void coger(){
		enUso = true;
	}
	public void soltar(){
		enUso = false;
	}
}
class Cena{
	Palillo palillos[];
	int comensales = 0;
	public Cena(int invitados) {
		this.comensales = invitados;
	}

	public int cogerPalilloDer(int x){
		return 0;
	}

	public int cogerPalilloIzq(int x){
		return 0;
	}

	public int soltarPalilloDer(int x){
		return 0;
	}

	public int soltarPalilloIzq(int x){
		return 0;
	}
}

class Filosofo extends Thread{
	private Cena cena;
	private int pizq, pder;
	private int numero; //Posición en la mesa
	private int veces; //Veces que va a cenar
	public Filosofo(int num, int almuerzos) {
		this.numero = num;
		this.veces = almuerzos;
		
	}
	
	public void	pensar() {
		//Tiempo aleatorio entre 1 y 1000 ms
		int minimum = 1,
				maximum = 1000;
		System.out.println("Filósofo "+numero+" está pensando");
		randomSleep(minimum, maximum);
		//System.out.println("Filósofo "+numero+" ha terminado de pensar");
	}

	public void	cogerPalillos() {
		cena.cogerPalilloDer(pder);
		System.out.println("Filósofo "+numero+" ha cogido el palillo derecho");	
		cena.cogerPalilloIzq(pizq);
		System.out.println("Filósofo "+numero+" ha soltado el palillo izquierdo");	
		
	}
	public void	comer() {
		//Tiempo aleatorio entre 1 y 2000 ms
		int minimum = 1,
				maximum = 2000;
		System.out.println("Filósofo "+numero+" está comiendo");
		randomSleep(minimum, maximum);
		//System.out.println("Filósofo "+numero+" ha terminado de comer");
	}
	public void soltarPalillos(){
		cena.soltarPalilloIzq(pizq);
		System.out.println("Filósofo "+numero+" ha soltado el palillo izquierdo");	
		cena.soltarPalilloDer(pder);
		System.out.println("Filósofo "+numero+" ha soltado el palillo derecho");	
	}
	
	@Override
	public void run(){
		for (int i = 0; i < veces; i++) {
			pensar();
			cogerPalillos();
			comer();
			soltarPalillos();
		}
	}

	private void randomSleep(int minimum, int maximum) {
		int randomNum = minimum + (int)(Math.random() * maximum); 
		try {
			Thread.sleep(randomNum);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class SimulacionCena {

	public static void main(String[] args) {
		int comensales = Integer.parseInt(args[0]);
		int almuerzos = Integer.parseInt(args[1]); //Cantidad de veces que van a comer
		
		Cena cena = new Cena(comensales);
		for (int i = 0; i < comensales; i++) {
			new Filosofo(i, almuerzos);
		}
	}

}
