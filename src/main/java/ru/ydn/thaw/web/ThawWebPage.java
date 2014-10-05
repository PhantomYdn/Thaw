package ru.ydn.thaw.web;

import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ThawWebPage<T> extends GenericWebPage<T>
{

	public ThawWebPage()
	{
		super();
	}

	public ThawWebPage(IModel<T> model)
	{
		super(model);
	}

	public ThawWebPage(PageParameters parameters)
	{
		super(parameters);
	}
	
	public IModel<String> getTitleModel() {
		return new ResourceModel("default.title");
	}

}
