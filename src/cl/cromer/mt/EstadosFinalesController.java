/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Controlar las acciones cuando el usuario elige los estados finales
 */
public class EstadosFinalesController {
	@FXML
	private VBox contenido;
	private EstadosFinales estadosFinales;

	/**
	 * El metodo llamado cuando el usuario hace click en elegir
	 */
	@FXML
	public void elegir() {
		ArrayList<Integer> elegidos = new ArrayList<>();
		for (int i = 0; i < estadosFinales.getEstadosExistentes().size(); i++) {
			CheckBox checkBox = (CheckBox) contenido.getScene().lookup("#q" + estadosFinales.getEstadosExistentes().get(i));
			if (checkBox.isSelected()) {
				elegidos.add(i);
			}
		}

		if (elegidos.size() > 0) {
			estadosFinales.setEstadosElegidos(elegidos);
			Stage stage = (Stage) contenido.getScene().getWindow();
			stage.close();
		}
		else {
			MT.mostrarMensaje("Elegir", "Usted tiene que elegir uno o mas estados finales!");
		}
	}

	/**
	 * Este metodo es para el evento de windowshown
	 */
	void handleWindowShownEvent() {
		Stage stage = (Stage) contenido.getScene().getWindow();
		estadosFinales = (EstadosFinales) stage.getScene().getUserData();

		for (int i = 0; i < estadosFinales.getEstadosExistentes().size(); i++) {
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.CENTER);
			hBox.prefWidthProperty().bind(contenido.prefWidthProperty());
			hBox.prefHeightProperty().bind(contenido.prefHeightProperty());
			hBox.prefWidthProperty().bind(contenido.widthProperty());
			hBox.prefHeightProperty().bind(contenido.heightProperty());
			CheckBox checkBox = new CheckBox("q" + estadosFinales.getEstadosExistentes().get(i));
			checkBox.setId("q" + estadosFinales.getEstadosExistentes().get(i));
			hBox.getChildren().add(checkBox);
			contenido.getChildren().add(hBox);
		}
	}
}