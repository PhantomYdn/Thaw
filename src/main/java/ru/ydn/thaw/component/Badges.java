package ru.ydn.thaw.component;

import java.util.Objects;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

import ru.ydn.wicket.wicketorientdb.model.FunctionModel;
import ru.ydn.wicket.wicketorientdb.model.OQueryModel;
import ru.ydn.wicket.wicketorientdb.utils.ODocumentORIDConverter;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class Badges extends GenericPanel<ODocument>
{

	public Badges(String id, IModel<ODocument> model)
	{
		super(id, model);
		OQueryModel<ODocument> queryModel = new OQueryModel<ODocument>("select expand(distinct(engagement)) from EngagementFact where user = :user");
		queryModel.setParameter("user", new FunctionModel<ODocument, ORID>(model, ODocumentORIDConverter.INSTANCE));
		add(new ListView<ODocument>("badges", queryModel) {

			@Override
			protected void populateItem(final ListItem<ODocument> item) {
				item.add(new AttributeAppender("style", "background-color: "+Objects.toString(item.getModelObject().field("color"), "#9013FE")).setSeparator("; "));
				item.add(new WebMarkupContainer("icon")
				{

					@Override
					protected void onComponentTag(ComponentTag tag) {
						super.onComponentTag(tag);
						tag.append("class", Objects.toString(item.getModelObject().field("icon"), "default"), " ");
					}
					
				});
			}
		});
	}
	
	
	
}
