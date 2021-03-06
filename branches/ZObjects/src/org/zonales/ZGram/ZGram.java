/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zonales.ZGram;

import org.zonales.errors.ZMessage;
import org.zonales.metadata.ZCrawling;

/**
 *
 * @author nacho
 */
public class ZGram extends ZCrawling {
    private Oid _id;
    private Integer cod;
    private String msg;
    //private ZMessage zmessage;
    private String verbatim;
    private String estado;
    private Long creado;
    private String creadoPor;
    private Long modificado;
    private String modificadoPor;
    private Long ultimaExtraccionConDatos;

    public ZGram(ZMessage zmessage, String verbatim, String estado, Long creado, Long modificado) {
        this.cod = zmessage.getCod();
        this.msg = zmessage.getMsg();
        this.verbatim = verbatim;
        this.estado = estado;
        this.creado = creado;
        this.modificado = modificado;
    }
    
    public ZGram() {
    }

    public Oid getId() {
        return _id;
    }

    public void setId(Oid _id) {
        this._id = _id;
    }

    public ZMessage getZmessage() {
        return new ZMessage(this.cod, this.msg);
    }

    public void setZmessage(ZMessage zmessage) {
        this.cod = zmessage.getCod();
        this.msg = zmessage.getMsg();
    }

    public String getVerbatim() {
        return verbatim;
    }

    public void setVerbatim(String verbatim) {
        this.verbatim = verbatim;
    }

    public Long getCreado() {
        return creado;
    }

    public void setCreado(Long creado) {
        this.creado = creado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getModificado() {
        return modificado;
    }

    public void setModificado(Long modificado) {
        this.modificado = modificado;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getUltimaExtraccionConDatos() {
        return ultimaExtraccionConDatos;
    }

    public void setUltimaExtraccionConDatos(Long ultimaExtraccionConDatos) {
        this.ultimaExtraccionConDatos = ultimaExtraccionConDatos;
    }

   

}
