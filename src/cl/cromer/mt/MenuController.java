/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Document;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controlar las acciones cuando una opción es elegido en el menu.
 */
public class MenuController {
	private Maquina maquina = null;

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem menuIndiv;

	@FXML
	private MenuItem menuLote;

	/**
	 * Menu opción cargar transiciones
	 *
	 * @throws Exception La excepción
	 */
	@FXML
	protected void cargarTransiciones() throws Exception {
		Scene scene = menuBar.getScene();
		Stage stage = (Stage) scene.getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir archivo XML");
		File archivo = fileChooser.showOpenDialog(stage);
		LeerXML xml = new LeerXML();
		Document documento = xml.leerArchivo(archivo);
		if (documento != null) {
			maquina = new Maquina(documento);
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
				contenido.getChildren().add(text);

				menuIndiv.setDisable(false);
				menuLote.setDisable(false);
			}
			TableView<TablaData> tableView = FXMLLoader.load(getClass().getResource("tabla.fxml"));
			VBox.setVgrow(tableView, Priority.ALWAYS);
			tableView.skinProperty().addListener((source, oldWidth, newWidth) -> {
				final TableHeaderRow header = (TableHeaderRow) tableView.lookup("TableHeaderRow");
				header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
			});
			tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			ObservableList<TablaData> tablaData = FXCollections.observableArrayList();
			Pattern pattern = Pattern.compile("(\\(.*\\)) = (\\(.*\\))");
			for (int i = 0; i < maquina.getMaquina().getEstados().size(); i++) {
				Matcher matcher = pattern.matcher(maquina.getMaquina().getEstados().get(i).toString());
				while (matcher.find()) {
					tablaData.add(new TablaData(matcher.group(1), matcher.group(2)));
				}
			}

			tableView.setEditable(true);
			tableView.setItems(tablaData);

			tableView.getColumns().get(0).setText("(qi,si)");
			tableView.getColumns().get(1).setText("(qj,sj,movimiento)");

			contenido.getChildren().add(tableView);
		}else{
			MT.mostrarMensaje("Error","El archivo "+ archivo.getName()+ " no es un xml valido");
		}
	}

	/**
	 * Menu opción reconocimiento individual
	 */
	@FXML
	protected void reconoceIndividual() {

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
		fxmlLoader.setLocation(getClass().getResource("lote.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 640, 480);
		scene.setUserData(maquina);
		scene.getStylesheets().add("/cl/cromer/mt/mt.css");
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(parentStage);
		stage.setTitle("Reconocimiento por lotes");
		stage.setMinHeight(480);
		stage.setMinWidth(640);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/cl/cromer/mt/images/icon.png")));
		stage.show();

		TableView<TablaData> tableView = FXMLLoader.load(getClass().getResource("tabla.fxml"));
		VBox.setVgrow(tableView, Priority.ALWAYS);
		tableView.skinProperty().addListener((source, oldWidth, newWidth) -> {
			final TableHeaderRow header = (TableHeaderRow) tableView.lookup("TableHeaderRow");
			header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
		});
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<TablaData, String> columna1 = (TableColumn<TablaData, String>) tableView.getColumns().get(0);
		columna1.setCellFactory(TextFieldTableCell.forTableColumn());
		columna1.setOnEditCommit(
				columna -> columna.getTableView().getItems().get(columna.getTablePosition().getRow()).setPrimer(columna.getNewValue())
		);

		tableView.getColumns().get(0).setText("Cadena");
		tableView.getColumns().get(0).setEditable(true);
		tableView.getColumns().get(1).setText("Aceptada/Rechazada");
		tableView.getColumns().get(1).setEditable(false);

		VBox contenido = (VBox) scene.lookup("#contenidoLote");
		contenido.getChildren().add(tableView);
	}
}