/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package mt;

import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import jdk.internal.org.xml.sax.ErrorHandler;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.SAXParseException;

import org.w3c.dom.Document;

/**
 * Esta clase puede abrir y validar un archivo de XML. Se necesita un archivo mtbase.dtd
 */
class LeerXML {

	/**
	 * El metodo va a verificar que el archivo existe y que contiene XML valido. Si es valido devuelve el documento.
	 *
	 * @param archivo Es el archivo a abrir.
	 *
	 * @return Devuelve un document de XML o null si hay algun error.
	 */
	Document leerArchivo(File archivo) {
		if(archivo == null){
			System.out.println("No se ha seleccionado archivo");
			return null;
		}
		if (!archivo.exists() || !archivo.getName().endsWith(".xml")) {
			System.out.println("Archivo " + archivo.getName() + " no existe o no es compatible");
			return null;
		}
		Document dc = createDocument(archivo);
		if (dc == null) {
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
		Document document;
		try {
			if (!archivo.exists()) {
				System.out.println("El archivo " + archivo.getName() + " no existe!");
				return null;
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			dbf.setNamespaceAware(true);

			DocumentBuilder db = dbf.newDocumentBuilder();
			SimpleErrorHandler seh = new SimpleErrorHandler();
			db.setErrorHandler(seh);
			document = db.parse(archivo);
			if (seh.error) {
				return null;
			}
			document.getDocumentElement().normalize();
			return document;
		}
		catch (Exception e) {
			if (e.getMessage().contains(".dtd")) {
				return validarXML(archivo);
			}
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
		Document document = createDocument(temp);
		if (!temp.delete()) {
			System.out.println("No puede borrar el archivo " + temp.getName());
		}
		if (document != null) {
			return document;
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
		boolean error = false;

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