package ru.ydn.thaw.web;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.thaw.ThawWebSession;
import ru.ydn.thaw.component.Badges;
import ru.ydn.thaw.jgravatar.Gravatar;
import ru.ydn.thaw.jgravatar.GravatarDefaultImage;
import ru.ydn.thaw.jgravatar.GravatarRating;
import ru.ydn.wicket.wicketorientdb.model.ODocumentPropertyModel;

import com.orientechnologies.orient.core.metadata.security.OUser;
import com.orientechnologies.orient.core.record.impl.ODocument;

@MountPath("/profile")
public class ProfilePage extends DocumentThawWebPage
{
	public ProfilePage()
	{
		super(new AbstractReadOnlyModel<ODocument>() {

			@Override
			public ODocument getObject() {
				OUser user = ThawWebSession.get().getUser();
				return user!=null?user.getDocument():null;
			}
		});
		
		add(new WebMarkupContainer("gravatar")
		{

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				Gravatar gravatar = new Gravatar();
				gravatar.setSize(150);
				gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
				gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
				String url = gravatar.getUrl((String)getModelObject().field("email"));
				tag.put("src", url);
			}
			
		});
		add(new Label("name", new ODocumentPropertyModel<String>(getModelObject(), "name")));
		add(new Label("count", new ODocumentPropertyModel<String>(getModelObject(), "count")));
		add(new Badges("badges", getModel()));
	}
	
}
