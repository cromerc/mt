/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IndividualController extends VBox {
	/**
	 * El contenido de la venta
	 */
	@FXML
	private VBox contenido;

	/**
	 * La cadena que ingresa el usuario
	 */
	@FXML
	private TextField cadena;

	/**
	 * La cinta en la pantalla
	 */
	@FXML
	private HBox cinta;

	/**
	 * El boton de acapter cadena
	 */
	@FXML
	private Button aceptar;

	/**
	 * El boton que corre paso a paso
	 */
	@FXML
	private Button paso;

	/**
	 * El estado actual de la maquina
	 */
	@FXML
	private Text estadoActual;

	/**
	 * El estado en el paso anterior
	 */
	@FXML
	private Text estadoPrevio;

	/**
	 * El simbolo que lee la maquina
	 */
	@FXML
	private Text simboloLeido;

	/**
	 * Lo que escribe la maquina
	 */
	@FXML
	private Text simboloEscrito;

	/**
	 * A donde mueve la maquina
	 */
	@FXML
	private Text movimiento;

	/**
	 * La cadena acapatada por el usario
	 */
	private String cadenaAceptada;

	/**
	 * Los estados finales que viene de la ventana anterior
	 */
	private EstadosFinales estadosFinales;

	/**
	 * La maquina turning pasado para correr
	 */
	private Maquina maquina;

	/**
	 * Donde estaba el cabezal anteriormente
	 */
	private int cabezalAnterior;

	/**
	 * La cantidad de cajas que está en la pantalla
	 */
	private int cajas;

	/**
	 * El numero qx del estado anterior
	 */
	private int estadoPrevioi;

	/**
	 * El simbolo que lee antes que la maquina escribe
	 */
	private char simboloAnterior;

	/**
	 * Este metodo se crea una caja para poner un simbolo de la cinta
	 *
	 * @param label El label para encontrar la caja despues
	 * @param texto El simbolo de la cinta
	 *
	 * @return Retorna un StackPane que contiene la caja
	 */
	private static StackPane crearCaja(String label, String texto) {
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(20);
		rectangle.setWidth(20);
		rectangle.setFill(Color.WHITE);
		rectangle.setStroke(Color.BLACK);
		rectangle.setId("caja_" + label);
		Text text = new Text();
		text.setId("simbolo_" + label);
		text.setStroke(Color.BLACK);
		text.setText(texto);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(rectangle, text);
		return stackPane;
	}

	/**
	 * Boton de aceptar cadena
	 */
	@FXML
	protected void aceptarCadena() {
		Scene scene = contenido.getScene();
		estadosFinales = (EstadosFinales) scene.getUserData();
		maquina = estadosFinales.getMaquina();
		cadenaAceptada = cadena.getText();
		StringBuilder temp = new StringBuilder(cadenaAceptada);
		temp.insert(0, "#");
		temp.insert(cadenaAceptada.length() + 1, "#");
		cadenaAceptada = temp.toString();
		for (int i = 0; i < cadenaAceptada.length(); i++) {
			cinta.getChildren().add(crearCaja(String.valueOf(i), String.valueOf(cadenaAceptada.charAt(i))));
			cajas = i + 1;
		}
		Rectangle rectangle = (Rectangle) scene.lookup("#caja_1");
		rectangle.setFill(Color.BLUE);
		Text simbolo = (Text) scene.lookup("#simbolo_1");
		simbolo.setStroke(Color.WHITE);
		cadena.setText("");
		cadena.setDisable(true);
		aceptar.setDisable(true);
		paso.setDisable(false);
		cabezalAnterior = 1;
		estadoPrevioi = 0;

		estadoActual.setText("Estado actual: q" + maquina.getEstadoActual().getQ());
		estadoPrevio.setText("Estado previo: q" + estadoPrevioi);
		simboloLeido.setText("Simbolo leido: #");
		simboloEscrito.setText("Simbolo escrito: #");
		movimiento.setText("Movimiento: #");
	}

	/**
	 * Boton de cerrar
	 */
	@FXML
	protected void paso() {
		Scene scene = contenido.getScene();

		int resultado = maquina.comprobarCadenaS2S(new StringBuilder(cadenaAceptada), estadosFinales.getEstadosElegidos().stream().mapToInt(i -> i).toArray());

		if (resultado == 1) {
			MT.mostrarMensaje("Resultado", "La cadena fue aceptada!");
			cadena.setDisable(false);
			aceptar.setDisable(false);
			paso.setDisable(true);
			maquina.reset();
		}
		else if (resultado == -1) {
			MT.mostrarMensaje("Resultado", "La cadena fue rechazada!");
			cadena.setDisable(false);
			aceptar.setDisable(false);
			paso.setDisable(true);
			maquina.reset();
		}
		else {
			if (maquina.getCintaAnterior().length() > cajas) {
				for (int i = cajas; i < maquina.getCintaAnterior().length(); i++) {
					cinta.getChildren().add(crearCaja(String.valueOf(i), String.valueOf(maquina.getCintaAnterior().charAt(i))));
				}
			}

			for (int i = 0; i < maquina.getCintaAnterior().length(); i++) {
				Text simbolo = (Text) scene.lookup("#simbolo_" + i);
				if (i == maquina.getCabezal()) {
					simboloAnterior = simbolo.getText().charAt(0);
				}
				simbolo.setText(String.valueOf(maquina.getCintaAnterior().charAt(i)));
			}

			// Undo cabezel anterior
			Rectangle rectangle = (Rectangle) scene.lookup("#caja_" + cabezalAnterior);
			rectangle.setFill(Color.WHITE);
			Text simbolo = (Text) scene.lookup("#simbolo_" + cabezalAnterior);
			simbolo.setStroke(Color.BLACK);

			estadoActual.setText("Estado actual: q" + maquina.getEstadoActual().getQ());
			estadoPrevio.setText("Estado previo: q" + estadoPrevioi);
			simboloLeido.setText("Simbolo leido: " + simboloAnterior);
			simboloEscrito.setText("Simbolo escrito: " + simbolo.getText());
			movimiento.setText("Movimiento: " + maquina.getEnlaceActual().getMovimiento());

			// Cabezel
			rectangle = (Rectangle) scene.lookup("#caja_" + maquina.getCabezal());
			rectangle.setFill(Color.BLUE);
			simbolo = (Text) scene.lookup("#simbolo_" + maquina.getCabezal());
			simbolo.setStroke(Color.WHITE);

			cabezalAnterior = maquina.getCabezal();
			estadoPrevioi = maquina.getEstadoActual().getQ();
		}
	}

	/**
	 * Boton de cerrar
	 */
	@FXML
	protected void cerrar() {
		Scene scene = contenido.getScene();
		Stage stage = (Stage) scene.getWindow();
		stage.close();
	}
}
