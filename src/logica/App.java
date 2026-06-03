package logica;
/*
Luis Molina / 21.564.225-9 / mixolydiann
Vicente Guerra / 21.855.415-6 / nemura0
*/

import java.util.InputMismatchException;
import java.util.Scanner;

// Entrada del programa -- solo se encarga de la interaccion por consola
// (menus y validacion de entradas) y le delega toda la logica al sistema
public class App {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		// Cargar datos
		System.out.println("Iniciando sistema . . .");
		Sistema sistema = new Sistema();
		sistema.cargarHechizos();
		sistema.cargarMagos(); // los magos se enlazan a los hechizos ya cargados
		System.out.println();

		boolean ejecutando = true;

		while (ejecutando) {
			System.out.println("====================================");
			System.out.println("        MUNDO DE LA MAGIA");
			System.out.println("====================================");
			System.out.println("1) Panel Administrador");
			System.out.println("2) Panel Analista");
			System.out.println("3) Salir");
			System.out.println();
			System.out.print("> Ingrese opcion: ");

			try {
				int opcion = sc.nextInt();
				sc.nextLine();

				switch (opcion) {

				case 1:
					panelAdministrador(sc, sistema);
					break;

				case 2:
					panelAnalista(sc, sistema);
					break;

				case 3:
					ejecutando = false;
					System.out.println("Ha cerrado el programa.");
					break;

				default:
					System.out.println("Input no reconocido. Ingrese 1, 2 o 3.");
				}

			} catch (InputMismatchException e) {
				System.out.println("¡Error! Debes ingresar un caracter numerico.");
				sc.nextLine();
			}
			System.out.println();
		}

		sc.close();
	}

	// PANEL ADMINISTRADOR (esqueleto)
	// cada case esta cableado; falta implementar la llamada al Sistema (los // TODO)
	public static void panelAdministrador(Scanner sc, Sistema sistema) {
		boolean enPanel = true;

		while (enPanel) {
			System.out.println("----- Panel Administrador -----");
			System.out.println("1) Agregar Mago");
			System.out.println("2) Modificar Mago");
			System.out.println("3) Eliminar Mago");
			System.out.println("4) Agregar Hechizo");
			System.out.println("5) Modificar Hechizo");
			System.out.println("6) Eliminar Hechizo");
			System.out.println("7) Volver al menu principal");
			System.out.println();
			System.out.print("> Ingrese opcion: ");

			try {
				int opcion = sc.nextInt();
				sc.nextLine();

				switch (opcion) {

				case 1:
					// TODO: pedir nombre (y hechizos) y llamar a sistema.agregarMago(...)
					break;

				case 2:
					// TODO: pedir nombre y llamar a sistema.modificarMago(...)
					break;

				case 3:
					// TODO: pedir nombre y llamar a sistema.eliminarMago(...)
					break;

				case 4:
					// TODO: pedir tipo + datos, instanciar la subclase correcta
					//       (HechizoFuego/Tierra/Planta/Agua) y llamar a sistema.agregarHechizo(...)
					break;

				case 5:
					// TODO: pedir nombre/datos y llamar a sistema.modificarHechizo(...)
					break;

				case 6:
					// TODO: pedir nombre y llamar a sistema.eliminarHechizo(...)
					break;

				case 7:
					enPanel = false;
					break;

				default:
					System.out.println("Input no reconocido.");
				}

			} catch (InputMismatchException e) {
				System.out.println("¡Error! Debes ingresar un caracter numerico.");
				sc.nextLine();
			}
			System.out.println();
		}
	}

	// PANEL ANALISTA (esqueleto)
	// Para las opciones 3-6 recorrer sistema.getCatalogoHechizos()
	// sistema.getListaMagos(); para 1-2 usar sistema.topHechizos(10) / topMagos(3).
	public static void panelAnalista(Scanner sc, Sistema sistema) {
		boolean enPanel = true;

		while (enPanel) {
			System.out.println("----- Panel Analista -----");
			System.out.println("1) Top 10 Mejores Hechizos");
			System.out.println("2) Top 3 Mejores Magos");
			System.out.println("3) Mostrar todos los Hechizos");
			System.out.println("4) Mostrar todos los Magos");
			System.out.println("5) Mostrar todos los Hechizos junto a su puntuacion");
			System.out.println("6) Mostrar todos los Magos junto a su puntuacion");
			System.out.println("7) Volver al menu principal");
			System.out.println();
			System.out.print("> Ingrese opcion: ");

			try {
				int opcion = sc.nextInt();
				sc.nextLine();

				switch (opcion) {

				case 1:
					// TODO: recorrer sistema.topHechizos(10) e imprimir nombre + puntaje
					break;

				case 2:
					// TODO: recorrer sistema.topMagos(3) e imprimir nombre + puntaje
					break;

				case 3:
					// TODO: recorrer sistema.getCatalogoHechizos() e imprimir nombre + tipo
					break;

				case 4:
					// TODO: recorrer sistema.getListaMagos() e imprimir el nombre
					break;

				case 5:
					// TODO: recorrer hechizos e imprimir nombre + calcularPuntaje()
					break;

				case 6:
					// TODO: recorrer magos e imprimir nombre + calcularPuntaje()
					break;

				case 7:
					enPanel = false;
					break;

				default:
					System.out.println("Input no reconocido.");
				}

			} catch (InputMismatchException e) {
				System.out.println("¡Error! Debes ingresar un caracter numerico.");
				sc.nextLine();
			}
			System.out.println();
		}
	}

}
