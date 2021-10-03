import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Test4 {
	String s1 = "Realizando prueba de String, deberia funcionar en teoria";
	String s2 = "Herramienta para realizar un test, para corroborar el correcto funcionamiento del mismo";
	
	int i1 = 10;
	int i2 = 5;
	
	String[] a1 = {"Código abierto, Pruebas, Programadores"};
	String[] a2 = {"Codigo abierto, Pruebeas, Programadoress"};

	private class Message {
		
		String s1;
		String s2;
		String s3;
		String s4 = "JUnit";
		String s5 = "JUnit";
		
		int i1;
		int i2;
		
		String[] a1;
		String[] a2;
		
		public Message (String s1, String s2, int i1, int i2, String[] a1, String[] a2) {
			this.s1 = s1;
			this.s2 = s2;
			this.i1 = i1;
			this.i2 = i2;
			this.a1 = a1;
			this.a2 = a2;
		}
		
		public String printMessage() {
			String resultado ="String 1: "+s1+"\n"+
					"String 2: "+s2+"\n"+
					"Int 1: "+i1+"\n"+
					"Int 2: "+i2+"\n"+
					"Array 1: "+a1+"\n"+
					"Array 2: "+a2+"\n";
			System.out.println(resultado);
			return resultado;
		}
	}
		
	@Test
	public void setUp() {
		
		Message m1 = new Message(s1,s2,i1,i2,a1,a2);
		
		// Objetos iguales
		assertEquals(m1.s1,"Realizando prueba de String, deberia funcionar en teoria");
		assertEquals(m1.s2,"Herramienta para realizar un test, para corroborar el correcto funcionamiento del mismo");
		
		// Verdadero
		assertTrue(m1.i1>m1.i2);
		
		//Falso
		assertFalse(m1.i2>m1.i1);
		
		//Not null
		assertNotNull(m1.s2);
		
		//Null
		assertNull(m1.s3);
		
		//Sane
		assertSame(m1.s4,m1.s5);
		
		//Not same
		assertNotSame(m1.s1,m1.s5);
		
		//
		assertArrayEquals(m1.a1,a1);
	}
}
