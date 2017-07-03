/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package mt;

import org.w3c.dom.Document;

class Maquina implements Cloneable {
	private Automata maquina;
	private Estado estadoactual;
	private Enlace enlaceactual;
	private String cintaanterior;
	private int cabezal;

	Maquina(Document document) {
		maquina = new Automata(document);
		reset();
	}

	@Override
	public Maquina clone() {
		try {
			final Maquina result = (Maquina) super.clone();
			result.maquina = this.maquina;
			result.estadoactual = this.estadoactual;
			result.enlaceactual = this.enlaceactual;
			result.cintaanterior = this.cintaanterior;
			return result;
		}
		catch (final CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	Automata getMaquina() {return maquina;}

	public Estado getEstadoactual() {return estadoactual;}

	public Enlace getEnlaceactual() {return enlaceactual;}

	public String getCintaanterior() {return cintaanterior;}

	public int getCabezal() {return cabezal;}

	public void reset(){
		estadoactual = maquina.getEstados().get(0);
		enlaceactual = null;
		cintaanterior = "";
		cabezal = 0;
	}

	boolean comprobarCadena(StringBuilder cinta, int[] estadoFinal) {
		//estadoactual = maquina.getEstados().get(0);
		int i;
		for (i = 0; i < estadoactual.getEnlaces().size(); i++) {
			if (estadoactual.getEnlaces().get(i).getSi() == cinta.charAt(cabezal)) {
				enlaceactual = estadoactual.getEnlaces().get(i);
				cinta.setCharAt(cabezal, enlaceactual.getSj());
				switch (enlaceactual.getMovimiento()) {
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
				estadoactual = enlaceactual.getQj();
				i = -1;
			}
		}
		for(i=0;i<estadoFinal.length;i++){
			if(estadoactual.getQ() == estadoFinal[i]){
				reset();
				return true;
			}
		}
		reset();
		return false;
	}

	boolean comprobarCadenaS2S(StringBuilder cinta, int[] estadoFinal){
		cintaanterior = cinta.toString();
		int i;
		for (i = 0; i < estadoactual.getEnlaces().size(); i++) {
			if (estadoactual.getEnlaces().get(i).getSi() == cinta.charAt(cabezal)) {
				enlaceactual = estadoactual.getEnlaces().get(i);
				estadoactual = enlaceactual.getQj();
				cinta.setCharAt(cabezal, enlaceactual.getSj());
				switch (enlaceactual.getMovimiento()) {
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
				return true;
			}
		}
		for(i=0;i<estadoFinal.length;i++){
			if(estadoactual.getQ() == estadoFinal[i]){
				enlaceactual = null; // Indicar que no hay más transiciones
				return true;
			}
		}
		return false;
	}
}

