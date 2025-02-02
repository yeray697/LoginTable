package yrj.dam.hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PruebaHilo {

	public static void main(String[] args) throws InterruptedException {
		HiloInterrupt hilo = new HiloInterrupt();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String tecla;
		
		while (hilo.vive()) {
			System.out.println("Hilo vivo, [i] interrumpir, [k] matar ");
			try {
				tecla = br.readLine();
				if (tecla.equals("i")) {
					hilo.interrumpir();
				}else if (tecla.equals("k")) {
					hilo.detener();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		hilo.esperar();
		System.out.println("Hilo finalizado");
	}

}
