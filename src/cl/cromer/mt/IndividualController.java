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

/**
 * Controlar las acciones de reconocimiento individual
 */
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
	 * La descripcion de que hace la maquina
	 */
	@FXML
	private Text descripcion;

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
	private int estadoPrevio;

	/**
	 * El simbolo que lee antes que la maquina escribe
	 */
	private char simboloAnterior;

	/**
	 * Si debe mover o si debe mostrar lo que lee y escribe
	 */
	private boolean mover;

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
		maquina.reset();
		cadenaAceptada = cadena.getText();
		StringBuilder temp = new StringBuilder(cadenaAceptada);
		temp.insert(0, "#");
		temp.insert(cadenaAceptada.length() + 1, "#");
		cadenaAceptada = temp.toString();
		cinta.getChildren().clear();
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
		estadoPrevio = 0;
		mover = false;
		simboloAnterior = '\0';

		descripcion.setText("Inicio en estado q" + maquina.getEstadoActual().getQ());
	}

	/**
	 * Boton de cerrar
	 */
	@FXML
	protected void paso() {
		Scene scene = contenido.getScene();

		int resultado = 0;
		if (!mover) {
			resultado = maquina.comprobarCadenaS2S(new StringBuilder(cadenaAceptada), estadosFinales.getEstadosElegidos().stream().mapToInt(i -> i).toArray());
		}

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
				if (i == maquina.getCabezal() && (mover || simboloAnterior == '\0')) {
					simboloAnterior = simbolo.getText().charAt(0);
				}
				simbolo.setText(String.valueOf(maquina.getCintaAnterior().charAt(i)));
			}

			if (mover) {
				String move;
				switch (maquina.getEnlaceActual().getMovimiento()) {
					case 'L': {
						move = "se movio a la izquierda";
						break;
					}
					case 'R': {
						move = "se movio a la derecha";
						break;
					}
					default: {
						move = "no se movio";
						break;
					}
				}
				descripcion.setText("La cinta " + move + "\nCambia del estado q" + estadoPrevio + " al estado q" + maquina.getEstadoActual().getQ());

				// Undo cabezel anterior
				Rectangle rectangle = (Rectangle) scene.lookup("#caja_" + cabezalAnterior);
				rectangle.setFill(Color.WHITE);
				Text simbolo = (Text) scene.lookup("#simbolo_" + cabezalAnterior);
				simbolo.setStroke(Color.BLACK);

				// Cabezel
				rectangle = (Rectangle) scene.lookup("#caja_" + maquina.getCabezal());
				rectangle.setFill(Color.BLUE);
				simbolo = (Text) scene.lookup("#simbolo_" + maquina.getCabezal());
				simbolo.setStroke(Color.WHITE);

				cabezalAnterior = maquina.getCabezal();
				estadoPrevio = maquina.getEstadoActual().getQ();
				mover = false;
			}
			else {
				Text simbolo = (Text) scene.lookup("#simbolo_" + cabezalAnterior);
				descripcion.setText("Se leyo el simbolo \"" + simboloAnterior + "\" y se escribio el simbolo \"" + simbolo.getText() + "\"");
				mover = true;
			}
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
