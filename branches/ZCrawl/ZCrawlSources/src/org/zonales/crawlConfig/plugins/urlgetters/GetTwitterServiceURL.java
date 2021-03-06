/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zonales.crawlConfig.plugins.urlgetters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zonales.crawlConfig.objets.Service;
import org.zonales.helpers.Utils;
import org.zonales.metadata.Criterio;
import org.zonales.metadata.Filtro;
import org.zonales.metadata.ZCrawling;

/**
 *
 * @author nacho
 */
public class GetTwitterServiceURL implements GetServiceURL {

    @Override
    public String getURL(ZCrawling metadata, Service service) {
        if (metadata == null) {
            return null;
        }

        String urlServlet = service.getUri()
                + "?zone="
                + Utils.normalizeZone(metadata.getLocalidad())
                + "&q=";

        if (metadata.getUltimoHitDeExtraccion() != null) {
            urlServlet += "+since_id:" + metadata.getUltimoHitDeExtraccion() + "+";
        }

        String places = "";

        if (metadata.getCriterios() != null) {

            for (Criterio criterio : metadata.getCriterios()) {

                if (metadata.getCriterios().indexOf(criterio) != 0) {
                    urlServlet += "+";
                }

                urlServlet += "(";

                if (criterio.getDeLosUsuarios() != null) {
                    String usuario = "";
                    for (int i = 0; i < criterio.getDeLosUsuarios().size(); i++) {
                        usuario = criterio.getDeLosUsuarios().get(i);
                        if (i != 0) {
                            urlServlet += "+OR+";
                            places += ";";
                        }
                        places += usuario + "Place=";
                        urlServlet += "from:" + usuario;
                        if (criterio.getDeLosUsuariosPlaces() != null && criterio.getDeLosUsuariosPlaces().get(i) != null) {
                            places += criterio.getDeLosUsuariosPlaces().get(i);
                        }
                    }
                }

                if (criterio.getPalabras() != null) {
                    for (String palabra : criterio.getPalabras()) {
                        if (criterio.getPalabras().indexOf(palabra) != 0) {
                            urlServlet += "+";
                            if (!criterio.getSiosi()) {
                                urlServlet += "OR+";
                            }
                        }
                        try {
                            urlServlet += URLEncoder.encode(palabra.trim(), "UTF-8");
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(GetTwitterServiceURL.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                urlServlet += ")";
            }
        }

        if (metadata.getFiltros() != null) {
            for (Filtro filtro : metadata.getFiltros()) {
                if (filtro.getMinActions() != null && filtro.getMinActions() > 0) {
                }
            }
        }

        if (metadata.getTags() != null) {
            for (String tag : metadata.getTags()) {
                if (metadata.getTags().indexOf(tag) == 0) {
                    urlServlet += "&tags=";
                } else {
                    urlServlet += ",";
                }
                urlServlet += tag;
            }
        }

        if (metadata.getNoCriterios() != null) {

            for (Criterio criterio : metadata.getNoCriterios()) {

                if (metadata.getNoCriterios().indexOf(criterio) != 0) {
                    urlServlet += "+";
                }

                if (criterio.getPalabras() != null) {
                    for (String palabra : criterio.getPalabras()) {
                        if (criterio.getPalabras().indexOf(palabra) != 0) {
                            urlServlet += "+-";
                        }
                        try {
                            urlServlet += URLEncoder.encode(palabra.trim(), "UTF-8");
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(GetTwitterServiceURL.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

        try {
            urlServlet += "&" + URLEncoder.encode(places, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GetTwitterServiceURL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return urlServlet;
    }
}
