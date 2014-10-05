package ru.ydn.thaw.web;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.thaw.ThawWebSession;
import ru.ydn.wicket.wicketorientdb.model.ODocumentModel;
import ru.ydn.wicket.wicketorientdb.utils.DBClosure;

import com.orientechnologies.orient.core.db.record.ODatabaseRecord;
import com.orientechnologies.orient.core.metadata.security.OUser;
import com.orientechnologies.orient.core.record.impl.ODocument;

@MountPath("/survey")
@AuthorizeInstantiation({"admin", "writer"})
public class SurveyPage extends DocumentThawWebPage
{
	private  class ResultLink extends Link<String>
	{
		public ResultLink(String id, String result)
		{
			this(id, Model.of(result));
		}

		public ResultLink(String id, IModel<String> model)
		{
			super(id, model);
		}

		@Override
		public void onClick() {
			ODocument fact = SurveyPage.this.getModelObject();
			fact.field("result", getModelObject());
			fact.save();
			ODocument thisEngagement = ThawWebSession.get().getEngagement();
			new DBClosure<Boolean>() {

				@Override
				protected Boolean execute(ODatabaseRecord db) {
					OUser oUser = ThawWebSession.get().getUser();
					if(oUser!=null)
					{
						ODocument doc = oUser.getDocument();
						doc.field("currentEngagementFact", (Object)null);
						doc.save();
					}
					return true; 
				}
			}.execute();
			setResponsePage(new ThankPage(new ODocumentModel(thisEngagement)));
		}
		
	}
	public SurveyPage(ODocument survey)
	{
		super(new ODocumentModel(survey));
		add(new ResultLink("goodLink", "GOOD"));
		add(new ResultLink("okLink", "OK"));
		add(new ResultLink("badLink", "BAD"));
	}
}
