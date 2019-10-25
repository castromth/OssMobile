package com.example.os.model;

public class Os {

    private String equip = "";
    private String local = "";
    private String desc = "";
    private String condCheg = "Sem registro";
    private String acoes = "Sem registro";
    private String condSaida = "Sem registro";
    private String pecasUlti = "Sem registro";

    private int status = 0;
    private String dataAbert = "";
    private String origem = "";
    private String data = "";
    private String contrato = "";
    private String responsavel = "";
    private String solicitante = "";

    private String id = "";

    public Os(){

    }

    public String getOrigem() {
        return origem;
    }

    public String getAcoes() {
        return acoes;
    }

    public String getEquip() {
        return equip;
    }

    public String getContrato() {
        return contrato;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public String getLocal() {
        return local;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public String getCondCheg() {
        return condCheg;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public String getCondSaida() {
        return condSaida;
    }

    public String getDataAbert() {
        return dataAbert;
    }

    public String getPecasUlti() {
        return pecasUlti;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setAcoes(String acoes) {
        this.acoes = acoes;
    }

    public void setCondCheg(String condCheg) {
        this.condCheg = condCheg;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public void setCondSaida(String condSaida) {
        this.condSaida = condSaida;
    }

    public void setDataAbert(String dataAbert) {
        this.dataAbert = dataAbert;
    }

    public void setPecasUlti(String pecasUlti) {
        this.pecasUlti = pecasUlti;
    }

    public void setSolicitante(String solicitante) {
        solicitante = solicitante;
    }

    public void setEquip(String equip) {
        this.equip = equip;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }
}
