package com.example.os.model;

public enum Contrato {

    ANTT,
    AGETOP,
    DUTRA,
    ACCIONA;

    public static Contrato stringToContrato(String contrato){
        if(Contrato.ACCIONA.toString() == contrato) {
            return Contrato.ACCIONA;
        }else if(Contrato.AGETOP.toString() == contrato) {
            return Contrato.AGETOP;
        }else if(Contrato.DUTRA.toString() == contrato) {
            return Contrato.DUTRA;
        }else if(Contrato.ANTT.toString() == contrato) {
            return Contrato.ANTT;
        }
        return null;

    }
}
