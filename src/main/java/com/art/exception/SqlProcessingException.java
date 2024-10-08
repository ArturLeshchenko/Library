package com.art.exception;

import java.sql.SQLException;

public class SqlProcessingException extends RuntimeException {
    public SqlProcessingException(SQLException e) {
        super(e);
    }
}
