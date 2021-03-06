package org.zonales.parser.services;

/*
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* $Id$
 *
 */
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zonales.errors.ZMessages;
import org.zonales.metadata.ZCrawling;
import org.zonales.parser.parser.Globals;
import org.zonales.parser.parser.Parser;
import org.zonales.parser.parser.ParserException;

/**
 * Example servlet showing request headers
 *
 * @author James Duncan Davidson <duncan@eng.sun.com>
 */
public class ZCrawlParser extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        InputStream stream = getServletContext().getResourceAsStream("/WEB-INF/servlet.properties");
        Properties props = new Properties();
        props.load(stream);
        Globals.host = props.getProperty("db_host");
        Globals.port = Integer.valueOf(props.getProperty("db_port"));
        Globals.db = props.getProperty("db_name");
        Globals.nodeUrl = props.getProperty("node_url");
        Globals.timeout = Integer.valueOf(props.getProperty("timeout"));

        ZCrawling zcrawling = new ZCrawling();
        String extraction = request.getParameter("q");
        try {
            String[] args = {"-string", extraction.replace("\\\"", "\"")};
            Parser.main(zcrawling, out, args);
            out.print("{\"cod\": \"" + ZMessages.SUCCESS.getCod() + "\", \"msg\": \"" + ZMessages.SUCCESS.getMsg() + "\", meta: \"" + (new Gson()).toJson(zcrawling).replace("\\\"", "").replace("\"", "\\\"") + "\"}");
        } catch (ParserException ex) {

            if (!"".equals(ex.getZcrawling().getLocalidad()) && !"".equals(ex.getZcrawling().getFuente()) && ex.getZcrawling().getTags() != null && !ex.getZcrawling().getTags().isEmpty()) {
                out.print(ZMessages.appendMessage(ZMessages.ZPARSER_ZGRAM_PARTIALLY_PARSED, " - Error al parsear la configuración de extracción (el * marca el comienzo del error): " + ex.getMessage().replace("\\\"", "").replace("\"", "\\\"").replace("\n", "\\n") + "\", \"meta\": \"" + (new Gson()).toJson(zcrawling).replace("\\\"", "").replace("\"", "\\\"")));
            } else {
                out.print(ZMessages.appendMessage(ZMessages.ZPARSER_CANNOT_PARSE, " - Error al parsear la configuración de extracción (el * marca el comienzo del error): " + ex.getMessage().replace("'", "\\'").replace("\"", "\\\"").replace("\n", "\\n")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ZCrawlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
