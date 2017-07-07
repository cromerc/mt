/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import javafx.beans.property.SimpleStringProperty;

/**
 * Esta clase se usa para organizar las columnas de un TableView
 */
public class TablaData {
	private final SimpleStringProperty primera;
	private final SimpleStringProperty segunda;

	/**
	 * Esta constructor recibe los nombres de las columnas
	 *
	 * @param primera La primera columna
	 * @param segunda La segunda columna
	 */
	public TablaData(SimpleStringProperty primera, SimpleStringProperty segunda) {
		this.primera = primera;
		this.segunda = segunda;
	}

	/**
	 * Esta constructor recibe los nombres de las columnas y los va a convertir a SimpleStringProperty
	 *
	 * @param primera La primera columna
	 * @param segunda La segunda columna
	 */
	public TablaData(String primera, String segunda) {
		this.primera = new SimpleStringProperty(primera);
		this.segunda = new SimpleStringProperty(segunda);
	}

	/**
	 * Developer el nombre de la primera columna
	 *
	 * @return El nombre
	 */
	public String getPrimera() {
		return primera.get();
	}

	/**
	 * Cambiar el nombre de primera columna
	 *
	 * @param primera El nombre de primera columna
	 */
	public void setPrimera(String primera) {
		this.primera.set(primera);
	}

	/**
	 * Devolver la primera string property
	 * @return La primera
	 */
	public SimpleStringProperty primeraProperty() {
		return primera;
	}

	/**
	 * Developer el nombre de la segunda columna
	 * @return El nombre
	 */
	public String getSegunda() {
		return segunda.get();
	}

	/**
	 * Cambiar el nombre de segunda columna
	 *
	 * @param segunda El nombre de segunda columna
	 */
	public void setSegunda(String segunda) {
		this.segunda.set(segunda);
	}

	/**
	 * Devolver la segunda string property
	 * @return La segunda
	 */
	public SimpleStringProperty segundaProperty() {
		return segunda;
	}
}