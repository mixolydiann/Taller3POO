# Taller 3 POO 2026-I

Programa de consola en Java para administrar magos y sus hechizos. Cada hechizo
pertenece a un elemento (Fuego, Tierra, Planta o Agua) y segun el elemento se
calcula su puntaje de forma distinta. El puntaje de un mago es la suma de los
puntajes de los hechizos que domina.

Los datos se cargan desde dos archivos de texto (`Magos.txt` y `Hechizos.txt`) al
iniciar, y cualquier cambio que se haga en el panel de administrador se vuelve a
escribir en esos mismos archivos para que persista.

## Integrantes

- Luis Molina (mixolydiann)
- Vicente Guerra (nemura0)

## Como ejecutar

El proyecto esta pensado para correr en Eclipse IDE. Pasos:

1. Descomprimir el .zip del proyecto.
2. En Eclipse: File -> Import -> General -> Existing Projects into Workspace, y
   seleccionar la carpeta del proyecto. Tambien sirve File -> Open Projects from
   File System apuntando a la carpeta.
3. Una vez importado, abrir `src/logica/App.java` y darle Run (el boton verde o
   click derecho -> Run As -> Java Application). El main esta ahi.

Los archivos `Magos.txt` y `Hechizos.txt` estan en la raiz del proyecto y se leen
y se escriben con ruta relativa, asi que hay que dejarlos donde estan para que el
programa los encuentre.

## Como esta organizado

Se separo el punto de entrada de la logica del sistema, y el dominio quedo aparte.

```
src/
├── logica/
│   ├── App.java       -> main, menus por consola y validacion de entradas
│   └── Sistema.java   -> toda la logica: cargar, guardar, CRUD y los rankings
└── dominio/
    ├── Puntuable.java     -> interfaz con calcularPuntaje()
    ├── Hechizo.java       -> clase abstracta base de los hechizos
    ├── HechizoFuego.java
    ├── HechizoTierra.java
    ├── HechizoPlanta.java
    ├── HechizoAgua.java
    └── Mago.java          -> tiene una lista de hechizos
```

`App` solo se encarga de mostrar menus y leer lo que escribe el usuario, despues le
pasa todo a `Sistema`. `Sistema` es el que guarda las dos colecciones (catalogo de
hechizos y lista de magos) y hace el trabajo pesado.

## Modelo

`Hechizo` es una clase abstracta que guarda lo que comparten todos los hechizos
(nombre, tipo y daño) e implementa la interfaz `Puntuable`. Cada subclase
(`HechizoFuego`, `HechizoTierra`, `HechizoPlanta`, `HechizoAgua`) agrega sus propios
atributos y define como se calcula su puntaje y como se escribe su linea en el txt.

Un `Mago` tiene un `ArrayList<Hechizo>` con referencias a los mismos objetos del
catalogo global. Asi, si se modifica un hechizo, el cambio se ve reflejado
automaticamente en todos los magos que lo tienen.

## Calculo de puntajes

Cada elemento tiene su formula:

- Fuego  -> Daño * DuracionQuemadura
- Tierra -> (Daño * MejoraDefensa) / 2
- Planta -> Daño + (DuracionStun * CantPlantas)
- Agua   -> (Daño + CantidadHeal + PresionDelAgua) * 2

El puntaje de un mago es la suma de los puntajes de todos sus hechizos.

## Que hace cada panel

### Panel Administrador

CRUD de magos y hechizos. Cada cambio se guarda al tiro en el archivo
correspondiente, respetando el formato original.

```
1) Agregar Mago
2) Modificar Mago
3) Eliminar Mago
4) Agregar Hechizo
5) Modificar Hechizo
6) Eliminar Hechizo
```

### Panel Analista

Solo lectura, muestra reportes y rankings. Los rankings se arman ordenando una copia
de la coleccion por puntaje (de mayor a menor) con ordenamiento por seleccion.

```
1) Top 10 Mejores Hechizos
2) Top 3 Mejores Magos
3) Mostrar todos los Hechizos
4) Mostrar todos los Magos
5) Mostrar todos los Hechizos junto a su puntuacion
6) Mostrar todos los Magos junto a su puntuacion
```

## Formato de los archivos

`Magos.txt`:

```
NombreMago;Hechizo1|Hechizo2|HechizoN
```

`Hechizos.txt` (los parametros cambian segun el tipo):

```
NombreHechizo;Fuego;Daño;DuracionQuemadura
NombreHechizo;Tierra;Daño;MejoraDefensa
NombreHechizo;Planta;Daño;DuracionStun,CantPlantas
NombreHechizo;Agua;Daño;CantidadHeal,PresionDelAgua
```

Si alguno de los archivos no existe, el programa avisa y sigue funcionando con las
listas vacias.

## Librerias usadas

- `Scanner` para leer los archivos y la entrada por consola
- `BufferedWriter` / `FileWriter` para sobrescribir los txt
- `ArrayList` para las colecciones
