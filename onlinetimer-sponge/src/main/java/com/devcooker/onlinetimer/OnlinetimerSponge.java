package com.devcooker.onlinetimer;

import com.devcooker.onlinetimer.common.PluginLoader;
import com.devcooker.onlinetimer.handler.OnlineTimerSpongeHandler;
import com.google.inject.Inject;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.LinearComponents;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.profile.property.ProfileProperty;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The main class of your Sponge plugin.
 *
 * <p>All methods are optional -- some common event registrations are included as a jumping-off point.</p>
 */
@Plugin("onlinetimer")
public class OnlinetimerSponge extends PluginLoader {

    private final PluginContainer container;
    private final Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    @Inject
    OnlinetimerSponge(final PluginContainer container, final Logger logger) {
        this.container = container;
        this.logger = logger;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) throws IOException {
        init(configDir);
    }

    @Override
    protected void setOnlineTimerHandler() {
        super.setOnlineTimerHandler();
        onlineTimerHandler = new OnlineTimerSpongeHandler();
        Sponge.eventManager().registerListeners(this.container, onlineTimerHandler);
    }

    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        // Register a simple command
        // When possible, all commands should be registered within a command register event
        final Parameter.Value<String> nameParam = Parameter.string().key("name").build();
        event.register(this.container, Command.builder()
                .addParameter(nameParam)
                .permission("onlinetimer.command.greet")
                .executor(ctx -> {
                    final String name = ctx.requireOne(nameParam);
                    ctx.sendMessage(Identity.nil(), LinearComponents.linear(
                            NamedTextColor.AQUA,
                            Component.text("Hello "),
                            Component.text(name, Style.style(TextDecoration.BOLD)),
                            Component.text("!")
                    ));

                    return CommandResult.success();
                })
                .build(), "greet", "wave");
    }
}
