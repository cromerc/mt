/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import org.w3c.dom.Document;

import java.util.ArrayList;

class Automata {
	private ArrayList<Estado> estados;
	private ArrayList<Integer> estados_existentes;

	Automata(Document document) {
		setEstados(new ArrayList<>());
		for (int i = 0; i < document.getElementsByTagName("transicion").getLength(); i++) {
			char move = document.getElementsByTagName("movimiento").item(i).getTextContent().charAt(0);
			if (move == 'E' || move == 'R' || move == 'L' || move == '*') {
				int qi = Integer.parseInt(document.getElementsByTagName("qi").item(i).getTextContent());
				int qj = Integer.parseInt(document.getElementsByTagName("qj").item(i).getTextContent());
				char si = document.getElementsByTagName("si").item(i).getTextContent().charAt(0);
				char sj = document.getElementsByTagName("sj").item(i).getTextContent().charAt(0);
		        /*if(estados.isEmpty() && qi != qj){
		            estados.add(qi, new Estado(qi));
                    estados.add(qj, new Estado(qj));
                }
                else if(estados.isEmpty() && qi == qj) {
                    estados.add(qi, new Estado(qi));
                }*/
				if (estados.size() <= qi) {
					estados.add(qi, new Estado(qi));
				}
				if (estados.size() <= qj) {
					estados.add(qj, new Estado(qj));
				}
				if (!estados.get(qi).createLink(si, estados.get(qj), sj, move)) {
					if (qi == qj) {
						System.out.println("Recursivo");
					}
					else {
						System.out.println("En" + qi + " a " + qj + "Transicion para " + si + " ya esta creada");
					}
				}
			}
			else {
				System.out.println("Movimiento invalido de cinta");
				System.exit(1);
			}
		}
		estados_existentes = new ArrayList<Integer>();
		for(int i=0 ; i < getEstados().size() ; i++){
			if(getEstados().get(i) != null) estados_existentes.add(getEstados().get(i).getQ());
		}
	}

	ArrayList<Estado> getEstados() {
		return estados;
	}

	private void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}

	public ArrayList<Integer> getEstados_existentes() {
		return estados_existentes;
	}

	public void setEstados_existentes(ArrayList<Integer> estados_existentes) {
		this.estados_existentes = estados_existentes;
	}
}
