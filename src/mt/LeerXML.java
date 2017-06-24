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
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import jdk.internal.org.xml.sax.ErrorHandler;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.SAXParseException;

import org.w3c.dom.Document;

/**
 * Esta clase puede abrir y validar un archivo de xml. Se necesita un archivo mtbase.dtd
 */
class LeerXML {

	/**
	 * El metodo va a verificar que el archivo existe y que contiene xml valido. Si es valido devuelve el documento.
	 *
	 * @param archivo Es el archivo a abrir.
	 * @return Devuelve un document de xml o null si hay algun error.
	 */
	public Document leerArchivo(File archivo) {
		if(!archivo.exists() || !archivo.getName().endsWith(".xml")){
			System.out.println("Archivo "+archivo.getName()+" no existe o no es compatible");
			return null;
		}
		Document dc = createDocument(archivo);
		if(dc == null) return validarXML(archivo);
		return dc;
	}

	/**
	 *
	 * @param archivo Es el archivo Xml
	 * @return Retorna un document del xml o null si hay algun error.
	 */

	private Document createDocument(File archivo){
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
			if(seh.error) return null;
			document.getDocumentElement().normalize();
			return document;
		}
		catch(Exception e) {
			if(e.getMessage().indexOf(".dtd") >= 0) {
				return validarXML(archivo);
			}
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *
	 * @param original es el archivo original del xml
	 * @return Retorna un document de un xml arreglado, null si el xml no es coherente a lo que se pide
	 */
	private Document validarXML(File original){
		File temp = fixXML(original);
		Document document = createDocument(temp);
		temp.delete();
		if(document != null) return document;
		return null;
	}

	/**
	 *
	 * @param original Es el archivo xml sin cambios
	 * @return un file de xml adaptado para ser validado
	 */
	private File fixXML(File original){
		String aux = "";
		File temp;
		try{
			temp = new File("temp.xml");
			BufferedReader br = new BufferedReader(new FileReader(original));
			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
			if((aux = br.readLine()).startsWith("<?xml")){
				bw.write(aux+"\n<!DOCTYPE root SYSTEM \"mtbase.dtd\">");
			}else{
				bw.write("<!DOCTYPE root SYSTEM \"mtbase.dtd\">\n"+aux);
			}
			for(aux = "";aux != null;aux = br.readLine()){
				if(!aux.startsWith("<!DOCTYPE")) bw.write(aux+"\n");
			}
			bw.close();
		}catch (Exception e){return null;}
		return temp;
	}

	/**
	 * Esta clase se usa para comprobar que el xml es valido.
	 */
	protected class SimpleErrorHandler implements ErrorHandler, org.xml.sax.ErrorHandler {
		public boolean error = false;
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
			error = true;
		}

		/**
		 * Un error fatal
		 * @param e La excepción
		 * @throws SAXException La excepción thrown
		 */
		public void fatalError(SAXParseException e) throws SAXException {
			System.out.println(e.getMessage());
			error = true;
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
			error = true;
		}

		/**
		 * Un error fatal
		 * @param e La excepción
		 */
		@Override
		public void fatalError(org.xml.sax.SAXParseException e) {
			System.out.println(e.getMessage());
			error = true;
		}
	}
}