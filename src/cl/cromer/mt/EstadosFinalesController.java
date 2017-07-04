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

/**
 * Controlar las acciones cuando el usuario elige los estados finales
 */
public class EstadosFinalesController {
	@FXML
	private VBox contenido;

	@FXML
	public void elegir() {
		Stage stage = (Stage) contenido.getScene().getWindow();
		stage.close();
	}

	/**
	 * Este metodo es para el evento de windowshown.
	 */
	public void handleWindowShownEvent() {
		Stage stage = (Stage) contenido.getScene().getWindow();
		EstadosFinales estadosFinales = (EstadosFinales) stage.getScene().getUserData();

		for (int i = 0; i < estadosFinales.getEstadosExistents().size(); i++) {
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.CENTER);
			hBox.prefWidthProperty().bind(contenido.prefWidthProperty());
			hBox.prefHeightProperty().bind(contenido.prefHeightProperty());
			hBox.prefWidthProperty().bind(contenido.widthProperty());
			hBox.prefHeightProperty().bind(contenido.heightProperty());
			CheckBox checkBox = new CheckBox("q" + estadosFinales.getEstadosExistents().get(i));
			hBox.getChildren().add(checkBox);
			contenido.getChildren().add(hBox);
		}
	}
}
