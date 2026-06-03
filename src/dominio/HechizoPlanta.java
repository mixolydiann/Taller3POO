package dominio;
/*
Luis Molina / 21.564.225-9 / mixolydiann
Vicente Guerra / 21.855.415-6 / nemura0
*/

// formato: Nombre;Planta;Daño;DuracionStun,CantPlantas
public class HechizoPlanta extends Hechizo {

	private int duracionStun;
	private int cantPlantas;

	public HechizoPlanta(String nombre, int dano, int duracionStun, int cantPlantas) {
		super(nombre, "Planta", dano);
		this.duracionStun = duracionStun;
		this.cantPlantas = cantPlantas;
	}

	public int getDuracionStun() {
		return duracionStun;
	}

	public void setDuracionStun(int duracionStun) {
		this.duracionStun = duracionStun;
	}

	public int getCantPlantas() {
		return cantPlantas;
	}

	public void setCantPlantas(int cantPlantas) {
		this.cantPlantas = cantPlantas;
	}

	// planta -> puntaje = daño + (DuracionStun * CantPlantas)
	@Override
	public int calcularPuntaje() {
		return getDano() + (duracionStun * cantPlantas);
	}

	@Override
	public String toLineaArchivo() {
		return getNombre() + ";Planta;" + getDano() + ";" + duracionStun + "," + cantPlantas;
	}

}
