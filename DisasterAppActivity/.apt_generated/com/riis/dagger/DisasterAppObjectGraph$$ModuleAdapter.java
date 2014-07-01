// Code generated by dagger-compiler.  Do not edit.
package com.riis.dagger;

import dagger.internal.BindingsGroup;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import javax.inject.Provider;

/**
 * A manager of modules and provides adapters allowing for proper linking and
 * instance provision of types served by {@code @Provides} methods.
 */
public final class DisasterAppObjectGraph$$ModuleAdapter extends ModuleAdapter<DisasterAppObjectGraph> {
  private static final String[] INJECTS = { "members/com.riis.DisasterAppActivity", "members/com.riis.controllers.contactListDisplay.ContactListDisplayAdapter", };
  private static final Class<?>[] STATIC_INJECTIONS = { };
  private static final Class<?>[] INCLUDES = { };

  public DisasterAppObjectGraph$$ModuleAdapter() {
    super(com.riis.dagger.DisasterAppObjectGraph.class, INJECTS, STATIC_INJECTIONS, false /*overrides*/, INCLUDES, true /*complete*/, false /*library*/);
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getBindings(BindingsGroup bindings, DisasterAppObjectGraph module) {
    bindings.contributeProvidesBinding("com.riis.models.ResponseMessageList", new ProvideResponseMessageListProvidesAdapter(module));
    bindings.contributeProvidesBinding("com.riis.models.ListOfContactLists", new ProvideListOfContactListsProvidesAdapter(module));
    bindings.contributeProvidesBinding("com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener", new ProvideMessageIndicatorItemClickListenerProvidesAdapter(module));
    bindings.contributeProvidesBinding("com.riis.models.ContactList", new ProvideContactListProvidesAdapter(module));
  }

  /**
   * A {@code Binding<com.riis.models.ResponseMessageList>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.riis.models.ResponseMessageList>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideResponseMessageListProvidesAdapter extends ProvidesBinding<com.riis.models.ResponseMessageList>
      implements Provider<com.riis.models.ResponseMessageList> {
    private final DisasterAppObjectGraph module;

    public ProvideResponseMessageListProvidesAdapter(DisasterAppObjectGraph module) {
      super("com.riis.models.ResponseMessageList", NOT_SINGLETON, "com.riis.dagger.DisasterAppObjectGraph", "provideResponseMessageList");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.riis.models.ResponseMessageList>}.
     */
    @Override
    public com.riis.models.ResponseMessageList get() {
      return module.provideResponseMessageList();
    }
  }

  /**
   * A {@code Binding<com.riis.models.ListOfContactLists>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.riis.models.ListOfContactLists>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideListOfContactListsProvidesAdapter extends ProvidesBinding<com.riis.models.ListOfContactLists>
      implements Provider<com.riis.models.ListOfContactLists> {
    private final DisasterAppObjectGraph module;

    public ProvideListOfContactListsProvidesAdapter(DisasterAppObjectGraph module) {
      super("com.riis.models.ListOfContactLists", NOT_SINGLETON, "com.riis.dagger.DisasterAppObjectGraph", "provideListOfContactLists");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.riis.models.ListOfContactLists>}.
     */
    @Override
    public com.riis.models.ListOfContactLists get() {
      return module.provideListOfContactLists();
    }
  }

  /**
   * A {@code Binding<com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideMessageIndicatorItemClickListenerProvidesAdapter extends ProvidesBinding<com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener>
      implements Provider<com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener> {
    private final DisasterAppObjectGraph module;

    public ProvideMessageIndicatorItemClickListenerProvidesAdapter(DisasterAppObjectGraph module) {
      super("com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener", NOT_SINGLETON, "com.riis.dagger.DisasterAppObjectGraph", "provideMessageIndicatorItemClickListener");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener>}.
     */
    @Override
    public com.riis.controllers.contactListDisplay.ContactListDisplayItemClickListener get() {
      return module.provideMessageIndicatorItemClickListener();
    }
  }

  /**
   * A {@code Binding<com.riis.models.ContactList>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.riis.models.ContactList>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideContactListProvidesAdapter extends ProvidesBinding<com.riis.models.ContactList>
      implements Provider<com.riis.models.ContactList> {
    private final DisasterAppObjectGraph module;

    public ProvideContactListProvidesAdapter(DisasterAppObjectGraph module) {
      super("com.riis.models.ContactList", NOT_SINGLETON, "com.riis.dagger.DisasterAppObjectGraph", "provideContactList");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.riis.models.ContactList>}.
     */
    @Override
    public com.riis.models.ContactList get() {
      return module.provideContactList();
    }
  }
}
