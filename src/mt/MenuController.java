/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package mt;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controlar las acciones cuando una opción es elegido en el menu.
 */
public class MenuController extends VBox implements Initializable {
	Maquina maquina = null;

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem menuIndiv;

	@FXML
	private MenuItem menuLote;

	/**
	 * Inicialicar el menu con el idioma.
	 *
	 * @param location Tiene URL de FXML en uso.
	 * @param resourceBundle Tiene recursos qu se pasa al controller.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resourceBundle) {
		// No es necesario poner algo aqui porque el programa mt no se usa los resourceBundles
	}

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
			TableView<ListaCargada> tableView = FXMLLoader.load(getClass().getResource("transiciones.fxml"));
			VBox.setVgrow(tableView, Priority.ALWAYS);
			tableView.skinProperty().addListener((source, oldWidth, newWidth) -> {
				final TableHeaderRow header = (TableHeaderRow) tableView.lookup("TableHeaderRow");
				header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
			});
			tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			ObservableList<ListaCargada> listaCargadas = FXCollections.observableArrayList();
			Pattern pattern = Pattern.compile("(\\(.*\\)) = (\\(.*\\))");
			for (int i = 0; i < maquina.getMaquina().getEstados().size(); i++) {
				Matcher matcher = pattern.matcher(maquina.getMaquina().getEstados().get(i).toString());
				while (matcher.find()) {
					listaCargadas.add(new ListaCargada(matcher.group(1), matcher.group(2)));
				}
			}

			tableView.setEditable(true);
			tableView.setItems(listaCargadas);

			contenido.getChildren().add(tableView);

			/*if (maquina.comprobarCadena(new StringBuilder("000111###"), 5)) {
				MT.mostrarMensaje("Resultado", "Reconce");
			}
			else {
				MT.mostrarMensaje("Resultado", " No reconce");
			}*/
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
	 */
	@FXML
	protected void reconoceLote() throws Exception {
		Scene parentScene = menuBar.getScene();
		Stage parentStage = (Stage) parentScene.getWindow();

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("lote.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 640, 480);
		scene.setUserData(maquina);
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(parentStage);
		stage.setTitle("Reconocimiento por lotes");
		stage.setScene(scene);
		stage.show();
	}
}