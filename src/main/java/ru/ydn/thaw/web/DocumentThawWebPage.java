package ru.ydn.thaw.web;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class DocumentThawWebPage extends ThawWebPage<ODocument>
{

	public DocumentThawWebPage()
	{
		super();
	}

	public DocumentThawWebPage(IModel<ODocument> model)
	{
		super(model);
	}

	public DocumentThawWebPage(PageParameters parameters)
	{
		super(parameters);
	}

	@Override
	protected void onConfigure() {
		ODocument doc = getModelObject();
		if(doc==null)
		{
			throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
		}
		else
		{
			doc.reload();
		}
		super.onConfigure();
	}
	
	
	
}
