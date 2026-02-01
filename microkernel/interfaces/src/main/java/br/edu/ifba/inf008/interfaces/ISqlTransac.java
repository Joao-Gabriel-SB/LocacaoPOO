package br.edu.ifba.inf008.interfaces;

import java.sql.Connection;

public interface ISqlTransac {
    void runTransaction(Connection conn);
}
