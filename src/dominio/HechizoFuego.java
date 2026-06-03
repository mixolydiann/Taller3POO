package dominio;
/*
Luis Molina / 21.564.225-9 / mixolydiann
Vicente Guerra / 21.855.415-6 / nemura0
*/

// formato: Nombre;Fuego;Daño;DuracionQuemadura
public class HechizoFuego extends Hechizo {

	private int duracionQuemadura;

	public HechizoFuego(String nombre, int dano, int duracionQuemadura) {
		super(nombre, "Fuego", dano);
		this.duracionQuemadura = duracionQuemadura;
	}

	public int getDuracionQuemadura() {
		return duracionQuemadura;
	}

	public void setDuracionQuemadura(int duracionQuemadura) {
		this.duracionQuemadura = duracionQuemadura;
	}

	// fuego -> puntaje = daño * duracionQuemadura
	@Override
	public int calcularPuntaje() {
		return getDano() * duracionQuemadura;
	}

	@Override
	public String toLineaArchivo() {
		return getNombre() + ";Fuego;" + getDano() + ";" + duracionQuemadura;
	}

}
