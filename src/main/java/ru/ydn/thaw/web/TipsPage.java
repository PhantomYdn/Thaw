package ru.ydn.thaw.web;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.wicket.wicketorientdb.model.ODocumentModel;
import ru.ydn.wicket.wicketorientdb.model.ODocumentPropertyModel;
import ru.ydn.wicket.wicketorientdb.model.OQueryModel;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;

@MountPath("/tips/${rid}")
public class TipsPage extends DocumentThawWebPage
{
	public TipsPage(IModel<ODocument> engModel)
	{
		super(engModel);
		add(new Label("name", new ODocumentPropertyModel<String>(engModel, "name")));
		add(new Label("description", new ODocumentPropertyModel<String>(engModel, "description")));
		OQueryModel<ODocument> tipsModel = new OQueryModel<ODocument>("select from EngagementTips where engagement=:eng");
		tipsModel.setParameter("eng", engModel);
		add(new ListView<ODocument>("tips", tipsModel)
		{

			@Override
			protected void populateItem(ListItem<ODocument> item) {
				item.add(new Label("content", new ODocumentPropertyModel<String>(item.getModel(), "content")));
			}
			
		});
	}
	
	public TipsPage(PageParameters parameters)
	{
		this(new ODocumentModel(new ORecordId(parameters.get("rid").toString())));
	}
}
