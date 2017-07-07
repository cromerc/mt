/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */
package cl.cromer.mt;

import java.util.ArrayList;

/**
 * Clase que almanecerá un estado con su subindices y sus enlaces adyacentes
 */
public class Estado {
	private final int q;
	private final ArrayList<Enlace> enlaces;

	/**
	 * Contructor de la clase
	 *
	 * @param q Subindice del estado
	 */
	public Estado(int q) {
		this.q = q;
		enlaces = new ArrayList<>();
	}

	/**
	 * Retorna una lista con los enlaces de un estado
	 *
	 * @return ArrayList de Enlaces
	 */
	public ArrayList<Enlace> getEnlaces() {
		return enlaces;
	}

	/**
	 * Retorna el subindice asociado al estado
	 *
	 * @return Subindice
	 */
	public int getQ() {
		return q;
	}

	/**
	 * Crea un enlace entre dos estados ó a si mismo
	 *
	 * @param si Simbolo a analizar en la cadena
	 * @param qj Estado adyacente
	 * @param sj Simbolo que escribe en la cadena
	 * @param mover Movimiento del cabezal (indice de la cadena)
	 */
	public void crearEnlace(char si, Estado qj, char sj, char mover) {
		if (enlaces.isEmpty()) {
			enlaces.add(new Enlace(si, qj, sj, mover));
		}
		for (Enlace enlace : enlaces) {
			if (enlace.getSi() == si) {
				return;
			}
		}
		enlaces.add(new Enlace(si, qj, sj, mover));
	}

	/**
	 * Retorna un String con la/s función/es de transición asociado a un estado
	 *
	 * @return String con la/s función/es
	 */
	@Override
	public String toString() {
		StringBuilder _return = new StringBuilder();
		for (Enlace enlace : enlaces) {
			_return.append("(q").append(q).append(enlace).append("\n");
		}
		return _return.toString();
	}
}