package net.md_5.bungee.forge;

import net.md_5.bungee.UserConnection;
import net.md_5.bungee.netty.ChannelWrapper;
import net.md_5.bungee.protocol.packet.PluginMessage;

/**
 * An interface that defines a Forge Handshake Server packet.
 *
 * @param <S> The State to transition to.
 */
public interface IForgeServerPacketHandler<S>
{

    /**
     * Handles any {@link PluginMessage}
     * packets.
     *
     * @param message The {@link PluginMessage}
     * to handle.
     * @param ch The {@link ChannelWrapper} to send packets to.
     * @return The state to transition to.
     */
    public S handle(PluginMessage message, ChannelWrapper ch);

    /**
     * Sends any {@link PluginMessage} packets.
     *
     * @param message The {@link PluginMessage}
     * to send.
     * @param con The {@link UserConnection} to send packets to
     * or read from.
     * @return The state to transition to.
     */
    public S send(PluginMessage message, UserConnection con);
}
