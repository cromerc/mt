/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */
package cl.cromer.mt;

import java.util.ArrayList;

public class Estado {
	private final int q;
	private final ArrayList<Enlace> enlaces;

	Estado(int q) {
		this.q = q;
		enlaces = new ArrayList<>();
	}

	ArrayList<Enlace> getEnlaces() {
		return enlaces;
	}

	int getQ() {
		return q;
	}

	boolean createLink(char si, Estado qj, char sj, char move) {
		if (enlaces.isEmpty()) {
			enlaces.add(new Enlace(si, qj, sj, move));
		}
		for (Enlace aLink : enlaces) {
			if (aLink.getSi() == si) {
				return false;
			}
		}
		enlaces.add(new Enlace(si, qj, sj, move));
		return true;
	}

	@Override
	public String toString() {
		StringBuilder _return = new StringBuilder();
		for (Enlace enlace : enlaces) {
			_return.append("(q").append(q).append(enlace).append("\n");
		}
		return _return.toString();
	}
}