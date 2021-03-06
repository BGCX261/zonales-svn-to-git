/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zonales.solrjtester;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.zonales.BaseService;

/**
 *
 * @author nacho
 */
public class getKmlPosts extends BaseService {

    @Override
    public void serve(HttpServletRequest request, HttpServletResponse response, Properties props) throws ServletException, IOException, Exception {
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        Double minLat = Double.valueOf(request.getParameter("minLat"));
        Double minLon = Double.valueOf(request.getParameter("minLon"));
        Double maxLat = Double.valueOf(request.getParameter("maxLat"));
        Double maxLon = Double.valueOf(request.getParameter("maxLon"));
        String url = "http://localhost:8080/solr-geo";
        UpdateResponse ur = new UpdateResponse();

        try {
            CommonsHttpSolrServer server = new CommonsHttpSolrServer(url);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Servidor Solr creado segun URL: {0}", url);


            server.setSoTimeout(1000);  // socket read timeout
            server.setConnectionTimeout(100);
            server.setDefaultMaxConnectionsPerHost(100);
            server.setMaxTotalConnections(100);
            server.setFollowRedirects(false);  // defaults to false
            // allowCompression defaults to false.
            // Server side must support gzip or deflate for this to have any effect.
            server.setAllowCompression(true);
            server.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
            server.setParser(new XMLResponseParser()); // binary parser is used by default

            SolrQuery query = new SolrQuery();
            query.setQuery("*");
            query.setSortField("id", SolrQuery.ORDER.desc);
            query.setRows(1);

            QueryResponse rsp = server.query( query );

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Error en SolrjTester: {0} - {1}", new Object[]{ex, ur});
            out.print(ex);
        }
    }


}
