package com.example.hypeadvice.domain.bean;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import java.io.Serializable;

public abstract class Bean implements Serializable {

	public Bean() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public final void init() {
		initBean();
	}

	protected abstract void initBean();

	protected void addMessageError(Exception e) {
		e.printStackTrace();
		String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
		addFaceMessage(FacesMessage.SEVERITY_ERROR, rootCauseMessage, null);
	}

	protected void addFaceMessage(Severity severity, String message, String componentId) {

		addFaceMessage(new FacesMessage(severity, message, null), componentId);
	}

	protected void addFaceMessage(FacesMessage facesMessage, String componentId) {

		FacesContext facesContext = getFacesContext();
		facesContext.addMessage(componentId, facesMessage);
//		facesContext.renderResponse();

		ExternalContext externalContext = getExternalContext();
		Flash flash = externalContext.getFlash();
		flash.setKeepMessages(true);

	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}
}
