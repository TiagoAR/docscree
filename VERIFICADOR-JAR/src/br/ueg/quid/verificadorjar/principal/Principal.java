package br.ueg.quid.verificadorjar.principal;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class Principal {

	public static void main(String [] args) {
		String jar = args[0];
		System.out.println("JAR A VERIFICAR: " + jar);
		verificarJar(jar);
	}

	private static void verificarJar(String jar) {
		LeitoraJarUtil leitor = new LeitoraJarUtil(new URL[] {});
		boolean erro = true;
		try {
			Collection<Class<?>> classes = leitor.listarClassesJar(jar);
			erro = false;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (erro) {
			System.exit(1);
		} else {
			System.out.println("JAR CORRETO");
			System.exit(0);
		}
	}
}
