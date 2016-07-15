package ua.pp.vbabich.oauth;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class MyFullContext implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MyFullContext.class.getName());
	private final String contextURL; 
	private final String contextName; 

	public MyFullContext(){
		if (logger.isLoggable(Level.INFO)) logger.log(Level.INFO,"Constructor start " + this.toString());
		contextName = FacesContext.getCurrentInstance().getExternalContext().getContextName();
		contextURL  = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme()
				.concat("://")
				.concat(FacesContext.getCurrentInstance().getExternalContext().getRequestServerName())
//				.concat((FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort() == 80)? "" : ":".concat(new Integer(FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()).toString()))
				.concat("/")
				.concat(contextName);
		if (logger.isLoggable(Level.INFO)) {
			logger.log(Level.INFO,"contextURL="+contextURL+" contextName="+contextName);
			logger.log(Level.INFO,"Constructor end");
		}
	}

	public String getContextURL(){
		return contextURL;
	}
	public String getContextName(){
		return contextName;
	}
}
