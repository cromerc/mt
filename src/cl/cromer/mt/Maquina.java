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
	private final Automata maquina;
	private Estado estadoActual;
	private Enlace enlaceActual;
	private String cintaAnterior;
	private int cabezal;

	/**
	 * Constructor de la clase
	 *
	 * @param document Document asociado al XML
	 */
	public Maquina(Document document) {
		maquina = new Automata(document);
		reset();
	}

	/**
	 * Retorna la maquina de Turing
	 *
	 * @return maquina asociado a alguna función de transición
	 */
	public Automata getMaquina() {
		return maquina;
	}

	/**
	 * Retorna el estado en que esta la maquina
	 *
	 * @return estado actual
	 */
	public Estado getEstadoActual() {
		return estadoActual;
	}

	/**
	 * Asigna un estado actual que esta la maquina en un instante de tiempo
	 *
	 * @param estadoActual
	 */
	public void setEstadoActual(Estado estadoActual) {
		this.estadoActual = estadoActual;
	}

	/**
	 * Retorna un enlace en que la maquina asocio a la cadena ingresada y al cabezal que se encuentra
	 *
	 * @return el enlace actual
	 */
	public Enlace getEnlaceActual() {
		return enlaceActual;
	}

	/**
	 * Asigna un enlace actual que esta la maquina en un instante de tiempo
	 *
	 * @param enlaceActual
	 */
	public void setEnlaceActual(Enlace enlaceActual) {
		this.enlaceActual = enlaceActual;
	}

	/**
	 * Retorna la cadena anterior a que la maquina hiciera cambios
	 *
	 * @return la cadena anterior
	 */
	public String getCintaAnterior() {
		return cintaAnterior;
	}

	/**
	 * Asigna la cadena anterior a que la maquina hiciera cambios
	 * @param cintaAnterior
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
	 * @param cabezal
	 */
	public void setCabezal(int cabezal) {
		this.cabezal = cabezal;
	}

	/**
	 * Inicializa atributos
	 */
	public void reset() {
		setEstadoActual(getMaquina().getEstados().get(0));
		setEnlaceActual(null);
		setCintaAnterior("");
		setCabezal(0);
	}

	/**
	 * Comprueba que si la cadena ingresada es reconocida por la maquina
	 *
	 * @param cinta cadena ingresada por el usuario
	 * @param estadoFinal Arreglo de estados finales, también ingresados por usuario
	 * @return Verdadero si lo reconoce, caso contrario, falso
	 */
	public boolean comprobarCadena(StringBuilder cinta, int[] estadoFinal) {
		int i;
		for (i = 0; i < getEstadoActual().getEnlaces().size(); i++) {
			if (getEstadoActual().getEnlaces().get(i).getSi() == cinta.charAt(getCabezal())) {
				setEnlaceActual(getEstadoActual().getEnlaces().get(i));
				cinta.setCharAt(getCabezal(), getEnlaceActual().getSj());
				movimientoCabezal(getEnlaceActual().getMovimiento(),cinta);
				setEstadoActual(getEnlaceActual().getQj());
				i = -1;
			}
		}
		for (i = 0; i < estadoFinal.length; i++) {
			if (getEstadoActual().getQ() == estadoFinal[i]) {
				reset();
				return true;
			}
		}
		reset();
		return false;
	}

	/**
	 * Comprueba que si el simbolo en la cadena, identificado por el cabezal
	 * es reconocido por la maquina. Este guarda la cadena anterior, estado actual y
	 * un enlace actual
	 *
	 * @param cinta Cadena ingresada por el usuario
	 * @param estadoFinal Arreglo de estados finales, también ingresados por usuario
	 * @return
	 */
	public boolean comprobarCadenaS2S(StringBuilder cinta, int[] estadoFinal) {
		setCintaAnterior(cinta.toString());
		int i;
		for (i = 0; i < getEstadoActual().getEnlaces().size(); i++) {
			if (getEstadoActual().getEnlaces().get(i).getSi() == cinta.charAt(getCabezal())) {
				setEnlaceActual(getEstadoActual().getEnlaces().get(i));
				setEstadoActual(getEnlaceActual().getQj());
				cinta.setCharAt(getCabezal(), getEnlaceActual().getSj());
				movimientoCabezal(getEnlaceActual().getMovimiento(),cinta);
				return true;
			}
		}
		for (i = 0; i < estadoFinal.length; i++) {
			if (getEstadoActual().getQ() == estadoFinal[i]) {
				setEnlaceActual(null);  // Indicar que no hay más transiciones
				return true;
			}
		}
		return false;
	}

	/**
	 * Realiza el movimiento correspondiente a base del caracter guardado en [movimiento]
	 * @param mov caracter asociado al movimiento
	 * @param cinta Cadena ingresada por el usuario
	 */
	private void movimientoCabezal(char mov,StringBuilder cinta) {
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

