# MAQUINA DE TURING
Este proyecto es una implementación de la Maquina de Turing.

# AUTORES
* Christopher Cromer
* Carlos Faúndez

# TABLA DE CONTENIDO
* [REQUISITOS](#requisitos)
* [DESCARGAR BINARIOS](#binarios)
* [USO](#uso)
	* [Transiciones ejemplo](#transiciones)
	* [Cargar transiciones](#cargar_transiciones)
	* [Comprobar](#comprobar)
		* [Reconocimiento Individual](#individual)
		* [Reconocimiento por Lote](#lote)
* [CÓDIGO](#codigo)
* [JAVADOC](#javadoc)
* [LICIENCIA](#liciencia)

## REQUISITIOS <a id="requisitos"></a>
Para compilar y/o correr el código se necesita:
* Java 8
* JavaFX

Se puede compilarlo a treves de build artifact, o tambien se puede compilar con apache-ant.
El proyecto fue desarollado en IntellIJ, pero se puede importar a NetBeans o Eclipse con un poco de cambios.

## DESCARGAR BINARIOS <a id="binarios"></a>
Se puede encontrar binarios compilados para windows y linux en la pagina: [MT](https://cromer.cl/mt)

## USO <a id="uso"></a>

### Transiciones ejemplo <a id="transiciones"></a>
Los archivos de xml de los transiciones debe ser de este estilo:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE root SYSTEM "mtbase.dtd">
<root>
	<transicion>
		<si>0</si>
		<qj>0</qj>
		<sj>0</sj>
		<qj>0</qj>
		<movimiento>R</movimiento>
	</transicion>
</root>
```

### Cargar transiciones <a id="cargar_transiciones"></a>
Para usar el programa es necesario cargio un archivo valido de xml que contiene transiciones.
Al cargar un archivo valido de xml se pide ingresar los estados finales que desea utilizar.

En la carpeta "ejemplos" se encuentra algunos ejemplos de maquinas que puede usar con el programa.

### Comprobar <a id="comprobar"></a>
Despues de cargar un archivo de xml, se puede correr la maquina de forma individual o por lote.

#### Reconocimiento Individual <a id="individual"></a>
Con la opción de reconocimiento individual se pide una cadena para combprobar si se puede reconocer con la maquina cargada anteriormente.
Se muestra paso por paso la cinta durante la operación.

#### Reconocimiento por Lote <a id="lote"></a>
La opción de reconocimiento por lote pide que ingresa varias cadenas. Al correr la maquina se va a mostrar si cada uno es aceptada or rechazada por la maquina.

## CÓDIGO <a id="codigo"></a>
El código se encuentra en la carpeta src o en github: [mt](https://github.com/cromerc/mx)

## JAVADOC <a id="javadoc"></a>
La documentación del proyecto se puede ver en la carpeta doc o en la enlace: [JavaDoc](https://cromer.cl/mt/doc/index.html)

## LICIENCIA <a id="liciencia"></a>
El programa es bajo la licenca de "3 Clause BSD" que se encuentra en la carpeta principal o en la pagina: [LICENSE](https://github.com/cromerc/fundamentos/blob/master/LICENSE)
