package ru.ydn.thaw.web;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("/login")
public class LoginPage extends ThawWebPage<Object>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginPage()
	{
		super();
		SignInPanel signinPanel = new SignInPanel("signInPanel", false);
		signinPanel.setRememberMe(false);
		add(signinPanel);
	}

	@Override
	public IModel<String> getTitleModel() {
		return new ResourceModel("login.title");
	}

}
