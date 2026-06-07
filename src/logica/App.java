package logica;
/*
Luis Molina / 21.564.225-9 / mixolydiann
Vicente Guerra / 21.855.415-6 / nemura0
*/

import dominio.*;
import java.util.ArrayList;
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
					System.out.println("Ingrese el nombre para el nuevo mago: ");
					String nombreMago = sc.nextLine();
					Mago nuevoMago = new Mago(nombreMago);
					sistema.agregarMago(nuevoMago);
					break;

				case 2:
					System.out.println("Ingrese el nombre del mago a modificar: ");
					String magoMod = sc.nextLine();
					
					sistema.modificarMago(magoMod, sc);
					break;

				case 3:
					System.out.println("Ingrese el nombre del mago a eliminar:");
					String magoElim = sc.nextLine();
					
					sistema.eliminarMago(magoElim);
					break;

				case 4:
					crearHechizoDesdeConsola(sistema, sc);
					break;

				case 5:
					System.out.println("Ingrese el nombre del hechizo a modificar: ");
					String hechizoMod = sc.nextLine();
					
					sistema.modificarHechizo(hechizoMod, sc);
					break;

				case 6:
					System.out.print("Ingrese el nombre del hechizo a eliminar: ");
                    String hechizoElim = sc.nextLine();
                    sistema.eliminarHechizo(hechizoElim);
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

	private static void crearHechizoDesdeConsola(Sistema sistema, Scanner sc) {
        System.out.print("Ingrese el nombre del nuevo hechizo: ");
        String nombre = sc.nextLine();

        // Ver si existe
        if (sistema.buscarHechizo(nombre) != null) {
            System.out.println("Error: Ya existe un hechizo con ese nombre.");
            return;
        }

        System.out.print("Ingrese el daño base: ");
        int dano = Integer.parseInt(sc.nextLine());

        System.out.println("Seleccione el elemento del hechizo:");
        System.out.println("1. Fuego\n"
        		+ "2. Tierra\n"
        		+ "3. Planta\n"
        		+ "4. Agua");
        int tipoOpcion = Integer.parseInt(sc.nextLine());

        Hechizo nuevoHechizo = null;

        switch (tipoOpcion) {
            case 1:
                System.out.print("Ingrese la duración de la quemadura: ");
                int quemadura = Integer.parseInt(sc.nextLine());
                nuevoHechizo = new HechizoFuego(nombre, dano, quemadura);
                break;
            case 2:
                System.out.print("Ingrese la mejora de defensa: ");
                int defensa = Integer.parseInt(sc.nextLine());
                nuevoHechizo = new HechizoTierra(nombre, dano, defensa);
                break;
            case 3:
                System.out.print("Ingrese la duración del stun: ");
                int stun = Integer.parseInt(sc.nextLine());
                System.out.print("Ingrese la cantidad de plantas: ");
                int cantPlantas = Integer.parseInt(sc.nextLine());
                nuevoHechizo = new HechizoPlanta(nombre, dano, stun, cantPlantas);
                break;
            case 4:
                System.out.print("Ingrese la cantidad de heal: ");
                int heal = Integer.parseInt(sc.nextLine());
                System.out.print("Ingrese la presión del agua: ");
                int presion = Integer.parseInt(sc.nextLine());
                nuevoHechizo = new HechizoAgua(nombre, dano, heal, presion);
                break;
            default:
                System.out.println("Tipo no válido. Creación cancelada.");
                return;
        }

        // Si se cero bien lo mandamos
        if (nuevoHechizo != null) {
            sistema.agregarHechizo(nuevoHechizo);
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
					ArrayList<Hechizo> topHechizos = sistema.topHechizos(10);
					System.out.println("--- Top 10 Mejores Hechizos ---");
					if (topHechizos.isEmpty()) {
						System.out.println("No hay hechizos cargados.");
					} else {
						for (int i = 0; i < topHechizos.size(); i++) {
							Hechizo h = topHechizos.get(i);
							System.out.println((i + 1) + ") " + h.getNombre() + " - " + h.calcularPuntaje() + " pts");
						}
					}
					break;

				case 2:
					ArrayList<Mago> topMagos = sistema.topMagos(3);
					System.out.println("--- Top 3 Mejores Magos ---");
					if (topMagos.isEmpty()) {
						System.out.println("No hay magos cargados.");
					} else {
						for (int i = 0; i < topMagos.size(); i++) {
							Mago m = topMagos.get(i);
							System.out.println((i + 1) + ") " + m.getNombre() + " - " + m.calcularPuntaje() + " pts");
						}
					}
					break;

				case 3:
					System.out.println("--- Todos los Hechizos ---");
					if (sistema.getCatalogoHechizos().isEmpty()) {
						System.out.println("No hay hechizos cargados.");
					} else {
						for (Hechizo h : sistema.getCatalogoHechizos()) {
							System.out.println(h.getNombre() + " (" + h.getTipo() + ")");
						}
					}
					break;

				case 4:
					System.out.println("--- Todos los Magos ---");
					if (sistema.getListaMagos().isEmpty()) {
						System.out.println("No hay magos cargados.");
					} else {
						for (Mago m : sistema.getListaMagos()) {
							System.out.println(m.getNombre());
						}
					}
					break;

				case 5:
					System.out.println("--- Hechizos con su puntuacion ---");
					if (sistema.getCatalogoHechizos().isEmpty()) {
						System.out.println("No hay hechizos cargados.");
					} else {
						for (Hechizo h : sistema.getCatalogoHechizos()) {
							System.out.println(h.getNombre() + " -> " + h.calcularPuntaje() + " pts");
						}
					}
					break;

				case 6:
					System.out.println("--- Magos con su puntuacion ---");
					if (sistema.getListaMagos().isEmpty()) {
						System.out.println("No hay magos cargados.");
					} else {
						for (Mago m : sistema.getListaMagos()) {
							System.out.println(m.getNombre() + " -> " + m.calcularPuntaje() + " pts");
						}
					}
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
