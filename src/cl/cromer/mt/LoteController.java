/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlar las acciones de reconocimiento por lote
 */
public class LoteController extends VBox {
	@FXML
	private VBox vboxLote;

	@FXML
	private TextField cadena;

	private ObservableList<TablaData> tablaData = FXCollections.observableArrayList();

	/**
	 * Boton de run MT
	 * @throws Exception La excepción
	 */
	@FXML
	protected void runLote() throws Exception {
		Scene scene = vboxLote.getScene();
		EstadosFinales estadosFinales = (EstadosFinales) scene.getUserData();
		Maquina maquina = estadosFinales.getMaquina();
		for (TablaData fila : tablaData) {
			boolean exito = maquina.comprobarCadena(new StringBuilder(fila.getPrimer()), estadosFinales.getEstadosElegidos().stream().mapToInt(i -> i).toArray());
			if (exito) {
				fila.setSegundo("Aceptada");
			}
			else {
				fila.setSegundo("Rechazada");
			}
			maquina.reset();
		}
	}

	/**
	 * Boton de agregar cadena
	 */
	@FXML
	protected void agregarCadena() {
		tablaData.add(new TablaData(cadena.getText(), ""));
		Scene scene = vboxLote.getScene();
		@SuppressWarnings("unchecked")
		TableView<TablaData> tableView = (TableView<TablaData>) scene.lookup("#tableView");
		tableView.setEditable(true);
		tableView.setItems(tablaData);
		cadena.setText("");
	}

	/**
	 * Boton de cerrar
	 */
	@FXML
	protected void cerrar() {
		Scene scene = vboxLote.getScene();
		Stage stage = (Stage) scene.getWindow();
		stage.close();
	}
}
