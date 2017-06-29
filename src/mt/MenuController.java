/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package mt;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlar las acciones cuando una opción es elegido en el menu.
 */
public class MenuController extends VBox implements Initializable {
	@FXML
	private MenuBar menuBar;

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
		Maquina maquina;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir archivo XML");
		File archivo = fileChooser.showOpenDialog(stage);
		LeerXML xml = new LeerXML();
		Document documento = xml.leerArchivo(archivo);
		if (documento != null) {
			maquina = new Maquina(documento);
			for (int i = 0; i < maquina.getMaquina().getEstados().size(); i++) {
				System.out.println(maquina.getMaquina().getEstados().get(i));
			}
			TableView tableView = FXMLLoader.load(getClass().getResource("tabla.fxml"));
			HBox.setHgrow(tableView, Priority.ALWAYS);
			HBox contenido = (HBox) scene.lookup("#contenido");
			contenido.getChildren().add(tableView);
			TableColumn tableColumn1 = (TableColumn) tableView.getColumns().get(0);
			TableColumn tableColumn2 = (TableColumn) tableView.getColumns().get(1);
			tableView.skinProperty().addListener((source, oldWidth, newWidth) -> {
				final TableHeaderRow header = (TableHeaderRow) tableView.lookup("TableHeaderRow");
				header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
			});
			tableColumn1.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
			tableColumn2.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));

			/*if (maquina.comprobarCadena(new StringBuilder("000111###"), 5)) {
				MT.mostrarMensaje("Resultado", "Reconce");
			}
			else {
				MT.mostrarMensaje("Resultado", " No reconce");
			}*/
		}
	}
}