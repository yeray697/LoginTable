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
	
	public void println(String cadena) {
		fichero.println(cadena);
		synchronized (mutex){
			mutex.notifyAll();
		}
	}

	public void print(String cadena) {
		
		fichero.print(cadena);
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
		
		Escritor cervantes = new Escritor(cFichero);
		Escritor shakespeare = new Escritor(cFichero);
		
		shakespeare.fraseAdd(parrafo1);
		cervantes.fraseAdd(parrafo2);
		
		shakespeare.start();
		cervantes.start();
		
		try {
			shakespeare.join();
			cervantes.join();
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
		for (int i = 0; i < contenido.length(); i++) {
			cFichero.print(contenido.substring(i,i+1));
		}
		cFichero.println("");
	}
}
