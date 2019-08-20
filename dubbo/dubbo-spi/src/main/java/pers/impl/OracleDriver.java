package pers.impl;

import pers.api.DataBaseDriver;

public class OracleDriver implements DataBaseDriver {
    @Override
    public String connect(String host) {
        return "oracle connect. host : " + host;
    }
}
