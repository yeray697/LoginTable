package yrj.dam;

class Saludo{
	boolean claseComenzada;
	
	public Saludo(){
		this.claseComenzada = false;
	}
	
	public synchronized void saludarAlProfesor() {
		while (!claseComenzada) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}
	public synchronized void llegadaDelProfesor() {
		this.claseComenzada = true;
		this.notifyAll();
	}
}

class Alumno extends Thread{
	Saludo elSaludo;
	String elNombre;

	public Alumno(String elNombre, Saludo s) {
		this.elSaludo = s;
		this.elNombre = elNombre;
	}
	
	@Override
	public void run(){
		System.out.println("Ha llegado el alumno " + this.elNombre);
		try {
			Thread.sleep(1000);
			elSaludo.saludarAlProfesor();
			System.out.println("("+this.elNombre+") Buenos días, profe!");
		} catch (InterruptedException e) {
			System.err.println("Hilo alumno interrumpido");
		}
	}
}

class Profesor extends Thread{
	Saludo elSaludo;
	String elNombre;
	
	public Profesor(String elNombre, Saludo elSaludo) {
		this.elNombre = elNombre;
		this.elSaludo = elSaludo;
	}
	
	@Override
	public void run(){
		try {
			System.out.println("Ha llegado el profesor "+this.elNombre);
			Thread.sleep(2000);
			elSaludo.llegadaDelProfesor();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

public class ComienzaClase {
	public static void main(String[] args) {
		Saludo saludo = new Saludo();
		int nAlumnos = Integer.parseInt(args[0]);
		for (int i = 0; i < nAlumnos; i++) {
			new Alumno("Alum"+i, saludo).start();			
		}
		Profesor profesor = new Profesor("Eliseo Moreno",  saludo);
		profesor.start();
	}
}
