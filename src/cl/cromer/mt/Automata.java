/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Clase que contiene métodos que guarda y ordena cada transición del archivo XML ya validado
 */
public class Automata {
	/**
	 * Los estados y sus enlaces
	 */
	private ArrayList<Estado> estados;

	/**
	 * Los estados que existen
	 */
	private ArrayList<Integer> estadosExistentes;

	/**
	 * Constructor de la clase Automata, organiza las etiquetas del XML en un array de estados
	 * y filtra los subindices de cada estado obtenido
	 *
	 * @param document Document del XML
	 */
	public Automata(Document document) {
		setEstados(new ArrayList<>());
		for (int i = 0; i < document.getElementsByTagName("transicion").getLength(); i++) {
			char mover = document.getElementsByTagName("movimiento").item(i).getTextContent().charAt(0);
			if (mover == 'E' || mover == 'R' || mover == 'L' || mover == '*' || mover == 'S') {
				int qi = Integer.parseInt(document.getElementsByTagName("qi").item(i).getTextContent());
				int qj = Integer.parseInt(document.getElementsByTagName("qj").item(i).getTextContent());
				char si = document.getElementsByTagName("si").item(i).getTextContent().charAt(0);
				char sj = document.getElementsByTagName("sj").item(i).getTextContent().charAt(0);
				if (getEstados().size() <= qi) {
					getEstados().add(qi, new Estado(qi));
				}
				if (getEstados().size() <= qj) {
					getEstados().add(qj, new Estado(qj));
				}
				getEstados().get(qi).crearEnlace(si, getEstados().get(qj), sj, mover);
			}
			else {
				System.out.println("Movimiento invalido de cinta");
			}
		}
		setEstadosExistentes(new ArrayList<>());
		for (int i = 0; i < getEstados().size(); i++) {
			if (getEstados().get(i) != null) {
				getEstadosExistentes().add(getEstados().get(i).getQ());
			}
		}
	}

	/**
	 * Retorna un lista de estados
	 *
	 * @return ArrayList de Estados
	 */
	public ArrayList<Estado> getEstados() {
		return estados;
	}

	/**
	 * Asigna una lista de estados
	 *
	 * @param estados Estados ya creados
	 */
	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}

	/**
	 * Retorna una lista de números que serán los subindices de los estados existentes
	 *
	 * @return estadosExistentes Lista de subindices
	 */
	public ArrayList<Integer> getEstadosExistentes() {
		return estadosExistentes;
	}

	/**
	 * Asigna una lista de estados existentes
	 *
	 * @param estadosExistentes Los estados a guardar
	 */
	public void setEstadosExistentes(ArrayList<Integer> estadosExistentes) {
		this.estadosExistentes = estadosExistentes;
	}
}
