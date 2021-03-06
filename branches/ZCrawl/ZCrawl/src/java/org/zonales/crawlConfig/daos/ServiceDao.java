/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zonales.crawlConfig.daos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.zonales.crawlConfig.objets.Param;
import org.zonales.crawlConfig.objets.Service;

/**
 *
 * @author nacho
 */
public class ServiceDao extends BaseDao {

    private DBCollection services;

    public ServiceDao(String db_host, Integer db_port, String db_name) {
        super(db_host, db_port, db_name);
        this.services = this.db.getCollection("services");
        this.services.ensureIndex(new BasicDBObject("name", 1), "uniqueName", true);
    }

    public void saveService(Service service) throws MongoException {
        BasicDBObject serviceDoc = new BasicDBObject();
        
        ArrayList<Param> params = service.getParams();
        ArrayList paramsToDoc = new ArrayList();

        for (Param param: params) {
            paramsToDoc.add(new BasicDBObject(param.getName(), param.getRequired()));
        }

        serviceDoc.put("name", service.getName());
        serviceDoc.put("uri", service.getUri());

        serviceDoc.put("params", paramsToDoc);

        System.out.println(serviceDoc.toString());

        this.services.insert(serviceDoc);
    }

    public void updateService(String name, Service newService) throws MongoException {
        BasicDBObject query = new BasicDBObject("name", name);
        DBObject resp;
        DBCursor cur;

        cur = this.services.find(query);

        resp = cur.next();

        if (resp != null) {
            BasicDBObject serviceDoc = new BasicDBObject();

            if (newService.getName() != null) {
                serviceDoc.put("name", newService.getName());
            } else {
                serviceDoc.put("name", (String)resp.get("name"));
            }

            if (newService.getUri() != null) {
                serviceDoc.put("uri", newService.getUri());
            } else {
                serviceDoc.put("uri", (String)resp.get("uri"));
            }

            ArrayList<Param> params = newService.getParams();

            if (params != null) {
                ArrayList paramsToDoc = new ArrayList();

                for (Param param: params) {
                    paramsToDoc.add(new BasicDBObject(param.getName(), param.getRequired()));
                }
                serviceDoc.put("params", paramsToDoc);
            } else {
                serviceDoc.put("params", resp.get("params"));
            }

            this.services.update(new BasicDBObject().append("name", name), serviceDoc);
        }
    }

    public String retrieveServiceJson(String name) {
        BasicDBObject query = new BasicDBObject("name", name);
        DBObject resp;
        DBCursor cur;

        cur = this.services.find(query);

        resp = cur.next();
        resp.removeField("_id");
        //System.out.println(resp);
        
        return resp.toString();
    }

    public Service retrieveService(String name) {
        BasicDBObject query = new BasicDBObject("name", name);
        DBObject resp;
        DBCursor cur;
        Service service = new Service();
        ArrayList<BasicDBObject> paramsJson = new ArrayList<BasicDBObject>();
        String paramName = "";
        String token;
        Boolean paramRequired = false;
        StringTokenizer paramToken;
        int tokenCount = 0;

        cur = this.services.find(query);

        resp = cur.next();
        resp.removeField("_id");

        service.setName((String)resp.get("name"));
        service.setUri((String)resp.get("uri"));

        paramsJson = (ArrayList<BasicDBObject>)resp.get("params");

        for (BasicDBObject paramJson: paramsJson) {
            paramToken = new StringTokenizer(paramJson.toString(), "\" }");
            while (paramToken.hasMoreTokens()) {
                token = paramToken.nextToken();
                tokenCount++;
                if (tokenCount == 2) {
                    paramName = token;
                }
                if (tokenCount == 4) {
                    paramRequired = Boolean.valueOf(token);
                }
            }
            service.addParam(paramName, paramRequired);
        }

        //System.out.println(service);
        
        return service;
    }

}
