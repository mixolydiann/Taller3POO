package dominio;
/*
Luis Molina / 21.564.225-9 / mixolydiann
Vicente Guerra / 21.855.415-6 / nemura0
*/

// formato: Nombre;Agua;Daño;CantidadHeal,PresionDelAgua
public class HechizoAgua extends Hechizo {

	private int cantidadHeal;
	private int presionDelAgua;

	public HechizoAgua(String nombre, int dano, int cantidadHeal, int presionDelAgua) {
		super(nombre, "Agua", dano);
		this.cantidadHeal = cantidadHeal;
		this.presionDelAgua = presionDelAgua;
	}

	public int getCantidadHeal() {
		return cantidadHeal;
	}

	public void setCantidadHeal(int cantidadHeal) {
		this.cantidadHeal = cantidadHeal;
	}

	public int getPresionDelAgua() {
		return presionDelAgua;
	}

	public void setPresionDelAgua(int presionDelAgua) {
		this.presionDelAgua = presionDelAgua;
	}

	@Override
	public int calcularPuntaje() {
		return (getDano() + cantidadHeal + presionDelAgua) * 2;
	}

	@Override
	public String toLineaArchivo() {
		return getNombre() + ";Agua;" + getDano() + ";" + cantidadHeal + "," + presionDelAgua;
	}

}
