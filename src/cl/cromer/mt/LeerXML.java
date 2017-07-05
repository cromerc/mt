/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package cl.cromer.mt;

import jdk.internal.org.xml.sax.ErrorHandler;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.SAXParseException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

/**
 * Esta clase puede abrir y validar un archivo de XML. Se necesita un archivo mtbase.dtd
 */
class LeerXML {

	private boolean error = false;
	/**
	 * El metodo va a verificar que el archivo existe y que contiene XML valido. Si es valido devuelve el documento.
	 *
	 * @param archivo Es el archivo a abrir.
	 *
	 * @return Devuelve un document de XML o null si hay algun error.
	 */
	Document leerArchivo(File archivo) {
		if (archivo == null) {
			return null;
		}
		if (!archivo.exists()) {
			MT.mostrarMensaje("Error", "Archivo " + archivo.getName() + " no existe!");
			return null;
		}
		Document dc = createDocument(archivo);
		if (dc == null) {
			error = false;
			return validarXML(archivo);
		}
		return dc;
	}

	/**
	 * Crear un documento de XML
	 *
	 * @param archivo Es el archivo XML
	 *
	 * @return Retorna un document del XML o null si hay algun error.
	 */
	private Document createDocument(File archivo) {
		Document documento;
		try {
			if (!archivo.exists()) {
				MT.mostrarMensaje("Error", "Archivo " + archivo.getName() + " no existe!");
				return null;
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			dbf.setNamespaceAware(true);

			DocumentBuilder db = dbf.newDocumentBuilder();
			SimpleErrorHandler seh = new SimpleErrorHandler();
			db.setErrorHandler(seh);
			documento = db.parse(archivo);
			if (error) {
				//MT.mostrarMensaje("Error", "El archivo " + archivo.getName() + " no contiene xml valido!");
				return null;
			}
			documento.getDocumentElement().normalize();
			return documento;
		}
		catch (Exception e) {
			if (e.getMessage().contains(".dtd")) {
				return validarXML(archivo);
			}
			MT.mostrarMensaje("Error", "El archivo " + archivo.getName() + " no contiene xml valido!");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Este metodo se usa para validar el XML.
	 *
	 * @param original es el archivo original del XML
	 *
	 * @return Retorna un document de un XML arreglado, null si el xml no es coherente a lo que se pide
	 */
	private Document validarXML(File original) {
		File temp = fixXML(original);
		if (temp == null) {
			return null;
		}
		Document documento = createDocument(temp);
		if (!temp.delete()) {
			MT.mostrarMensaje("Error", "No se puede borrar el archivo " + temp.getName());
		}
		if (documento != null) {
			return documento;
		}
		return null;
	}

	/**
	 * Arreglar el archivo de XML si no es valido.
	 *
	 * @param original Es el archivo XML sin cambios
	 *
	 * @return un file de XML adaptado para ser validado
	 */
	private File fixXML(File original) {
		String aux;
		File temp;
		try {
			temp = new File("temp.xml");
			BufferedReader br = new BufferedReader(new FileReader(original));
			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
			if ((aux = br.readLine()).startsWith("<?xml")) {
				bw.write(aux + "\n<!DOCTYPE root SYSTEM \"mtbase.dtd\">");
			}
			else {
				bw.write("<!DOCTYPE root SYSTEM \"mtbase.dtd\">\n" + aux);
			}
			for (aux = ""; aux != null; aux = br.readLine()) {
				if (!aux.startsWith("<!DOCTYPE")) {
					bw.write(aux + "\n");
				}
			}
			bw.close();
		}
		catch (Exception e) {
			return null;
		}
		return temp;
	}

	/**
	 * Esta clase se usa para comprobar que el XML es valido.
	 */
	class SimpleErrorHandler implements ErrorHandler, org.xml.sax.ErrorHandler {

		/**
		 * Un warning
		 *
		 * @param e La excepción
		 *
		 * @throws SAXException La excepción thrown
		 */
		public void warning(SAXParseException e) throws SAXException {
			System.out.println(e.getMessage());
		}

		/**
		 * Un error
		 *
		 * @param e La excepción
		 *
		 * @throws SAXException La excepción thrown
		 */
		public void error(SAXParseException e) throws SAXException {
			//System.out.println(e.getMessage());
			error = true;
		}

		/**
		 * Un error fatal
		 *
		 * @param e La excepción
		 *
		 * @throws SAXException La excepción thrown
		 */
		public void fatalError(SAXParseException e) throws SAXException {
			//System.out.println(e.getMessage());
			error = true;
		}

		/**
		 * Un warning
		 *
		 * @param e La excepción
		 */
		@Override
		public void warning(org.xml.sax.SAXParseException e) {
			System.out.println(e.getMessage());
		}

		/**
		 * Un error
		 *
		 * @param e La excepción
		 */
		@Override
		public void error(org.xml.sax.SAXParseException e) {
			//System.out.println(e.getMessage());
			error = true;
		}

		/**
		 * Un error fatal
		 *
		 * @param e La excepción
		 */
		@Override
		public void fatalError(org.xml.sax.SAXParseException e) {
			//System.out.println(e.getMessage());
			error = true;
		}
	}
}