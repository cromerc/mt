/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

public class Enlace {
	private char si;
	private char sj;
	private char movimiento;
	private Estado qj;

	public Enlace(char si, Estado qj, char sj, char move) {
		setMovimiento(move);
		setSj(sj);
		setQj(qj);
		setSi(si);
	}

	public char getSi() {
		return this.si;
	}

	private void setSi(char si) {
		this.si = si;
	}

	public char getSj() {
		return this.sj;
	}

	public void setSj(char sj) {
		this.sj = sj;
	}

	public char getMovimiento() {
		return this.movimiento;
	}

	public void setMovimiento(char movimiento) {
		this.movimiento = movimiento;
	}

	public Estado getQj() {
		return qj;
	}

	public void setQj(Estado qj) {
		this.qj = qj;
	}

	@Override
	public String toString() {
		return "," + si + ") = (q" + qj.getQ() + "," + sj + "," + movimiento + ")";
	}
}