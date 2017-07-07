/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Maquina de Turing
 *
 * @author Christopher Cromer
 * @author Carlos Fáundez
 * @version 1.0.0
 */
public class MT extends Application {
	static public final String version = "1.0.0";

	/**
	 * El metodo principal del programa
	 *
	 * @param args Los argumentos pasado al programa
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Se muestra un mensaje con titulo
	 *
	 * @param titulo El titulo de la ventana
	 * @param mensaje El mensaje
	 */
	static void mostrarMensaje(String titulo, String mensaje) {
		ButtonType botonCerrar = new ButtonType("Cerrar", ButtonBar.ButtonData.OK_DONE);
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle(titulo);
		dialog.setContentText(mensaje);
		dialog.getDialogPane().getButtonTypes().add(botonCerrar);
		dialog.getDialogPane().getScene().getWindow().sizeToScene();
		dialog.show();
	}

	/**
	 * Metodo de JavaFX llamada para generar el interfaz grafico
	 * @param primaryStage La ventana principal
	 * @throws Exception La excepción
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/cl/cromer/mt/fxml/mt.fxml"));
		primaryStage.setTitle("Maquina de Turing");

		Scene scene = new Scene(root, 640, 480);
		scene.getStylesheets().add("/cl/cromer/mt/css/mt.css");
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(480);
		primaryStage.setMinWidth(640);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/cl/cromer/mt/images/icon.png")));
		primaryStage.show();
	}
}