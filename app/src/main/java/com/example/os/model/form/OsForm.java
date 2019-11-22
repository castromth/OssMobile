package com.example.os.model.form;

import com.example.os.model.Contrato;
import com.example.os.model.Os;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OsForm {
    @NotNull
    @NotEmpty
    private String origem;
    @NotNull @NotEmpty
    private String sumario;
    @NotNull @NotEmpty
    private String descricao;
    @NotNull @NotEmpty
    private String contrato;
    @NotNull @NotEmpty
    private String responsavel;
    @NotNull @NotEmpty
    private String solicitante;
    public String getSolicitante() {
        return solicitante;
    }
    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }


    @NotNull @NotEmpty
    private String local;
    @NotNull @NotEmpty
    private String equiId;
    private String dataLimite;



    public String getOrigem() {
        return origem;
    }
    public void setOrigem(String origem) {
        this.origem = origem;
    }
    public String getSumario() {
        return sumario;
    }
    public void setSumario(String sumario) {
        this.sumario = sumario;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Contrato getContrato() {
        return Contrato.stringToContrato(this.contrato);
    }
    public void setContrato(String contrato) {
        this.contrato = contrato;
    }
    public String getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public String getEquiId() {
        return equiId;
    }
    public void setEquiId(String equiId) {
        this.equiId = equiId;
    }
    public Date getDataLimite() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data;
        try {
            data = formato.parse(this.dataLimite);
            return data;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public void setDataLimite(String dataLimite) {
        this.dataLimite = dataLimite;
    }
}
