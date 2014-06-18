package com.riis.controllers;

import com.riis.models.ContactList;

public interface MessageSender
{
	public boolean sendMessage(ContactList contactList, String message);
}