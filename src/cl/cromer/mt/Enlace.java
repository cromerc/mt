/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

/**
 * Clase que funciona como enlaze entre dos estados ó asi mismo
 */
public class Enlace {
	private char si;
	private char sj;
	private char movimiento;
	private Estado qj;

	/**
	 * Contructor de la clase
	 *
	 * @param si   Simbolo a analizar en la cadena
	 * @param qj   Estado con que se enlaza
	 * @param sj   Simbolo a escribir en la cadena
	 * @param move Movimiento del indice de la cadena
	 */
	public Enlace(char si, Estado qj, char sj, char move) {
		setMovimiento(move);
		setSj(sj);
		setQj(qj);
		setSi(si);
	}

	/**
	 * Retorna simbolo a analizar
	 *
	 * @return simbolo
	 */
	public char getSi() {
		return this.si;
	}

	/**
	 * Asigna simbolo a analizar
	 *
	 * @param si
	 */
	private void setSi(char si) {
		this.si = si;
	}

	/**
	 * Retorna simbolo a escribir
	 *
	 * @return simbolo
	 */
	public char getSj() {
		return this.sj;
	}

	/**
	 * Asigna simbolo a escribir
	 *
	 * @param sj
	 */
	public void setSj(char sj) {
		this.sj = sj;
	}

	/**
	 * Retorna el movimiento del indice de la cadena
	 *
	 * @return simbolo caracteristico (R,L,E ó *)
	 */
	public char getMovimiento() {
		return this.movimiento;
	}

	/**
	 * Asigna el movimiento del indice
	 *
	 * @param movimiento simbolo caracteristico (R,L,E ó *)
	 */
	public void setMovimiento(char movimiento) {
		this.movimiento = movimiento;
	}

	/**
	 * Retorna el estado con que se enlaza
	 *
	 * @return estado siguiente
	 */
	public Estado getQj() {
		return qj;
	}

	/**
	 * Asigna el estado con que se enlaza
	 *
	 * @param qj estado siguiente
	 */
	public void setQj(Estado qj) {
		this.qj = qj;
	}

	/**
	 * Retorna la función de transición definida en un String
	 *
	 * @return String con la función
	 */
	@Override
	public String toString() {
		return "," + si + ") = (q" + qj.getQ() + "," + sj + "," + movimiento + ")";
	}
}