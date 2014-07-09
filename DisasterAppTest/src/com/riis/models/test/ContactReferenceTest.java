package com.riis.models.test;

import junit.framework.TestCase;

import com.riis.models.ContactReference;

public class ContactReferenceTest extends TestCase
{
	private ContactReference ref;

	public ContactReferenceTest(String name)
	{
		super(name);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		
		ref = new ContactReference(null);
	}
	
	public void testNewContactListId()
	{
		ref.setContactListId(1);
		assertEquals(1, ref.getContactListId());
	}
	
	public void testNewContactId()
	{
		ref.setContactId(1);
		assertEquals(1, ref.getContactId());
	}
	
	public void testNewNotes()
	{
		ref.setNotes(1);
		assertEquals(1, ref.getNotes());
	}
}