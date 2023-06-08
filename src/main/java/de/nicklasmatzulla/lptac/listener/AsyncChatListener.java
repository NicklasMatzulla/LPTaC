/*
 * Copyright 2023 Nicklas Matzulla
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.nicklasmatzulla.lptac.listener;

import de.nicklasmatzulla.lptac.config.MessagesConfiguration;
import de.nicklasmatzulla.lptac.util.LuckPermsUtil;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class AsyncChatListener implements Listener {

    @SuppressWarnings("DataFlowIssue")
    @EventHandler
    public void onAsyncChatEvent(@NotNull final AsyncChatEvent event) {
        final Player player = event.getPlayer();
        final Component originalMessage = event.originalMessage();
        final String chatFormat = MessagesConfiguration.getInstance().CHAT_FORMAT;

        final LuckPermsUtil luckPermsUtil = LuckPermsUtil.getInstance();
        final MiniMessage miniMessage = MiniMessage.miniMessage();

        final User permissionUser = luckPermsUtil.getUser(player.getUniqueId());
        final Group parentGroup = luckPermsUtil.getParentGroup(permissionUser);
        final String prefix = luckPermsUtil.getPrefix(parentGroup);
        final NamedTextColor color = luckPermsUtil.getColor(parentGroup);

        if (chatFormat == null || prefix == null || color == null) {
            event.setCancelled(true);
            return;
        }

        event.renderer(((source, sourceDisplayName, message, viewer) -> miniMessage.deserialize(chatFormat
                        .replace("<playerName>", source.getName())
                        .replace("<groupPrefix>", prefix)
                        .replace("<groupColor>", "<" + color.asHexString() + ">")
                )
                .replaceText(builder -> builder.match("<message>").replacement(originalMessage))
        ));
    }

}
