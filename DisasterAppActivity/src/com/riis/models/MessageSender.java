package com.riis.models;

public interface MessageSender
{
	public boolean sendMessage(ContactList contactList, String message);
}