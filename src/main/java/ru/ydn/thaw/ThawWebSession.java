package ru.ydn.thaw;

import java.util.Date;
import java.util.Random;

import org.apache.wicket.Session;
import org.apache.wicket.request.Request;

import com.orientechnologies.orient.core.db.record.ODatabaseRecord;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.metadata.security.OUser;
import com.orientechnologies.orient.core.record.impl.ODocument;

import ru.ydn.wicket.wicketorientdb.OrientDbWebSession;
import ru.ydn.wicket.wicketorientdb.model.OQueryModel;
import ru.ydn.wicket.wicketorientdb.utils.DBClosure;

public class ThawWebSession extends OrientDbWebSession
{
	private static final Random RANDOM = new Random();
	private ODocument engagementFactForGuest;
	
	public ThawWebSession(Request request)
	{
		super(request);
	}
	
	public static ThawWebSession get()
	{
		return (ThawWebSession)Session.get();
	}
	
	public boolean isEngagementFactExistForGuest()
	{
		return engagementFactForGuest!=null;
	}
	
	public ODocument getEngagementFact()
	{
		ODocument engagementFact = isSignedIn()
										?(ODocument)getUser().getDocument().field("currentEngagementFact")
										:engagementFactForGuest;
		if(engagementFact==null)
		{
			engagementFact = new DBClosure<ODocument>() {
				
				protected  ODocument execute(ODatabaseRecord db)
				{
					long engagementCnt = getDatabase().getMetadata().getSchema().getClass("Engagement").count();
					int randomEngament = RANDOM.nextInt((int) engagementCnt);
					ODocument engagement = new OQueryModel<ODocument>("select from Engagement").iterator(randomEngament, 1).next();
					ODocument engagementFact = new ODocument("EngagementFact");
					engagementFact.field("engagement", engagement);
					engagementFact.field("date", new Date());
					if(isSignedIn()) engagementFact.field("user", getUser().getDocument());
					engagementFact.save();
					if(isSignedIn())
					{
						ODocument userDocument = getUser().getDocument();
						userDocument.field("currentEngagementFact", engagementFact);
						userDocument.save();
					}
					return engagementFact;
				}
			}.execute();
			if(!isSignedIn()) engagementFactForGuest = engagementFact;
			
		}
		return engagementFact;
	}
	
	public ODocument getEngagement()
	{
		return getEngagementFact().field("engagement");
	}

	@Override
	public boolean authenticate(String username, String password) {
		boolean ret = super.authenticate(username, password);
		if(ret && engagementFactForGuest!=null)
		{
			new DBClosure<Boolean>() {

				@Override
				protected Boolean execute(ODatabaseRecord db) {
					ODocument user = getUser().getDocument();
					user.field("currentEngagementFact", engagementFactForGuest);
					user.save();
					return true;
				}
			}.execute();
		}
		return ret;
	}
	
	

}
