# MAQUINA DE TURING
Este proyecto es un implimentación de la Maquina de Turing.

# TABLA DE CONTENIDO
  * [REQUISITOS](#requisitos)
  * [DESCARGAR BINARIOS](#binarios)
  * [LICIENCIA](#liciencia)

## REQUISITIOS <a id="requisitos"></a>
Para compilar el código se necesita:
* Java 8
* JavaFX

## DESCARGAR BINARIOS <a id="binarios"></a>
Se puede encontrar binarios compilados para windows y linux en la pagina: [MT](https://cromer.cl/mt)

## USO

### Transiciones
Los archivos de xml de los transiciones debe ser de este estilo:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
 <!DOCTYPE root SYSTEM "mtbase.dtd">
 <root>
 	<transicion>
 		<si>0</si>
 		<qj>0</qj>
 		<sj>0</sj>
 		<movimiento>R</movimiento>
 	</transicion>
 </root>
 ```

### Cargar transiciones
Para usar el programa es necesario cargio un archivo valido de xml que contiene transiciones.
Al cargar un archivo valido de xml se pide ingresar los estados finales que desea utilizar.

En la carpeta ejemplos se encuentra algunos ejemplos de maquinas que puede usar.

### Comprobar
Despues de cargar un archivo de xml, se puede correr la maquina de forma individual o por lote.
Al usar uno de estos opciones hay que ingresar cadenas y luego la maquina va a intentar reconocerlas.

## CÓDIGO
El código se encuentra en la carpeta src o en github: [fundamentos](https://github.com/cromerc/fundamentos)

## JAVADOC
La documentación del proyecto se puede ver en la carpeta doc o en la enlace: [JavaDoc](https://cromer.cl/mt/doc/index.html)
Vamos a cambiar la enlace a otra lugar despues, ignora que muestra el codigo.

## LICIENCIA <a id="liciencia"></a>
El programa es bajo la licenca de "3 Clause BSD" que se encuentra en la carpeta principal o en la pagina: [LICENSE](https://github.com/cromerc/fundamentos/blob/master/LICENSE)