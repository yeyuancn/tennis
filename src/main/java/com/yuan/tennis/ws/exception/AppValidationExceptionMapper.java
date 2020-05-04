package com.yuan.tennis.ws.exception;

import com.yuan.tennis.ws.exception.AppValidationException;
import org.glassfish.jersey.internal.Errors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by V644593 on 9/24/2015.
 */
@Provider
public class AppValidationExceptionMapper implements ExceptionMapper<AppValidationException>
{
    final static int VALIDATION_ERROR_STATUS = 403;

    @Override
    public Response toResponse(AppValidationException ex) {
        return Response.status(VALIDATION_ERROR_STATUS)
                .entity(new ErrorMessage(VALIDATION_ERROR_STATUS, ex.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
