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
  private static final String[] INJECTS = { "members/com.riis.SendEmergencyMessageActivity", };
  private static final Class<?>[] STATIC_INJECTIONS = { };
  private static final Class<?>[] INCLUDES = { };

  public DisasterAppObjectGraph$$ModuleAdapter() {
    super(com.riis.dagger.DisasterAppObjectGraph.class, INJECTS, STATIC_INJECTIONS, false /*overrides*/, INCLUDES, true /*complete*/, false /*library*/);
  }

  @Override
  public DisasterAppObjectGraph newModule() {
    return new com.riis.dagger.DisasterAppObjectGraph();
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getBindings(BindingsGroup bindings, DisasterAppObjectGraph module) {
    bindings.contributeProvidesBinding("com.riis.models.ContactList", new ProvideContactListProvidesAdapter(module));
    bindings.contributeProvidesBinding("com.riis.models.TextMessageSender", new ProvideTextMessageSenderProvidesAdapter(module));
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

  /**
   * A {@code Binding<com.riis.models.TextMessageSender>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<com.riis.models.TextMessageSender>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideTextMessageSenderProvidesAdapter extends ProvidesBinding<com.riis.models.TextMessageSender>
      implements Provider<com.riis.models.TextMessageSender> {
    private final DisasterAppObjectGraph module;

    public ProvideTextMessageSenderProvidesAdapter(DisasterAppObjectGraph module) {
      super("com.riis.models.TextMessageSender", NOT_SINGLETON, "com.riis.dagger.DisasterAppObjectGraph", "provideTextMessageSender");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<com.riis.models.TextMessageSender>}.
     */
    @Override
    public com.riis.models.TextMessageSender get() {
      return module.provideTextMessageSender();
    }
  }
}
