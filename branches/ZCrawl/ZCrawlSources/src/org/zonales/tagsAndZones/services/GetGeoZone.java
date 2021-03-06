/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zonales.tagsAndZones.services;

import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zonales.BaseService;
import org.zonales.errors.ZMessages;
import org.zonales.tagsAndZones.daos.GeoZoneDao;

/**
 *
 * @author rodrigo
 */
public class GetGeoZone extends BaseService {

    @Override
    public void serve(HttpServletRequest request, HttpServletResponse response, Properties props) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/javascript");
            out = response.getWriter();

            String key = request.getParameter("key");
            String id = request.getParameter("id");

            GeoZoneDao geoZoneDao = new GeoZoneDao(props.getProperty("db_host"), Integer.valueOf(props.getProperty("db_port")), props.getProperty("db_name"));
            String retrieve = null;

            if ("zone".equals(key) && id != null) {
                retrieve = geoZoneDao.retrieveJsonByZone(id);
            }

            if (retrieve != null) {
                out.print(retrieve);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "No se encontraron geozonas");
                out.print(ZMessages.DATA_NOT_FOUND);
            }

        } catch (Exception ex) {
            StringBuilder stacktrace = new StringBuilder();
            for (StackTraceElement line : ex.getStackTrace()) {
                stacktrace.append(line.toString());
                stacktrace.append("\n");
            }
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                    "EXCEPCION: {0}\nTRACE: {1}", new Object[]{ex, stacktrace.toString()});

            out.print(ZMessages.MONGODB_ERROR);            
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
