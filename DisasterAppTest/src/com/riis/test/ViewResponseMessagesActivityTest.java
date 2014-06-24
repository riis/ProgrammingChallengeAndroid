package com.riis.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

import com.riis.R;
import com.riis.ViewResponseMessagesActivity;
import com.riis.controllers.ResponseMessagesAdapter;
import com.riis.models.Contact;
import com.riis.models.ContactList;
import com.riis.models.ResponseMessage;
import com.riis.models.ResponseMessageList;

public class ViewResponseMessagesActivityTest extends ActivityInstrumentationTestCase2<ViewResponseMessagesActivity>
{
	private ViewResponseMessagesActivity viewResponseMessagesActivity;
	private Button returnToMainScreenButton;
	private ListView responseMessagesListView;
	
	private Contact contact;
	private Context context;
	
	public ViewResponseMessagesActivityTest() 
	{
		super(ViewResponseMessagesActivity.class);
	}
	
	protected void setUp() throws Exception 
	{
		super.setUp();
		context = this.getInstrumentation()
				.getTargetContext().getApplicationContext();
		viewResponseMessagesActivity = getActivity();
		
		returnToMainScreenButton = (Button) viewResponseMessagesActivity.findViewById(R.id.returnToMainScreenButton);
		responseMessagesListView = (ListView) viewResponseMessagesActivity.findViewById(R.id.responseMessagesListView);
		
		contact = new Contact(context);
		contact.setFirstName("Robert");
		contact.setLastName("Jones");
		contact.setEmailAddress("bjones@example.com");
		contact.setPhoneNumber("5555555555");
//		contact.setMessageSentTimeStamp(50);
	}
	
	public void testListViewPopulates() 
	{
		viewResponseMessagesActivity.runOnUiThread(new Runnable() 
		{	
			@Override
			public void run() 
			{
				ResponseMessage response = new ResponseMessage(context);
	        	response.setPhoneNumber("5555555555");
	        	response.setTextMessageContents("This is a test");
	        	response.updateMessageSentTimeStamp();
	        	response.create();
	    		
				contact.create();
				
				ContactList contactList = new ContactList(context);
				contactList.readAllContacts();
				responseMessagesListView.setAdapter(new ResponseMessagesAdapter(viewResponseMessagesActivity.
						getApplicationContext(), contactList.getContacts()));
			}
		});
		
		try 
		{
			Thread.sleep(5000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertTrue(responseMessagesListView.getCount() > 0);
		
		ResponseMessage response = new ResponseMessage(context);
    	response.setPhoneNumber("5555555555");
    	response.setTextMessageContents("This is a test");
    	response.updateMessageSentTimeStamp();
				
		contact.read();
		contact.delete();
		response.read();
		response.delete();		
	}
	
	public void testDeleteContact()
	{
		ResponseMessage message = new ResponseMessage(context);
		message.updateMessageSentTimeStamp();
		message.setTextMessageContents("This is a test message.");
		message.setPhoneNumber("5555555555");
		
		message.create();
		
		assertTrue(message.delete());
	}
	
	public void testResponseMessagesListViewExists() 
	{
		assertNotNull(responseMessagesListView);
	}
	
	public void testReturnToMainScreenButtonExists() 
	{
		assertNotNull(returnToMainScreenButton);
	}
	
	public void testSortingContactByTimeStamp()
	{
//		Contact secondContact = new Contact(context);
//		secondContact.setFirstName("Frank");
//		secondContact.setLastName("Benjonmin");
//		secondContact.setEmailAddress("FB@example.com");
//		secondContact.setPhoneNumber("9315550066");
//		secondContact.setMessageSentTimeStamp(1000);
//		
//		Contact thirdContact = new Contact(context);
//		thirdContact.setFirstName("Darrell");
//		thirdContact.setLastName("Willis");
//		thirdContact.setEmailAddress("DW@example.com");
//		thirdContact.setPhoneNumber("9995556666");
//		thirdContact.setMessageSentTimeStamp(700);
//		
//		thirdContact.create();
//		contact.create();
//		secondContact.create();
//		
//		ContactList contactList = new ContactList(context);
//		contactList.readByTimeStamp();
//		
//		contact.delete();
//		secondContact.delete();
//		thirdContact.delete();
//		
//		assertEquals(50L,contactList.getContact(0).getMessageSentTimeStamp());
//		assertEquals(700L,contactList.getContact(1).getMessageSentTimeStamp());
//		assertEquals(1000L,contactList.getContact(2).getMessageSentTimeStamp());
	}
	
	public void testSortingResponseMessageByTimeStamp() 
	{
		ResponseMessage response = new ResponseMessage(context);
    	response.setPhoneNumber("5555555555");
    	response.setTextMessageContents("This is a test");
    	response.setTimeStamp(40);
    	response.create();
    	
    	ResponseMessage secondResponse = new ResponseMessage(context);
    	secondResponse.setPhoneNumber("1115550011");
    	secondResponse.setTextMessageContents("This is another test");
    	secondResponse.setTimeStamp(1000);
    	secondResponse.create();
    	
    	ResponseMessageList responseMessages = new ResponseMessageList(context);
    	responseMessages.read();
    	response.delete();
		secondResponse.delete();
		
		assertTrue(responseMessages.getResponseMessage(0).getTimeStamp() >
			responseMessages.getResponseMessage(1).getTimeStamp());    
	}
	
	public void testDeleteResponseMessage()
	{
		ResponseMessage newMessage = new ResponseMessage(context);
		newMessage.setPhoneNumber("5550009090");
		newMessage.setTextMessageContents("random contents");
		newMessage.setTimeStamp(500L);
		newMessage.create();
		
		assertTrue(newMessage.delete());
	}
	
	public void testCreateResponseMessage()
	{
		ResponseMessage newMessage = new ResponseMessage(context);
		newMessage.setPhoneNumber("5550009090");
		newMessage.setTextMessageContents("random contents");
		newMessage.setTimeStamp(50L);
		newMessage.create();
		
		ResponseMessageList messageList = new ResponseMessageList(context);
		messageList.read();
		ResponseMessage output = messageList.getResponseMessage(messageList.size() - 1);
		newMessage.delete();
		
		assertEquals(output.getTextMessageContents(), newMessage.getTextMessageContents());
		assertEquals(output.getTimeStamp(), newMessage.getTimeStamp());
		assertEquals(output.getPhoneNumber(), newMessage.getPhoneNumber());
	}
	
	public void testReadResponseMessage()
	{
		ResponseMessage newMessage = new ResponseMessage(context);
		newMessage.setPhoneNumber("5550009090");
		newMessage.setTextMessageContents("random contents");
		newMessage.setTimeStamp(50L);
		
		newMessage.create();
		
		ResponseMessage output = new ResponseMessage(context);
		output.setPhoneNumber("5550009090");

		assertTrue(output.read());
		
		newMessage.delete();
	}
	
	public void testUpdateContact()
	{
		ResponseMessage newMessage = new ResponseMessage(context);
		newMessage.setPhoneNumber("5550009090");
		newMessage.setTextMessageContents("random contents");
		newMessage.setTimeStamp(50L);
		
		newMessage.create();
		
		newMessage.setTimeStamp(70L);
		newMessage.update();
		
		ResponseMessage output = new ResponseMessage(context);
		output.setPhoneNumber("5550009090");
		output.read();
		
		newMessage.delete();
		
		assertEquals(newMessage.getTimeStamp(), output.getTimeStamp());
	}
}