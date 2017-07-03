package mt;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class LoteController {
	@FXML
	private VBox vboxLote;

	/**
	 * Boton Run MT de lote
	 */
	@FXML
	protected void runLote() throws Exception {
		Scene scene = vboxLote.getScene();
		Maquina maquina = (Maquina) scene.getUserData();
		int[] estados = {5};
		if (maquina.comprobarCadena(new StringBuilder("000111###"),estados)) {
			MT.mostrarMensaje("Resultado", "Reconce");
		}
		else {
			MT.mostrarMensaje("Resultado", " No reconce");
		}
	}
}
