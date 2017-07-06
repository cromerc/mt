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
 * Clase que contiene método que guarda y ordena cada transición del archivo XML ya validado
 */

public class Automata {
	private ArrayList<Estado> estados;
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
			char move = document.getElementsByTagName("movimiento").item(i).getTextContent().charAt(0);
			if (move == 'E' || move == 'R' || move == 'L' || move == '*') {
				int qi = Integer.parseInt(document.getElementsByTagName("qi").item(i).getTextContent());
				int qj = Integer.parseInt(document.getElementsByTagName("qj").item(i).getTextContent());
				char si = document.getElementsByTagName("si").item(i).getTextContent().charAt(0);
				char sj = document.getElementsByTagName("sj").item(i).getTextContent().charAt(0);
				if (estados.size() <= qi) {
					estados.add(qi, new Estado(qi));
				}
				if (estados.size() <= qj) {
					estados.add(qj, new Estado(qj));
				}
				estados.get(qi).createLink(si, estados.get(qj), sj, move);
			}
			else {
				System.out.println("Movimiento invalido de cinta");
				System.exit(1);
			}
		}
		estadosExistentes = new ArrayList<>();
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
	 * @param estadosExistentes
	 */
	public void setEstadosExistentes(ArrayList<Integer> estadosExistentes) {
		this.estadosExistentes = estadosExistentes;
	}
}
