import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import com.sun.jdi.InternalException;

public class MaquinaDeGolosinas {

	public MaquinaDeGolosinas() {
		// TODO Auto-generated constructor stub
		try {
			FileReader fr=new FileReader("golosinas.txt");
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine();
			int numGolosinas=Integer.parseInt(line);
			double ventas[][]=new double[numGolosinas][numGolosinas];
			for (int x=0;x<numGolosinas;x++) {
				Arrays.fill(ventas[x], 0.0);
			}
			String golosinas[][]=leeGolosinas(numGolosinas,br);
			double precioGolosinas[][]=leePrecios(numGolosinas,br);
			int cantidadGolosinas[][]=leeCantidad(numGolosinas,br);
			br.close();fr.close();
			maquinaDeGolosinas(golosinas,precioGolosinas,cantidadGolosinas,ventas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void maquinaDeGolosinas(String[][] golosinas, double[][] precioGolosinas, int[][] cantidadGolosinas,double [][]ventas) {
		// TODO Auto-generated method stub
		boolean salirMenu=false;
		while (salirMenu==false) {
			Scanner sc=new Scanner(System.in);
			System.out.printf("%s %n","┌────────────────────────────────────────┐");
			System.out.printf("%-10s %s %10s %n","│","MÁQUINA DE GOLOSINAS","│");
			System.out.printf("%-10s %31s %n","│","│");
			System.out.printf("%-10s %31s %n","│","│");
			System.out.printf("%-10s %31s %n","│","│");
			System.out.printf("%-10s %31s %n","│","│");
			System.out.printf("%s %s %14s %n","│","┌───────────────────────┐","│");
			System.out.printf("%s %s %s %5s %14s %n","│","│","1.Pedir golosina.","│","│");
			System.out.printf("%s %s %14s %n","│","└───────────────────────┘","│");
			System.out.printf("%s %s %14s %n","│","┌───────────────────────┐","│");
			System.out.printf("%s %s %s %14s %n","│","│","2.Mostrar golosinas.  │","│");
			System.out.printf("%s %s %14s %n","│","└───────────────────────┘","│");
			System.out.printf("%s %s %14s %n","│","┌───────────────────────┐","│");
			System.out.printf("%s %s %s %14s %n","│","│","3.Rellenar golosinas. │","│");
			System.out.printf("%s %s %14s %n","│","└───────────────────────┘","│");
			System.out.printf("%s %s %14s %n","│","┌───────────────────────┐","│");
			System.out.printf("%s %s %s %14s %n","│","│","4.Apagar máquina.     │","│");
			System.out.printf("%s %s %14s %n","│","└───────────────────────┘","│");
			System.out.printf("%-10s %31s %n","│","│");
			System.out.printf("%s %n","└────────────────────────────────────────┘");
			System.out.print(" → ");
			int opcionMaquina=sc.nextInt();
			switch (opcionMaquina) {
				case 1:pideGolosina(cantidadGolosinas,golosinas,precioGolosinas,sc,ventas);break;
				case 2:muestraGolosina(golosinas,precioGolosinas,cantidadGolosinas);break;
				case 3:rellenaGolosinas(cantidadGolosinas,golosinas,sc);break;
				case 4:apagaMaquinaYGuardaCambios(golosinas,cantidadGolosinas,precioGolosinas,ventas);salirMenu=true;break;
				default:System.err.println("Introduzca un opción correcta.");break;
			}
		}
	}
	
	private void apagaMaquinaYGuardaCambios(String golosinas[][],int cantidadGolosinas[][],double precioGolosinas[][],double [][]ventas) {
		double ventasTotales=0.0;
		FileWriter fw;
		try {
			fw = new FileWriter("golosinasGuardado.txt");
			BufferedWriter bw=new BufferedWriter(fw);
			int numGolosinas=golosinas.length;
			String line=""+numGolosinas;
			bw.write(line);
			bw.newLine();
			for (int x=0;x<golosinas.length;x++) {
				line="";
				for (int y=0;y<golosinas[x].length;y++) {
					if (y==golosinas[x].length-1)
						line+=golosinas[x][y];
					else line+=golosinas[x][y]+",";
				}
				bw.write(line);
				bw.newLine();
			}
			for (int x=0;x<precioGolosinas.length;x++) {
				line="";
				for (int y=0;y<precioGolosinas[x].length;y++) {
					if (y==precioGolosinas[x].length-1)
						line+=precioGolosinas[x][y];
					else line+=precioGolosinas[x][y]+",";
				}
				bw.write(line);
				bw.newLine();
			}
			for (int x=0;x<cantidadGolosinas.length;x++) {
				line="";
				for (int y=0;y<cantidadGolosinas[x].length;y++) {
					if (y==cantidadGolosinas[x].length-1)
						line+=cantidadGolosinas[x][y];
					else line+=cantidadGolosinas[x][y]+",";
				}
				bw.write(line);
				bw.newLine();
			}
			for (int x=0;x<ventas.length;x++) {
				line="";
				for (int y=0;y<ventas[x].length;y++) {
					if (y==cantidadGolosinas[x].length-1) {
						line+=ventas[x][y];
					} else {
						line+=ventas[x][y]+",";
					}
					ventasTotales+=ventas[x][y];
					System.out.println("Se han vendido "+ventas[x][y]+" € de "+golosinas[x][y]);
				}
				bw.write(line);
				bw.newLine();
			}
			line=""+ventasTotales;
			bw.write(line);
			bw.close();fw.close();
			System.out.println("Ventas totales de hoy: "+ventasTotales+" €");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void rellenaGolosinas(int[][] cantidadGolosinas,String golosinas[][],Scanner sc) {
		System.out.println("Introduzca la contraseña: ");
		System.out.print(" → ");
		String contraseñaMaestra="MaquinaExpendedora2017";
		String contraseña=sc.next();
		int codigoGolosina=0,cantidadARellenar=0;
		try {
			if (contraseña.equals(contraseñaMaestra)) {
				System.out.println("Introduzca el código de la golosina a rellenar: ");
				System.out.print(" → ");
				codigoGolosina=sc.nextInt();
				int fila=codigoGolosina/10,col=codigoGolosina%10;
				System.out.println("Ha seleccionado: "+"'"+golosinas[fila][col]+"'");
				System.out.println();
				System.out.println("Introduzca la cantidad que va a introducir: ");
				System.out.print(" → ");
				cantidadARellenar=sc.nextInt();
				while (cantidadARellenar<1) {
					System.out.println("Cantidad no válida, por favor, vuelva a introducir la cantidad deseada: ");
					System.out.print(" → ");
					cantidadARellenar=sc.nextInt();
				}
				cantidadGolosinas[fila][col]+=cantidadARellenar;
			} else System.out.println("Contraseña incorrecta.");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Código erroneo.");
			System.out.println();
		}
	}
	
	private void muestraGolosina(String[][] golosinas, double[][] precioGolosinas,int[][] cantidadGolosinas) {
		// TODO Auto-generated method stub
		System.out.printf("%-24s %-6s %s %n","  Golosina","Cod.","PVP.");
		System.out.println();
		for (int x=0;x<golosinas.length;x++) {
			for (int y=0;y<golosinas[x].length;y++) {
				if (cantidadGolosinas[x][y]>0)
				System.out.printf("%-25s %-4s %4.2f %s %n","►'"+golosinas[x][y]+"'",x+""+y,precioGolosinas[x][y],"€");
			}
		}
	}

	private void pideGolosina(int[][] cantidadGolosinas, String[][] golosinas, double[][] precioGolosinas, Scanner sc,double[][]ventas) {
		// TODO Auto-generated method stub
		boolean datoCorrecto=false;
		int fil=0,col=0;
		try {
			System.out.println("Introduzca el código de la golosina: ");
			System.out.print(" → ");
			String codGolosinaStr=sc.next();
			int codGolosina=Integer.parseInt(codGolosinaStr);
			fil=codGolosina/10;col=codGolosina%10;
			if (cantidadGolosinas[fil][col]>0) {
				String golosinaComprada=golosinas[fil][col];
				System.out.println("Aqui tiene su golosina: ");
				System.out.println("*Recibe "+"'"+golosinaComprada+"'");
				System.out.println();
				ventas[fil][col]+=precioGolosinas[fil][col];
				cantidadGolosinas[fil][col]--;
				datoCorrecto=true;
			} else System.err.println("No quedan más golosinas de este tipo");
				datoCorrecto=true;
		} catch (Exception e) {
			System.err.println("Código erroneo.");
			System.out.println();
		}
		sc=null;
	}

	private int[][] leeCantidad(int numGolosinas, BufferedReader br) throws IOException {
		// TODO Auto-generated method stub
		int cantidadGolosinas[][]=new int[numGolosinas][numGolosinas];
		String line="";
		for (int x=0;x<cantidadGolosinas.length;x++) {
			line=br.readLine();
			String lineSplit[]=line.split(",");
			for (int y=0;y<cantidadGolosinas[x].length;y++) {
				cantidadGolosinas[x][y]=Integer.parseInt(lineSplit[y]);
			}
		}
		return cantidadGolosinas;
	}

	private double[][] leePrecios(int numGolosinas, BufferedReader br) throws IOException {
		// TODO Auto-generated method stub
		double precioGolosinas[][]=new double[numGolosinas][numGolosinas];
		String line="";
		for (int x=0;x<precioGolosinas.length;x++) {
			line=br.readLine();
			String lineSplit[]=line.split(",");
			for (int y=0;y<precioGolosinas[x].length;y++) {
				precioGolosinas[x][y]=Double.parseDouble(lineSplit[y]);
			}
		}
		return precioGolosinas;
	}

	private String[][] leeGolosinas(int numGolosinas, BufferedReader br) throws IOException {
		// TODO Auto-generated method stub
		String golosinas[][]=new String[numGolosinas][numGolosinas];
		String line="";
		for (int x=0;x<golosinas.length;x++) {
			line=br.readLine();
			String lineSplit[]=line.split(",");
			for (int y=0;y<golosinas[x].length;y++) {
				golosinas[x][y]=lineSplit[y];
			}
		}
		return golosinas;
	}

}
