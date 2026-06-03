package dominio;
/*
Luis Molina / 21.564.225-9 / mixolydiann
Vicente Guerra / 21.855.415-6 / nemura0
*/

import java.util.ArrayList;

// Guardamos las referencias a los mismos objetos del catalogo global
// Cuando se modifica un hechizo el cambio se hace en todos los magos
public class Mago implements Puntuable {

	private String nombre;
	private ArrayList<Hechizo> hechizos;

	public Mago(String nombre) {
		this.nombre = nombre;
		this.hechizos = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Hechizo> getHechizos() {
		return hechizos;
	}

	public void agregarHechizo(Hechizo h) {
		this.hechizos.add(h);
	}

	@Override
	public int calcularPuntaje() {
		int total = 0;
		for (Hechizo h : hechizos) {
			total += h.calcularPuntaje();
		}
		return total;
	}

}
