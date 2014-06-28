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
public final class SendEmergencyMessageObjectGraph$$ModuleAdapter extends ModuleAdapter<SendEmergencyMessageObjectGraph> {
  private static final String[] INJECTS = { "members/com.riis.SendEmergencyMessageActivity", };
  private static final Class<?>[] STATIC_INJECTIONS = { };
  private static final Class<?>[] INCLUDES = { };

  public SendEmergencyMessageObjectGraph$$ModuleAdapter() {
    super(com.riis.dagger.SendEmergencyMessageObjectGraph.class, INJECTS, STATIC_INJECTIONS, false /*overrides*/, INCLUDES, true /*complete*/, false /*library*/);
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getBindings(BindingsGroup bindings, SendEmergencyMessageObjectGraph module) {
    bindings.contributeProvidesBinding("com.riis.controllers.TextMessageSender", new ProvideTextMessageSenderProvidesAdapter(module));
    bindings.contributeProvidesBinding("com.riis.models.ContactList", new ProvideContactListProvidesAdapter(module));
  }

  /**
   * A {@code Binding<com.riis.controllers.TextMessageSender>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.riis.controllers.TextMessageSender>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideTextMessageSenderProvidesAdapter extends ProvidesBinding<com.riis.controllers.TextMessageSender>
      implements Provider<com.riis.controllers.TextMessageSender> {
    private final SendEmergencyMessageObjectGraph module;

    public ProvideTextMessageSenderProvidesAdapter(SendEmergencyMessageObjectGraph module) {
      super("com.riis.controllers.TextMessageSender", NOT_SINGLETON, "com.riis.dagger.SendEmergencyMessageObjectGraph", "provideTextMessageSender");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.riis.controllers.TextMessageSender>}.
     */
    @Override
    public com.riis.controllers.TextMessageSender get() {
      return module.provideTextMessageSender();
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
    private final SendEmergencyMessageObjectGraph module;

    public ProvideContactListProvidesAdapter(SendEmergencyMessageObjectGraph module) {
      super("com.riis.models.ContactList", NOT_SINGLETON, "com.riis.dagger.SendEmergencyMessageObjectGraph", "provideContactList");
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
