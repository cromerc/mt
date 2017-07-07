/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import org.w3c.dom.Document;

/**
 * Clase de la Maquina de Turing que renocerá algún asociado a las transiciones escritas en un archivo XML
 */
public class Maquina {
	/**
	 * La automata destras de la maquina de turning
	 */
	private final Automata automata;

	/**
	 * El estado actual donde se encuentra la maquina
	 */
	private Estado estadoActual;

	/**
	 * El enlace actual
	 */
	private Enlace enlaceActual;

	/**
	 * La cinta anterior para usar paso a paso
	 */
	private String cintaAnterior;

	/**
	 * Donde está el cabezal
	 */
	private int cabezal;

	/**
	 * Constructor de la clase
	 *
	 * @param document Document asociado al XML
	 */
	public Maquina(Document document) {
		automata = new Automata(document);
		reset();
	}

	/**
	 * Retorna la automata de Turing
	 *
	 * @return automata asociado a alguna función de transición
	 */
	public Automata getAutomata() {
		return automata;
	}

	/**
	 * Retorna el estado en que esta la automata
	 *
	 * @return estado actual
	 */
	public Estado getEstadoActual() {
		return estadoActual;
	}

	/**
	 * Asigna un estado actual que esta la automata en un instante de tiempo
	 *
	 * @param estadoActual En que estado es la automata
	 */
	public void setEstadoActual(Estado estadoActual) {
		this.estadoActual = estadoActual;
	}

	/**
	 * Retorna un enlace en que la automata asocio a la cadena ingresada y al cabezal que se encuentra
	 *
	 * @return el enlace actual
	 */
	public Enlace getEnlaceActual() {
		return enlaceActual;
	}

	/**
	 * Asigna un enlace actual que esta la automata en un instante de tiempo
	 *
	 * @param enlaceActual La enlace donde esta la automata
	 */
	public void setEnlaceActual(Enlace enlaceActual) {
		this.enlaceActual = enlaceActual;
	}

	/**
	 * Retorna la cadena anterior a que la automata hiciera cambios
	 *
	 * @return la cadena anterior
	 */
	public String getCintaAnterior() {
		return cintaAnterior;
	}

	/**
	 * Asigna la cadena anterior a que la automata hiciera cambios
	 * @param cintaAnterior La cinta a verificar
	 */
	public void setCintaAnterior(String cintaAnterior) {
		this.cintaAnterior = cintaAnterior;
	}

	/**
	 * Retorna el indice en que se ubica en la cadena (Cabezal)
	 *
	 * @return un número asociado
	 */
	public int getCabezal() {
		return cabezal;
	}

	/**
	 * Asigna el indice en que se ubica en la cadena (Cabezal)
	 *
	 * @param cabezal Donde poner la cabezal
	 */
	public void setCabezal(int cabezal) {
		this.cabezal = cabezal;
	}

	/**
	 * Inicializa atributos
	 */
	public void reset() {
		setEstadoActual(getAutomata().getEstados().get(0));
		setEnlaceActual(null);
		setCintaAnterior("");
		setCabezal(1);
	}

	/**
	 * Comprueba que si la cadena ingresada es reconocida por la automata
	 *
	 * @param cinta cadena ingresada por el usuario
	 * @param estadosFinales Arreglo de estados finales, también ingresados por usuario
	 * @return Verdadero si lo reconoce, caso contrario, falso
	 */
	public boolean comprobarCadena(StringBuilder cinta, int[] estadosFinales) {
		for (int i = 0; i < getEstadoActual().getEnlaces().size(); i++) {
			if (getEstadoActual().getEnlaces().get(i).getSi() == cinta.charAt(getCabezal())) {
				setEnlaceActual(getEstadoActual().getEnlaces().get(i));
				cinta.setCharAt(getCabezal(), getEnlaceActual().getSj());
				movimientoCabezal(getEnlaceActual().getMovimiento(), cinta);
				setEstadoActual(getEnlaceActual().getQj());
				i = -1;
			}
		}
		for (int estadoFinal : estadosFinales) {
			if (getEstadoActual().getQ() == estadoFinal) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba que si el simbolo en la cadena, identificado por el cabezal
	 * es reconocido por la automata. Este guarda la cadena anterior, estado actual y
	 * un enlace actual
	 *
	 * @param cinta Cadena ingresada por el usuario
	 * @param estadosFinales Arreglo de estados finales, también ingresados por usuario
	 * @return Verdadero si reconce la cinta, sino falso
	 */
	public int comprobarCadenaS2S(StringBuilder cinta, int[] estadosFinales) {
		if (cintaAnterior.equals("")) {
			setCintaAnterior(cinta.toString());
		}
		else {
			cinta = new StringBuilder(getCintaAnterior());
		}
		for (int i = 0; i < getEstadoActual().getEnlaces().size(); i++) {
			if (getEstadoActual().getEnlaces().get(i).getSi() == cinta.charAt(getCabezal())) {
				setEnlaceActual(getEstadoActual().getEnlaces().get(i));
				cinta.setCharAt(getCabezal(), getEnlaceActual().getSj());
				movimientoCabezal(getEnlaceActual().getMovimiento(), cinta);
				setEstadoActual(getEnlaceActual().getQj());
				setCintaAnterior(cinta.toString());
				return 0;
			}
		}
		for (int estadoFinal : estadosFinales) {
			if (getEstadoActual().getQ() == estadoFinal) {
				return 1;
			}
		}
		return -1;
	}

	/**
	 * Realiza el movimiento correspondiente a base del caracter guardado en [movimiento]
	 * @param mov caracter asociado al movimiento
	 * @param cinta Cadena ingresada por el usuario
	 */
	private void movimientoCabezal(char mov, StringBuilder cinta) {
		switch (mov) {
			case 'L': {
				setCabezal(getCabezal() - 1);
				if (getCabezal() == (-1)) {
					cinta.insert(0, "#");
					setCabezal(getCabezal() + 1);
				}
				break;
			}
			case 'R': {
				setCabezal(getCabezal() + 1);
				if (getCabezal() == cinta.length()) {
					cinta.insert(getCabezal(), "#");
				}
				break;
			}
			default: {/*Se mantiene*/}
		}
	}
}