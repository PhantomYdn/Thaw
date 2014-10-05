package ru.ydn.thaw.web;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;

import ru.ydn.wicket.wicketorientdb.model.ODocumentPropertyModel;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class ThankPage extends DocumentThawWebPage
{
	public ThankPage(IModel<ODocument> model)
	{
		super(model);
		final IModel<String> nameModel = new ODocumentPropertyModel<String>(model, "name");
		final IModel<String> aliasModel = new ODocumentPropertyModel<String>(model, "alias");
		add(new Label("whatCompleted", nameModel));
		add(new WebMarkupContainer("tweet")
		{

			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("data-text", "I did this"+nameModel.getObject());
				tag.put("data-hashtags", aliasModel.getObject());
				
				tag.put("data-url", RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse("/")));
			}
			
		});
	}
}
