/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import org.w3c.dom.Document;

public class Maquina {
	private final Automata maquina;
	private Estado estadoActual;
	private Enlace enlaceActual;
	private String cintaAnterior;
	private int cabezal;

	public Maquina(Document document) {
		maquina = new Automata(document);
		reset();
	}

	public Automata getMaquina() {
		return maquina;
	}

	public Estado getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(Estado estadoActual) {
		this.estadoActual = estadoActual;
	}

	public Enlace getEnlaceActual() {
		return enlaceActual;
	}

	public void setEnlaceActual(Enlace enlaceActual) {
		this.enlaceActual = enlaceActual;
	}

	public String getCintaAnterior() {
		return cintaAnterior;
	}

	public void setCintaAnterior(String cintaAnterior) {
		this.cintaAnterior = cintaAnterior;
	}

	public int getCabezal() {
		return cabezal;
	}

	public void setCabezal(int cabezal) {
		this.cabezal = cabezal;
	}

	public void reset() {
		setEstadoActual(maquina.getEstados().get(0));
		setEnlaceActual(null);
		setCintaAnterior("");
		setCabezal(0);
	}

	public boolean comprobarCadena(StringBuilder cinta, int[] estadoFinal) {
		//estadoActual = maquina.getEstados().get(0);
		int i;
		for (i = 0; i < getEstadoActual().getEnlaces().size(); i++) {
			if (getEstadoActual().getEnlaces().get(i).getSi() == cinta.charAt(getCabezal())) {
				setEnlaceActual(getEstadoActual().getEnlaces().get(i));
				cinta.setCharAt(getCabezal(), getEnlaceActual().getSj());
				switch (getEnlaceActual().getMovimiento()) {
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

	public boolean comprobarCadenaS2S(StringBuilder cinta, int[] estadoFinal) {
		setCintaAnterior(cinta.toString());
		int i;
		for (i = 0; i < getEstadoActual().getEnlaces().size(); i++) {
			if (getEstadoActual().getEnlaces().get(i).getSi() == cinta.charAt(getCabezal())) {
				setEnlaceActual(getEstadoActual().getEnlaces().get(i));
				setEstadoActual(getEnlaceActual().getQj());
				cinta.setCharAt(getCabezal(), getEnlaceActual().getSj());
				switch (getEnlaceActual().getMovimiento()) {
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
}

