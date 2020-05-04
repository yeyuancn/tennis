package com.yuan.tennis.ws.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by V644593 on 9/24/2015.
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException>
{
    final static int APP_BUS_ERROR_STATUS = 409;

    @Override
    public Response toResponse(AppException ex) {
        return Response.status(APP_BUS_ERROR_STATUS)
                .entity(new ErrorMessage(APP_BUS_ERROR_STATUS, ex.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
