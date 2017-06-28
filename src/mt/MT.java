/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package mt;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import org.w3c.dom.Document;

import java.io.File;

/**
 * Esta clase es la clase princial de la Maquina Turing
 */
public class MT extends Application {

	/**
	 * Metodo de JavaFX llamada para generar el interfaz grafico.
	 * @param primaryStage La ventana principal
	 * @throws Exception La excepción
	 */

    static Machine machine;
    @Override
    public void start(final Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("mt.fxml"));
        Group root = new Group();
        primaryStage.setTitle("Maquina de Turing");
        primaryStage.setScene(new Scene(root, 300, 275));
        Button button = new Button("Elegir archivo");
        button.setDefaultButton(true);
        button.setLayoutX(130);button.setLayoutY(125);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
	            FileChooser fileChooser = new FileChooser();
	            fileChooser.setTitle("Abrir archivo XML");
	            File archivo = fileChooser.showOpenDialog(primaryStage);
	            LeerXML xml = new LeerXML();
	            Document document = xml.leerArchivo(archivo);
	            if(document != null) {
                    machine = new Machine(document);
                    for(int i=0; i<machine.machine.estados.size();i++){
                        System.out.println(machine.machine.estados.get(i));
                    }
                    if(machine.comprobarCadena(new StringBuilder("000111###"),5)){
                        System.out.println("Reconoce");
                    }else System.out.println("No reconoce");
                }
            }
        };
        button.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);
        root.getChildren().add(button);
        primaryStage.show();
    }

	/**
	 * El metodo principal del programa
	 * @param args Los argumentos pasado al programa
	 */
    public static void main(String[] args) {
        launch(args);
    }
}
