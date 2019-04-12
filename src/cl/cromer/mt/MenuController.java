/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import org.w3c.dom.Document;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controlar las acciones cuando una opción es elegido en el menu.
 */
public class MenuController {
	/**
	 * La barra del menu
	 */
	@FXML
	private MenuBar menuBar;

	/**
	 * La opción del menu por individual
	 */
	@FXML
	private MenuItem menuIndiv;

	/**
	 * La opción del enu por individual
	 */
	@FXML
	private MenuItem menuLote;

	/**
	 * Los estados finales que se usa en lote y individual
	 */
	private EstadosFinales estadosFinales;

	/**
	 * Menu opción cargar transiciones
	 *
	 * @throws Exception La excepción
	 */
	@FXML
	protected void cargarTransiciones() throws Exception {
		Scene scene = menuBar.getScene();
		Stage parentStage = (Stage) scene.getWindow();

		ImageView imageView = (ImageView) scene.lookup("#logo");
		if (imageView != null) {
			VBox vbox = (VBox) imageView.getParent();
			vbox.getChildren().remove(imageView);
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir archivo XML");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos XML (*.xml)", "*.xml"));
		File archivo = fileChooser.showOpenDialog(parentStage);
		LeerXML xml = new LeerXML();
		Document documento = xml.leerArchivo(archivo);
		if (documento != null) {
			Maquina maquina = new Maquina(documento);
			TableView temp = (TableView) scene.lookup("#tableView");
			VBox contenido = (VBox) scene.lookup("#contenido");
			if (temp != null) {
				// Remover tabla anterior si existe
				contenido.getChildren().remove(temp);
			}
			else {
				Text text = new Text(0, 0, "TRANSICIONES CARGADAS");
				text.setFill(Color.BLACK);
				text.setFont(Font.font(java.awt.Font.SANS_SERIF, 25));
				text.setId("cargada");
				contenido.getChildren().add(text);

				menuIndiv.setDisable(false);
				menuLote.setDisable(false);
			}
			TableView<TablaData> tableView = FXMLLoader.load(getClass().getResource("/cl/cromer/mt/fxml/tabla.fxml"));
			VBox.setVgrow(tableView, Priority.ALWAYS);
			tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			ObservableList<TablaData> tablaData = FXCollections.observableArrayList();
			Pattern pattern = Pattern.compile("(\\(.*\\)) = (\\(.*\\))");
			for (int i = 0; i < maquina.getAutomata().getEstados().size(); i++) {
				Matcher matcher = pattern.matcher(maquina.getAutomata().getEstados().get(i).toString());
				while (matcher.find()) {
					tablaData.add(new TablaData(matcher.group(1), matcher.group(2)));
				}
			}
			tableView.setEditable(true);
			tableView.setItems(tablaData);

			tableView.getColumns().get(0).setText("(qi,si)");
			tableView.getColumns().get(1).setText("(qj,sj,movimiento)");

			contenido.getChildren().add(tableView);

			// Obtener los estados finales:
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/cl/cromer/mt/fxml/estadosFinales.fxml"));
			Scene nuevaScene = new Scene(fxmlLoader.load(), 250, 250);
			estadosFinales = new EstadosFinales(maquina);
			nuevaScene.setUserData(estadosFinales);
			nuevaScene.getStylesheets().add("/cl/cromer/mt/css/mt.css");
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(parentStage);
			stage.setTitle("Elegir Estados Finales");
			stage.setScene(nuevaScene);
			stage.setMinHeight(250);
			stage.setMinWidth(250);
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/cl/cromer/mt/images/icon.png")));
			final EstadosFinalesController estadosFinalesController = fxmlLoader.getController();
			stage.addEventHandler(WindowEvent.WINDOW_SHOWN, window -> estadosFinalesController.handleWindowShownEvent());
			stage.initStyle(StageStyle.UTILITY);
			stage.setOnCloseRequest(Event::consume);
			stage.show();
		}
		else {
			TableView tableView = (TableView) scene.lookup("#tableView");
			VBox contenido = (VBox) scene.lookup("#contenido");
			if (tableView != null) {
				Text cargada = (Text) scene.lookup("#cargada");
				contenido.getChildren().removeAll(tableView, cargada);
			}
			menuIndiv.setDisable(true);
			menuLote.setDisable(true);
			if (archivo != null) {
				MT.mostrarMensaje("Error", "El archivo " + archivo.getName() + " no es un xml valido");
			}
			else {
				MT.mostrarMensaje("Aviso", "No se ha seleccionado archivo!");
			}
		}
	}

	/**
	 * Menu opción reconocimiento individual
	 *
	 * @throws Exception La excepción
	 */
	@FXML
	@SuppressWarnings("unchecked")
	protected void reconoceIndividual() throws Exception {
		Scene parentScene = menuBar.getScene();
		Stage parentStage = (Stage) parentScene.getWindow();

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/cl/cromer/mt/fxml/individual.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 640, 480);
		scene.setUserData(estadosFinales);
		scene.getStylesheets().add("/cl/cromer/mt/css/mt.css");
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(parentStage);
		stage.setTitle("Reconocimiento individual");
		stage.setMinHeight(480);
		stage.setMinWidth(640);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/cl/cromer/mt/images/icon.png")));
		stage.show();
	}

	/**
	 * Menu opción reconocimiento lote
	 *
	 * @throws Exception La excepción
	 */
	@FXML
	@SuppressWarnings("unchecked")
	protected void reconoceLote() throws Exception {
		Scene parentScene = menuBar.getScene();
		Stage parentStage = (Stage) parentScene.getWindow();

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/cl/cromer/mt/fxml/lote.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 640, 480);
		scene.setUserData(estadosFinales);
		scene.getStylesheets().add("/cl/cromer/mt/css/mt.css");
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(parentStage);
		stage.setTitle("Reconocimiento por lote");
		stage.setMinHeight(480);
		stage.setMinWidth(640);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/cl/cromer/mt/images/icon.png")));
		stage.show();

		TableView<TablaData> tableView = FXMLLoader.load(getClass().getResource("/cl/cromer/mt/fxml/tabla.fxml"));
		VBox.setVgrow(tableView, Priority.ALWAYS);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.setEditable(true);
		TableColumn<TablaData, String> columna1 = (TableColumn<TablaData, String>) tableView.getColumns().get(0);
		columna1.setCellFactory(TextFieldTableCell.forTableColumn());
		columna1.setOnEditCommit(event -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setPrimera(agregarGatos(event.getNewValue()));
			event.getTableView().refresh();
		});

		tableView.getColumns().get(0).setText("Cadena");
		tableView.getColumns().get(0).setEditable(true);
		tableView.getColumns().get(1).setText("Aceptada/Rechazada");
		tableView.getColumns().get(1).setEditable(false);

		VBox contenido = (VBox) scene.lookup("#contenido");
		contenido.getChildren().add(tableView);
	}

	/**
	 * Click en acerca.
	 */
	@FXML
	protected void menuAcerca() {
		ButtonType botonCerrar = new ButtonType("Cerrar", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(Alert.AlertType.NONE, "Maquina de Turning " + MT.version + "\n\nConstruido por:\n\tChristopher Cromer\n\tCarlos Fáundez\n\nIngenier\u00EDa Civil en Inform\u00E1tica\nUniversidad del B\u00EDo B\u00EDo");
		alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
		alert.setGraphic(null);
		alert.getDialogPane().getButtonTypes().add(botonCerrar);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/cl/cromer/mt/images/icon.png")));
		alert.show();
	}

	/**
	 * Agregar un gato al inicio y al final de un string si no existen
	 *
	 * @param string El string a agregar los gatos
	 *
	 * @return String con gatos
	 */
	private String agregarGatos(String string) {
		StringBuilder temp = new StringBuilder(string);
		if (string.charAt(0) != '#') {
			temp.insert(0, "#");
		}
		if (temp.charAt(temp.length() - 1) != '#') {
			temp.insert(temp.length(), "#");
		}
		return temp.toString();
	}
}