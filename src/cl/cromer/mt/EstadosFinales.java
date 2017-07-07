/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import java.util.ArrayList;

/**
 * Esta clase es un objeto para pasar entre los stage.
 */
public class EstadosFinales {
	/**
	 * Los estados que existen
	 */
	private ArrayList<Integer> estadosExistentes;

	/**
	 * Los estados que eligó el usuario
	 */
	private ArrayList<Integer> estadosElegidos;

	/**
	 * La maquina turning con sus transiciones, estados y enlaces
	 */
	private Maquina maquina;

	/**
	 * Constructor de la clase que recibe una maquina de turning
	 *
	 * @param maquina La maquina de turning a pasar entre stages
	 */
	public EstadosFinales(Maquina maquina) {
		this.maquina = maquina;
		this.estadosExistentes = maquina.getAutomata().getEstadosExistentes();
	}

	/**
	 * Devolver los estados que existen
	 *
	 * @return Los estados que existen
	 */
	public ArrayList<Integer> getEstadosExistentes() {
		return estadosExistentes;
	}

	/**
	 * Cambiar los estados que existen
	 *
	 * @param estadosExistentes Los estados nuevos
	 */
	public void setEstadosExistentes(ArrayList<Integer> estadosExistentes) {
		this.estadosExistentes = estadosExistentes;
	}

	/**
	 * Devolver los estados elegidos por el usuario
	 * @return Los estados elegidos
	 */
	public ArrayList<Integer> getEstadosElegidos() {
		return estadosElegidos;
	}

	/**
	 * Cambiar los estados elegidos por el usuario
	 *
	 * @param estadosElegidos Los estados elegidods nuevos
	 */
	public void setEstadosElegidos(ArrayList<Integer> estadosElegidos) {
		this.estadosElegidos = estadosElegidos;
	}

	/**
	 * Devolver la maquina de turning para usar
	 * @return La maquina de turning
	 */
	public Maquina getMaquina() {
		return maquina;
	}

	/**
	 * Cambiar la maquina de turning del objeto
	 * @param maquina La maquina
	 */
	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}
}