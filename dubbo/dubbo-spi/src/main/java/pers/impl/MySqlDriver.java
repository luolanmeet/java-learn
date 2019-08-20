package pers.impl;

import pers.api.DataBaseDriver;

public class MySqlDriver implements DataBaseDriver {
    @Override
    public String connect(String host) {
        return "mysql connect. host : " + host;
    }
}
