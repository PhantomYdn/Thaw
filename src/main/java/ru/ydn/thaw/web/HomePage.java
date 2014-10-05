package ru.ydn.thaw.web;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.wicket.wicketorientdb.security.RequiredOrientResource;

import com.orientechnologies.orient.core.record.impl.ODocument;

@MountPath("/")
public class HomePage extends ThawWebPage
{
	public HomePage()
	{
		add(new BookmarkablePageLink<ODocument>("taskLink", TaskPage.class));
	}
}
