package dominio;
/*
Luis Molina / 21.564.225-9 / mixolydiann
Vicente Guerra / 21.855.415-6 / nemura0
*/

// Clase base de todos los hechizos
// Guarda los atributos que comparten todos (nombre, tipo y daño) y obliga a cada
// subclase a definir como calcula su puntaje y como se escribe en Hechizos.txt

public abstract class Hechizo implements Puntuable {

	private String nombre;
	private String tipo; // Fuego, Tierra, Planta o Agua
	private int dano;

	public Hechizo(String nombre, String tipo, int dano) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.dano = dano;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	// calcularPuntaje() viene de Puntuable y queda abstracto

	// cda subclase sabe escribirse a si misma en el formato exacto de Hechizos.txt
	public abstract String toLineaArchivo();

}
