package yrj.dam;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class ControladorFichero {
	private PrintWriter fichero;
	private boolean firstThread = false;
	private Object mutex = new Object();
	
	public ControladorFichero(String nomFichero){
		try {
			fichero = new PrintWriter(new FileWriter(nomFichero));
		} catch (IOException e) {
			System.err.println("Error al crear el fichero: " + e.getMessage());
		}
	}
	
	public synchronized void println(String cadena) {
		fichero.print(cadena + "\n");
	}

	public synchronized void print(String cadena) {
		for (int i = 0; i < cadena.length(); i++) {
			fichero.print(cadena.substring(i,i+1));
		}
	}
	
	public void close(){
		if (fichero != null)
			fichero.close();
	}
}

public class PruebaFicheroSincronizado {

	public static void main(String[] args) {
		String ruta = "poema.txt";
		ControladorFichero cFichero = new ControladorFichero(ruta);
		
		String parrafo1 = "¡Ser, o no ser, esa es la cuestión! ¿Qué debe más dignamente optar...?";
		String parrafo2 = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme...";
		String parrafo3 = "Quisiera ser pirata, no por el oro ni la plata, sino por ese tesoro...";
		
		Escritor cervantes = new Escritor(cFichero);
		Escritor shakespeare = new Escritor(cFichero);
		Escritor calico = new Escritor(cFichero);
		
		shakespeare.fraseAdd(parrafo1);
		cervantes.fraseAdd(parrafo2);
		calico.fraseAdd(parrafo3);
		
		shakespeare.start();
		cervantes.start();
		calico.start();
		
		try {
			shakespeare.join();
			cervantes.join();
			calico.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cFichero.close();
		System.out.println("Los datos se han escrito en el fichero");
	}

}

class Escritor extends Thread{
	ControladorFichero cFichero;
	String contenido = "";
	
	public Escritor(ControladorFichero cFichero) {
		this.cFichero = cFichero;
	}
	
	public void fraseAdd(String parrafo){
		this.contenido += parrafo;
	}
	
	@Override
	public void run(){
		/*
		 * Ésto funciona ya que es una operación atómica
		 * this.cFichero.println(contenido);
		 */
		this.cFichero.println(contenido);
	}
}
