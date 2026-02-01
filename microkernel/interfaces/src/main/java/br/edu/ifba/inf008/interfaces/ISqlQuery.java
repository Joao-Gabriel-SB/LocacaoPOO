package br.edu.ifba.inf008.interfaces;

import java.sql.Connection;

public interface ISqlQuery <T>{
    T search(Connection conn);
}
