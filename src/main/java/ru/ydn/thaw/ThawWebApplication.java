package ru.ydn.thaw;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

import ru.ydn.thaw.hooks.CalculablePropertiesHook;
import ru.ydn.thaw.hooks.ReferencesConsistencyHook;
import ru.ydn.thaw.web.HomePage;
import ru.ydn.thaw.web.LoginPage;
import ru.ydn.thaw.web.RegisterPage;
import ru.ydn.wicket.wicketorientdb.OrientDbWebApplication;
import ru.ydn.wicket.wicketorientdb.OrientDbWebSession;

public class ThawWebApplication extends OrientDbWebApplication
{
	
	@Override
	protected Class<? extends OrientDbWebSession> getWebSessionClass() {
		return ThawWebSession.class;
	}

	@Override
	protected void init() {
		super.init();
		getOrientDbSettings().setDBUrl("remote:/thaw");
		getOrientDbSettings().setDBUserName("reader");
		getOrientDbSettings().setDBUserPassword("reader");
		getOrientDbSettings().setDBInstallatorUserName("admin");
		getOrientDbSettings().setDBInstallatorUserPassword("admin");
		getOrientDbSettings().getORecordHooks().add(new CalculablePropertiesHook());
		getOrientDbSettings().getORecordHooks().add(new ReferencesConsistencyHook());
		new AnnotatedMountScanner().scanPackage("ru.ydn.thaw.web").mount(this);
		getMarkupSettings().setStripWicketTags(true);
		getResourceSettings().setThrowExceptionOnMissingResource(false);
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return RegisterPage.class;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

}
