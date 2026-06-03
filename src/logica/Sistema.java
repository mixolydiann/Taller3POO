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

	// Implementacion carga de archivos
	
	// Lee Hechizos.txt y segun el tipo instancia la subclase correcta parseando su 4to campo
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

	// Lee Magos.txt y enlaza cada nombre de hechizo con el objeto real del catalogo
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

	// busca un hechizo en el catalogo por nombre
	public Hechizo buscarHechizo(String nombre) {
		for (Hechizo h : catalogoHechizos) {
			if (h.getNombre().equalsIgnoreCase(nombre)) {
				return h;
			}
		}
		return null;
	}

	// busca un mago en la lista por nombre
	public Mago buscarMago(String nombre) {
		for (Mago m : listaMagos) {
			if (m.getNombre().equalsIgnoreCase(nombre)) {
				return m;
			}
		}
		return null;
	}

	// Implementacion escritura de archivos
	
	// Reescribe Hechizos.txt completo
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

	// Reescribe Magos.txt completo con el formato Nombre;Hechizo1|Hechizo2|...
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

	// PANEL ADMIN (TODO)

	// 1. Agregar Mago.
	// TODO: validar que no exista ya (buscarMago()), crear el Mago, opcionalmente
	//       pedir sus hechizos (enlazar con buscarHechizo()), agregarlo a listaMagos
	//       y al final llamar guardarMagos() para que persista en el .txt.
	public void agregarMago(Mago nuevo) {
		// TODO: implementar
	}

	// 2. Modificar Mago.
	// TODO: buscar el mago; si existe, cambiarle el nombre y/o su repertorio de
	//       hechizos, y luego guardarMagos().
	public boolean modificarMago(String nombre) {
		// TODO: implementar
		return false;
	}

	// 3. Eliminar Mago.
	// TODO: buscar y remover de listaMagos; luego guardarMagos().
	public boolean eliminarMago(String nombre) {
		// TODO: implementar
		return false;
	}

	// 4. Agregar Hechizo.
	// TODO: validar que no exista (buscarHechizo()), agregar 'nuevo' al catalogo y
	//       guardarHechizos(). OJO: el Main es quien instancia la subclase correcta
	//       (HechizoFuego / HechizoTierra / HechizoPlanta / HechizoAgua).
	public void agregarHechizo(Hechizo nuevo) {
		// TODO: implementar
	}

	// 5. Modificar Hechizo.
	// TODO: buscar el hechizo; actualizar su daño y los atributos propios de su
	//       elemento (castear con instanceof a la subclase y usar sus setters).
	//       Como los magos guardan REFERENCIAS al mismo objeto, el cambio se refleja
	//       solo en todos los magos que lo poseen. Finalmente guardarHechizos().
	public boolean modificarHechizo(String nombre) {
		// TODO: implementar
		return false;
	}

	// 6. Eliminar Hechizo.
	// TODO: remover del catalogo Y tambien de la lista de cada mago que lo tenga
	//       (recorrer listaMagos y quitarlo de su ArrayList de hechizos).
	//       Luego guardarHechizos() y guardarMagos().
	public boolean eliminarHechizo(String nombre) {
		// TODO: implementar
		return false;
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
