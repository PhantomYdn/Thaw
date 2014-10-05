package ru.ydn.thaw.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import ru.ydn.thaw.ThawWebSession;
import ru.ydn.wicket.wicketorientdb.model.ODocumentModel;
import ru.ydn.wicket.wicketorientdb.model.ODocumentPropertyModel;
import ru.ydn.wicket.wicketorientdb.utils.DBClosure;

import com.orientechnologies.orient.core.db.record.ODatabaseRecord;
import com.orientechnologies.orient.core.metadata.security.OUser;
import com.orientechnologies.orient.core.record.impl.ODocument;

@MountPath("/survey")
@AuthorizeInstantiation({"admin", "writer"})
public class SurveyPage extends DocumentThawWebPage
{
	private  class ResultLink extends AjaxSubmitLink
	{
		private String result;
		public ResultLink(String id, String result)
		{
			super(id);
			this.result = result;
		}
		
		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			ODocument fact = SurveyPage.this.getModelObject();
			fact.field("result", result);
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
		Form<ODocument> form = new Form<ODocument>("form", getModel());
		form.add(new ResultLink("goodLink", "GOOD"));
		form.add(new ResultLink("okLink", "OK"));
		form.add(new ResultLink("badLink", "BAD"));
		form.add(new TextArea<String>("feedback", new ODocumentPropertyModel<String>(getModel(), "feedback")));
		add(form);
	}
}
