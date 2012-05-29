package br.ueg.unucet.quid.utilitarias;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe responvael por serializar e deserializar um objeto.
 * @author QUID
 *
 */
public class SerializadorObjetoUtil {

	/**
	 * Metodo que realiza a serializacao de um objeto em um vetor de bytes.
	 * @param obj Objeto que sera serializado.
	 * @return Vetor de bytes advindo da serializacao do objeto.
	 */
	public static byte[] toByteArray (Object obj){
	  byte[] bytes = null;
	  ByteArrayOutputStream bos = new ByteArrayOutputStream();
	  try {
	    ObjectOutputStream oos = new ObjectOutputStream(bos); 
	    oos.writeObject(obj);
	    oos.flush(); 
	    oos.close(); 
	    bos.close();
	    bytes = bos.toByteArray ();
	  }
	  catch (IOException ex) {
		  ex.printStackTrace();
	  }
	  return bytes;
	}
	    
	/**
	 * Metodo responsavel por deserializar um vetor de bytes em um objeto.
	 * @param bytes Vetor de bytes que sera deserializado.
	 * @return Objeto advindo da deserializacao.
	 */
	public static Object toObject (byte[] bytes){
	  Object obj = null;
	  try {
	    ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
	    ObjectInputStream ois = new ObjectInputStream (bis);
	    obj = ois.readObject();
	  }
	  catch (IOException ex) {
	    //TODO: Handle the exception
	  }
	  catch (ClassNotFoundException ex) {
	    //TODO: Handle the exception
	  }
	  return obj;
	}
}
