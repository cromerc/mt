/* Copyright (c) 2017 Christopher Cromer
 * Copyright (c) 2017 Carlos Faúndez
 *
 * This file is part of mt. It is subject to the license terms in the LICENSE file found in the top-level directory of this distribution.
 * This file may not be copied, modified, propagated, or distributed except according to the terms contained in the LICENSE file.
 */

package mt;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import jdk.internal.org.xml.sax.ErrorHandler;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.SAXParseException;

import org.w3c.dom.Document;

/**
 * Esta clase puede abrir y validar un archivo de xml.
 */
class LeerXML {

	/**
	 * El metodo va a verificar que el archivo existe y que contiene xml valido. Si es valido devuelve el documento.
	 *
	 * @param archivo Es el archivo a abrir.
	 * @return Devuelve un document de xml o null si habia algun error.
	 */
	Document leerArchivo(File archivo) {
		try {
			if (!archivo.exists()) {
				System.out.println("El archivo " + archivo.getName() + " no existe!");
				return null;
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			dbf.setNamespaceAware(true);

			DocumentBuilder db = dbf.newDocumentBuilder();
			db.setErrorHandler(new SimpleErrorHandler());
			Document document = db.parse(archivo);
			document.getDocumentElement().normalize();
			return document;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Esta clase se usa para validar que el xml es valido.
	 */
	protected class SimpleErrorHandler implements ErrorHandler, org.xml.sax.ErrorHandler {

		/**
		 * Un warning
		 * @param e La excepción
		 * @throws SAXException La excepción thrown
		 */
		public void warning(SAXParseException e) throws SAXException {
			System.out.println(e.getMessage());
		}

		/**
		 * Un error
		 * @param e La excepción
		 * @throws SAXException La excepción thrown
		 */
		public void error(SAXParseException e) throws SAXException {
			System.out.println(e.getMessage());
		}

		/**
		 * Un error fatal
		 * @param e La excepción
		 * @throws SAXException La excepción thrown
		 */
		public void fatalError(SAXParseException e) throws SAXException {
			System.out.println(e.getMessage());
		}

		/**
		 * Un warning
		 * @param e La excepción
		 */
		@Override
		public void warning(org.xml.sax.SAXParseException e) {
			System.out.println(e.getMessage());
		}

		/**
		 * Un error
		 * @param e La excepción
		 */
		@Override
		public void error(org.xml.sax.SAXParseException e) {
			System.out.println(e.getMessage());
		}

		/**
		 * Un error fatal
		 * @param e La excepción
		 */
		@Override
		public void fatalError(org.xml.sax.SAXParseException e) {
			System.out.println(e.getMessage());
		}
	}
}