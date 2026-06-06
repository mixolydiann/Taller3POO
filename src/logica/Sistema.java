package logica;
/*
Luis Molina / 21.564.225-9 / mixolydiann
Vicente Guerra / 21.855.415-6 / nemura0
*/
import dominio.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {

	private static final String ARCHIVO_HECHIZOS = "Hechizos.txt";
	private static final String ARCHIVO_MAGOS = "Magos.txt";

	private ArrayList<Hechizo> catalogoHechizos;
	private ArrayList<Mago> listaMagos;

	public Sistema() {
		this.catalogoHechizos = new ArrayList<>();
		this.listaMagos = new ArrayList<>();
	}

	public ArrayList<Hechizo> getCatalogoHechizos() {
		return catalogoHechizos;
	}

	public ArrayList<Mago> getListaMagos() {
		return listaMagos;
	}
	
	
	public void cargarHechizos() {
		try {
			File archivo = new File(ARCHIVO_HECHIZOS);
			Scanner sc = new Scanner(archivo);

			while (sc.hasNextLine()) {
				String linea = sc.nextLine().trim();
				if (linea.isEmpty()) {
					continue;
				}

				String[] partes = linea.split(";");
				String nombre = partes[0].trim();
				String tipo = partes[1].trim();
				int dano = Integer.parseInt(partes[2].trim());
				String params = partes[3].trim(); // el 4to campo depende del tipo

				Hechizo nuevo = null;

				if (tipo.equalsIgnoreCase("Fuego")) {
					// Fuego -> DuracionQuemadura
					int duracionQuemadura = Integer.parseInt(params);
					nuevo = new HechizoFuego(nombre, dano, duracionQuemadura);

				} else if (tipo.equalsIgnoreCase("Tierra")) {
					// Tierra -> MejoraDefensa
					int mejoraDefensa = Integer.parseInt(params);
					nuevo = new HechizoTierra(nombre, dano, mejoraDefensa);

				} else if (tipo.equalsIgnoreCase("Planta")) {
					// Planta -> DuracionStun,CantPlantas
					String[] datos = params.split(",");
					int duracionStun = Integer.parseInt(datos[0].trim());
					int cantPlantas = Integer.parseInt(datos[1].trim());
					nuevo = new HechizoPlanta(nombre, dano, duracionStun, cantPlantas);

				} else if (tipo.equalsIgnoreCase("Agua")) {
					// Agua -> CantidadHeal,PresionDelAgua
					String[] datos = params.split(",");
					int cantidadHeal = Integer.parseInt(datos[0].trim());
					int presionDelAgua = Integer.parseInt(datos[1].trim());
					nuevo = new HechizoAgua(nombre, dano, cantidadHeal, presionDelAgua);
				}

				if (nuevo != null) {
					catalogoHechizos.add(nuevo);
				}
			}

			sc.close();
			System.out.println("Hechizos cargados con exito (" + catalogoHechizos.size() + ").");

		} catch (FileNotFoundException e) {
			System.out.println("Error: No se encontro el archivo " + ARCHIVO_HECHIZOS);
		}
	}
	
	
	public void cargarMagos() {
		try {
			File archivo = new File(ARCHIVO_MAGOS);
			Scanner sc = new Scanner(archivo);

			while (sc.hasNextLine()) {
				String linea = sc.nextLine().trim();
				if (linea.isEmpty()) {
					continue;
				}

				String[] partes = linea.split(";");
				String nombre = partes[0].trim();
				Mago mago = new Mago(nombre);

				if (partes.length > 1) {
					String[] nombresHechizos = partes[1].split("\\|");

					for (String nombreHechizo : nombresHechizos) {
						Hechizo encontrado = buscarHechizo(nombreHechizo.trim());
						if (encontrado != null) {
							mago.agregarHechizo(encontrado);
						}
					}
				}

				listaMagos.add(mago);
			}

			sc.close();
			System.out.println("Magos cargados con exito (" + listaMagos.size() + ").");

		} catch (FileNotFoundException e) {
			System.out.println("Error: No se encontro el archivo " + ARCHIVO_MAGOS);
		}
	}

	// busca un hechizo
	public Hechizo buscarHechizo(String nombre) {
		for (Hechizo h : catalogoHechizos) {
			if (h.getNombre().equalsIgnoreCase(nombre)) {
				return h;
			}
		}
		return null;
	}

	// busca un mago
	public Mago buscarMago(String nombre) {
		for (Mago m : listaMagos) {
			if (m.getNombre().equalsIgnoreCase(nombre)) {
				return m;
			}
		}
		return null;
	}

	// Escritura de archivos
	
	
	public void guardarHechizos() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_HECHIZOS, false));
			for (Hechizo h : catalogoHechizos) {
				bw.write(h.toLineaArchivo());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("Error: no se pudo escribir " + ARCHIVO_HECHIZOS);
		}
	}


	public void guardarMagos() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_MAGOS, false));
			for (Mago m : listaMagos) {
				String linea = m.getNombre() + ";";
				ArrayList<Hechizo> hechizos = m.getHechizos();
				for (int i = 0; i < hechizos.size(); i++) {
					linea = linea + hechizos.get(i).getNombre();
					if (i < hechizos.size() - 1) {
						linea = linea + "|";
					}
				}
				bw.write(linea);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("Error: no se pudo escribir " + ARCHIVO_MAGOS);
		}
	}

	// ==========================================
		// PANEL ADMIN
		// ==========================================

		// 1. Agregar Mago.
		public boolean agregarMago(Mago nuevo) {
			if (buscarMago(nuevo.getNombre()) != null) {
				System.out.println("Error: Ya existe un mago con el nombre " + nuevo.getNombre());
				return false;
			}
			listaMagos.add(nuevo);
			guardarMagos();
			System.out.println("Mago agregado exitosamente.");
			return true;
		}

		// 2. Modificar Mago.
		public boolean modificarMago(String nombre, Scanner sc) {
			Mago mago = buscarMago(nombre);
			if (mago == null) {
				System.out.println("Error: El mago no existe.");
				return false;
			}

			System.out.println("Seleccione que desea modificar:");
			System.out.println("1. Cambiar nombre");
			System.out.println("2. Agregar hechizo al repertorio");
			System.out.println("3. Eliminar hechizo del repertorio");
			int opcion = Integer.parseInt(sc.nextLine());

			if (opcion == 1) {
				System.out.print("Ingrese el nuevo nombre: ");
				String nuevoNombre = sc.nextLine();
				if (buscarMago(nuevoNombre) == null) {
					mago.setNombre(nuevoNombre); // Asegurate de tener el setter en la clase Mago
					System.out.println("Nombre actualizado.");
				} else {
					System.out.println("Error: Ya existe un mago con ese nombre.");
				}
			} else if (opcion == 2) {
				System.out.print("Ingrese el nombre del hechizo a agregar: ");
				String nomHechizo = sc.nextLine();
				Hechizo h = buscarHechizo(nomHechizo);
				if (h != null) {
					mago.agregarHechizo(h);
					System.out.println("Hechizo agregado al mago.");
				} else {
					System.out.println("Error: El hechizo no existe en el catalogo.");
				}
			} else if (opcion == 3) {
				System.out.print("Ingrese el nombre del hechizo a eliminar: ");
				String nomHechizo = sc.nextLine();
				Hechizo h = buscarHechizo(nomHechizo);
				if (h != null && mago.getHechizos().contains(h)) {
					mago.getHechizos().remove(h);
					System.out.println("Hechizo eliminado del repertorio del mago.");
				} else {
					System.out.println("Error: El mago no posee este hechizo.");
				}
			} else {
				System.out.println("Opcion no valida.");
				return false;
			}

			guardarMagos();
			return true;
		}

		// 3. Eliminar Mago.
		public boolean eliminarMago(String nombre) {
			Mago mago = buscarMago(nombre);
			if (mago != null) {
				listaMagos.remove(mago);
				guardarMagos();
				System.out.println("Mago eliminado exitosamente.");
				return true;
			}
			System.out.println("Error: El mago no existe.");
			return false;
		}

		// 4. Agregar Hechizo.
		public boolean agregarHechizo(Hechizo nuevo) {
			if (buscarHechizo(nuevo.getNombre()) != null) {
				System.out.println("Error: El hechizo ya existe en el catalogo.");
				return false;
			}
			catalogoHechizos.add(nuevo);
			guardarHechizos();
			System.out.println("Hechizo agregado exitosamente al catalogo.");
			return true;
		}

		// 5. Modificar Hechizo.
		public boolean modificarHechizo(String nombre, Scanner sc) {
			Hechizo h = buscarHechizo(nombre);
			if (h == null) {
				System.out.println("Error: El hechizo no existe.");
				return false;
			}

			System.out.print("Ingrese el nuevo daño (actual: " + h.getDano() + "): ");
			int nuevoDano = Integer.parseInt(sc.nextLine());
			h.setDano(nuevoDano); // Asegurate de tener setDano() en tu clase abstracta Hechizo

			// Casteo usando instanceof para acceder a los atributos especificos
			if (h instanceof HechizoFuego) {
				HechizoFuego hf = (HechizoFuego) h;
				System.out.print("Ingrese nueva duracion de quemadura (actual: " + hf.getDuracionQuemadura() + "): ");
				hf.setDuracionQuemadura(Integer.parseInt(sc.nextLine()));
			} else if (h instanceof HechizoTierra) {
				HechizoTierra ht = (HechizoTierra) h;
				System.out.print("Ingrese nueva mejora de defensa (actual: " + ht.getMejoraDefensa() + "): ");
				ht.setMejoraDefensa(Integer.parseInt(sc.nextLine()));
			} else if (h instanceof HechizoPlanta) {
				HechizoPlanta hp = (HechizoPlanta) h;
				System.out.print("Ingrese nueva duracion de stun (actual: " + hp.getDuracionStun() + "): ");
				hp.setDuracionStun(Integer.parseInt(sc.nextLine()));
				System.out.print("Ingrese nueva cantidad de plantas (actual: " + hp.getCantPlantas() + "): ");
				hp.setCantPlantas(Integer.parseInt(sc.nextLine()));
			} else if (h instanceof HechizoAgua) {
				HechizoAgua ha = (HechizoAgua) h;
				System.out.print("Ingrese nueva cantidad de heal (actual: " + ha.getCantidadHeal() + "): ");
				ha.setCantidadHeal(Integer.parseInt(sc.nextLine()));
				System.out.print("Ingrese nueva presion de agua (actual: " + ha.getPresionDelAgua() + "): ");
				ha.setPresionDelAgua(Integer.parseInt(sc.nextLine()));
			}

			// Al modificar el objeto por referencia, los magos que lo tienen ya ven el cambio en memoria.
			guardarHechizos(); 
			return true;
		}

		// 6. Eliminar Hechizo.
		public boolean eliminarHechizo(String nombre) {
			Hechizo h = buscarHechizo(nombre);
			if (h == null) {
				System.out.println("Error: El hechizo no existe.");
				return false;
			}

			// 1. Lo quitamos del catalogo general
			catalogoHechizos.remove(h);

			// 2. Lo quitamos del inventario de TODOS los magos que lo posean
			for (Mago m : listaMagos) {
				if (m.getHechizos().contains(h)) {
					m.getHechizos().remove(h);
				}
			}

			// 3. Sobrescribimos ambos archivos para reflejar el borrado global
			guardarHechizos();
			guardarMagos();
			System.out.println("Hechizo eliminado del catalogo y del repertorio de todos los magos.");
			return true;
		}

	// PANEL ANALISTA (TODO)

	// Top N hechizos con mayor puntaje.
	// TODO: copiar catalogoHechizos a una lista nueva, ordenarla de mayor a menor por
	//       calcularPuntaje() (ordenamiento manual: seleccion o burbuja, sin Comparator)
	//       y devolver solo los primeros n.
	public ArrayList<Hechizo> topHechizos(int n) {
		// TODO: implementar
		return new ArrayList<>();
	}

	// Top N magos con mayor puntaje (suma de los puntajes de sus hechizos).
	// TODO: mismo procedimiento que topHechizos pero sobre listaMagos.
	public ArrayList<Mago> topMagos(int n) {
		// TODO: implementar
		return new ArrayList<>();
	}

}
