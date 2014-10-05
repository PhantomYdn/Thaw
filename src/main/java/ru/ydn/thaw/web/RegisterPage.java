package ru.ydn.thaw.web;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import com.orientechnologies.orient.core.db.record.ODatabaseRecord;
import com.orientechnologies.orient.core.metadata.security.OUser;
import com.orientechnologies.orient.core.record.impl.ODocument;

import ru.ydn.thaw.ThawWebApplication;
import ru.ydn.thaw.ThawWebSession;
import ru.ydn.wicket.wicketorientdb.utils.DBClosure;

@MountPath("/register")
public class RegisterPage extends ThawWebPage
{
	public RegisterPage()
	{
		if(ThawWebSession.get().isSignedIn()) setResponsePage(ThawWebApplication.get().getHomePage());
		
		final TextField<String> username = new TextField<String>("username", Model.of(""));
		final TextField<String> email = new TextField<String>("email", Model.of(""));
		username.setRequired(true);
		email.setRequired(true);
		final PasswordTextField password = new PasswordTextField("password", Model.of(""));
		final PasswordTextField confirmPassword = new PasswordTextField("confirmPassword", Model.of(""));
		Form<?> form = new Form<Object>("form")
		{
			@Override
			protected void onSubmit() {
				new DBClosure<Boolean>() {

					@Override
					protected Boolean execute(ODatabaseRecord db) {
						OUser newUser = db.getMetadata().getSecurity().createUser(username.getModelObject(), password.getModelObject(), "writer", "user");
						ODocument newUserDoc = newUser.getDocument();
						newUserDoc.field("email", email.getModelObject());
						newUserDoc.save();
						return true;
					}
				}.execute();
				ThawWebSession.get().signIn(username.getModelObject(), password.getModelObject());
			}
			
		};
		form.add(new EqualPasswordInputValidator(password, confirmPassword));
		form.add(username, email, password, confirmPassword, new Button("register"));
		add(form);
		add(new BookmarkablePageLink<Object>("login", LoginPage.class));
	}
}
