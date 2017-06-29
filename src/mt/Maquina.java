/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package mt;

import org.w3c.dom.Document;

class Maquina {
	private final Automata maquina;

	Maquina(Document document) {
		maquina = new Automata(document);
	}

	Automata getMaquina() {
		return maquina;
	}

	boolean comprobarCadena(StringBuilder cinta, int estadoFinal) {
		Estado qi = maquina.getEstados().get(0);
		int cabezal = 0;
		int i;
		for (i = 0; i < qi.getEnlaces().size(); i++) {
			if (qi.getEnlaces().get(i).getSi() == cinta.charAt(cabezal)) {
				cinta.setCharAt(cabezal, qi.getEnlaces().get(i).getSj());
				switch (qi.getEnlaces().get(i).getMovimiento()) {
					case 'L': {
						cabezal--;
						if (cabezal == (-1)) {
							cinta.insert(0, "#");
							cabezal++;
						}
						break;
					}
					case 'R': {
						cabezal++;
						if (cabezal == cinta.length()) {
							cinta.insert(cabezal, "#");
						}
						break;
					}
					default: {/*Se mantiene*/}
				}
				qi = qi.getEnlaces().get(i).getQj();
				i = -1;
			}
		}
		return (qi.getQ() == estadoFinal);
	}
}

