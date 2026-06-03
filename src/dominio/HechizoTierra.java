package dominio;
/*
Luis Molina / 21.564.225-9 / mixolydiann
Vicente Guerra / 21.855.415-6 / nemura0
*/

// formato: Nombre;Tierra;Daño;MejoraDefensa
public class HechizoTierra extends Hechizo {

	private int mejoraDefensa;

	public HechizoTierra(String nombre, int dano, int mejoraDefensa) {
		super(nombre, "Tierra", dano);
		this.mejoraDefensa = mejoraDefensa;
	}

	public int getMejoraDefensa() {
		return mejoraDefensa;
	}

	public void setMejoraDefensa(int mejoraDefensa) {
		this.mejoraDefensa = mejoraDefensa;
	}

	// tierra -> puntaje = (Daño * MejoraDefensa) / 2
	@Override
	public int calcularPuntaje() {
		return (getDano() * mejoraDefensa) / 2;
	}

	@Override
	public String toLineaArchivo() {
		return getNombre() + ";Tierra;" + getDano() + ";" + mejoraDefensa;
	}

}
