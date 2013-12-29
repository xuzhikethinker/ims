package com.ims.webapp.view;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Created by Administrator on 13-12-25.
 */
public class IMSExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory base;

    private IMSExceptionHandler cached;

    public IMSExceptionHandlerFactory(ExceptionHandlerFactory base) {
        this.base = base;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        if(cached == null) {
            cached = new IMSExceptionHandler(base.getExceptionHandler());
        }

        return cached;
    }
}
