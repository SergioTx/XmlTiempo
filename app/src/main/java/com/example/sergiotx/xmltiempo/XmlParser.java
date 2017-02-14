package com.example.sergiotx.xmltiempo;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Sergio on 31/01/2017.
 */

public class XmlParser {

    private URL rssUrl;

    public XmlParser(String url){
        try {
            this.rssUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream(){
        try {
            return rssUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> returnTemp(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<String> datos = new ArrayList<String>();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(this.getInputStream());
            Element root = dom.getDocumentElement();

            Node predicciones = root.getElementsByTagName("prediccion").item(0);
            Node hoy = predicciones.getChildNodes().item(1);

            NodeList lista = hoy.getChildNodes();

            for(int i=0;i<lista.getLength();i++) {
                String tag = lista.item(i).getNodeName();
                Log.d("XML",tag);
                if (tag.equals("temperatura")){
                    Log.d("XML","ENTRA");
                    NodeList temperatura = lista.item(i).getChildNodes();
                    datos.add(getNodeText(temperatura.item(1)));
                    datos.add(getNodeText(temperatura.item(3)));
                }
            }

            return datos;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getNodeText(Node dato){
        StringBuilder texto = new StringBuilder();
        NodeList fragmentos = dato.getChildNodes();
        for (int i=0;i<fragmentos.getLength();i++){
            texto.append(fragmentos.item(i).getNodeValue());
        }
        return texto.toString();
    }

}
