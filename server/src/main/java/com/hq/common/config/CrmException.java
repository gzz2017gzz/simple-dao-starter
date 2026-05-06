package com.hq.common.config;

import java.io.Serial;

public class CrmException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CrmException(String message) {
        super(message);
    }
}