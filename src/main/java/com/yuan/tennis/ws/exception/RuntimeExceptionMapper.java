package com.yuan.tennis.ws.exception;

import com.yuan.tennis.ws.MatchResultService;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException>
{
    final static int VALIDATION_ERROR_STATUS = 403;

    private static final Logger logger = Logger.getLogger(RuntimeExceptionMapper.class.getName());

    @Override
    public Response toResponse(RuntimeException ex) {
        logger.error("Got runtime exception: " + ex.getMessage(), ex);
        return Response.status(VALIDATION_ERROR_STATUS)
                .entity(new ErrorMessage(VALIDATION_ERROR_STATUS, "RuntimeException: " + ex.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
