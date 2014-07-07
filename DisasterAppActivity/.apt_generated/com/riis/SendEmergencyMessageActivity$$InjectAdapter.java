// Code generated by dagger-compiler.  Do not edit.
package com.riis;

import java.util.Set;

import javax.inject.Provider;

import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;

/**
 * A {@code Binding<SendEmergencyMessageActivity>} implementation which satisfies
 * Dagger's infrastructure requirements including:
 *
 * Owning the dependency links between {@code SendEmergencyMessageActivity} and its
 * dependencies.
 *
 * Being a {@code Provider<SendEmergencyMessageActivity>} and handling creation and
 * preparation of object instances.
 *
 * Being a {@code MembersInjector<SendEmergencyMessageActivity>} and handling injection
 * of annotated fields.
 */
public final class SendEmergencyMessageActivity$$InjectAdapter extends Binding<SendEmergencyMessageActivity>
    implements Provider<SendEmergencyMessageActivity>, MembersInjector<SendEmergencyMessageActivity> {
  private Binding<com.riis.models.ContactList> contactList;
  private Binding<com.riis.controllers.textMessage.TextMessageSender> textMessageSender;

  public SendEmergencyMessageActivity$$InjectAdapter() {
    super("com.riis.SendEmergencyMessageActivity", "members/com.riis.SendEmergencyMessageActivity", NOT_SINGLETON, SendEmergencyMessageActivity.class);
  }

  /**
   * Used internally to link bindings/providers together at run time
   * according to their dependency graph.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void attach(Linker linker) {
    contactList = (Binding<com.riis.models.ContactList>) linker.requestBinding("com.riis.models.ContactList", SendEmergencyMessageActivity.class, getClass().getClassLoader());
    textMessageSender = (Binding<com.riis.controllers.textMessage.TextMessageSender>) linker.requestBinding("com.riis.controllers.textMessage.TextMessageSender", SendEmergencyMessageActivity.class, getClass().getClassLoader());
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getDependencies(Set<Binding<?>> getBindings, Set<Binding<?>> injectMembersBindings) {
    injectMembersBindings.add(contactList);
    injectMembersBindings.add(textMessageSender);
  }

  /**
   * Returns the fully provisioned instance satisfying the contract for
   * {@code Provider<SendEmergencyMessageActivity>}.
   */
  @Override
  public SendEmergencyMessageActivity get() {
    SendEmergencyMessageActivity result = new SendEmergencyMessageActivity();
    injectMembers(result);
    return result;
  }

  /**
   * Injects any {@code @Inject} annotated fields in the given instance,
   * satisfying the contract for {@code Provider<SendEmergencyMessageActivity>}.
   */
  @Override
  public void injectMembers(SendEmergencyMessageActivity object) {
    object.contactList = contactList.get();
    object.textMessageSender = textMessageSender.get();
  }

}
