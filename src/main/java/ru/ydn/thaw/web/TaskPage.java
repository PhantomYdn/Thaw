package ru.ydn.thaw.web;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.thaw.ThawWebSession;
import ru.ydn.wicket.wicketorientdb.OrientDbWebSession;
import ru.ydn.wicket.wicketorientdb.model.ODocumentPropertyModel;

import com.orientechnologies.orient.core.record.impl.ODocument;

@MountPath("/task")
public class TaskPage extends ThawWebPage<ODocument>
{
	public TaskPage(IModel<ODocument> taskModel)
	{
		super(taskModel);
		add(new Label("name", new ODocumentPropertyModel<String>(taskModel, "name")));
		add(new Label("description", new ODocumentPropertyModel<String>(taskModel, "description")));
		add(new Label("content", new ODocumentPropertyModel<String>(taskModel, "content")));
		add(new Label("count", new ODocumentPropertyModel<String>(taskModel, "count")));
		add(new Link<ODocument>("doneLink") {

			@Override
			public void onClick() {
				setResponsePage(new SurveyPage(ThawWebSession.get().getEngagementFact()));
			}
		});
		add(new Link<ODocument>("tipsLink")
				{
					@Override
					public void onClick() {
						setResponsePage(new TipsPage(TaskPage.this.getModel()));
					}
				
				});
	}
	
	public TaskPage()
	{
		this(new AbstractReadOnlyModel<ODocument>() {
			@Override
			public ODocument getObject() {
				return ThawWebSession.get().getEngagement();
			}
		});
	}
}
