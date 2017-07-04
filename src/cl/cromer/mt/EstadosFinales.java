/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import java.util.ArrayList;

public class EstadosFinales {
	private ArrayList<Integer> estadosExistents;

	private ArrayList<Integer> estadosElegidos;

	public EstadosFinales(ArrayList<Integer> estadosExistents) {
		this.estadosExistents = estadosExistents;
	}

	public ArrayList<Integer> getEstadosExistents() {
		return estadosExistents;
	}

	public void setEstadosExistents(ArrayList<Integer> estadosExistents) {
		this.estadosExistents = estadosExistents;
	}

	public ArrayList<Integer> getEstadosElegidos() {
		return estadosElegidos;
	}

	public void setEstadosElegidos(ArrayList<Integer> estadosElegidos) {
		this.estadosElegidos = estadosElegidos;
	}
}
