package ua.pp.vbabich.oauth;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ua.pp.vbabich.oauth.model.UserAutoReqProps;

@Named
@SessionScoped
public class Core implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(Core.class.getName());

	@Inject private OAuthProviders oAuthProviders;
	@Inject private MyFullContext myFullContext;

	private UserAutoReqProps userAutoReqProps;
	private String validatedId;
    private boolean loggedIn;

	public String authorize(String providerName){
        oAuthProviders.setCallback(new CallBack());
		oAuthProviders.setContextURL(myFullContext.getContextURL());
		oAuthProviders.authorize(providerName);
		return null;
	}

	public void fiscal(){
		if (!loggedIn) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(myFullContext.getContextURL().concat("/pages/login.jsf"));
			} catch (IOException e) {
				logger.log(Level.SEVERE, "fiscal(): redirect error", e);
			}
		}
	}

    private class CallBack implements OAuthCallbackIntf {
        @Override
        public void onSuccess(String validatedId, UserAutoReqProps userAutoReqProps) {
            try {
                setUserAutoReqProps(userAutoReqProps);
                setValidatedId(validatedId);
                loggedIn = true;
                FacesContext.getCurrentInstance().getExternalContext().redirect(myFullContext.getContextURL().concat("/pages/welcome.jsf"));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "fiscal(): redirect error", e);
            }
        }

        @Override
        public void onError(String error) {
            logger.log(Level.WARNING, "Error:", error);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(myFullContext.getContextURL().concat("/pages/error.jsf"));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "fiscal(): redirect error", e);
            }
        }
    }

	public UserAutoReqProps getUserAutoReqProps() {
		return userAutoReqProps;
	}

	private void setUserAutoReqProps(UserAutoReqProps userAutoReqProps) {
		this.userAutoReqProps = userAutoReqProps;
	}

	public String getValidatedId() {
		return validatedId;
	}

	private void setValidatedId(String validatedId) {
		this.validatedId = validatedId;
	}
}
