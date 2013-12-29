package com.ims.webapp.view;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;

/**
 * Created by Administrator on 13-12-25.
 */
public class IMSExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public IMSExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterable<ExceptionQueuedEvent> events = this.wrapped.getUnhandledExceptionQueuedEvents();
        for(Iterator<ExceptionQueuedEvent> it = events.iterator(); it.hasNext();) {
            ExceptionQueuedEvent event = it.next();
            ExceptionQueuedEventContext eqec = event.getContext();

            if(eqec.getException() instanceof ViewExpiredException) {
                FacesContext context = eqec.getContext();
                if(!context.isReleased()) {
                    NavigationHandler navHandler = context.getApplication().getNavigationHandler();

                    try {
                        navHandler.handleNavigation(context, null, "index?faces-redirect=true&expired=true");
                    }
                    finally {
                        it.remove();
                    }
                }

            }
        }

        this.wrapped.handle();
    }

}
