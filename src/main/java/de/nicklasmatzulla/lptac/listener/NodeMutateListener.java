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

import de.nicklasmatzulla.lptac.LPTaC;
import de.nicklasmatzulla.lptac.TablistStyle;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NodeMutateListener {

    public NodeMutateListener(@NotNull final LPTaC plugin) {
        final LuckPerms luckPerms = LuckPermsProvider.get();
        final EventBus eventBus = luckPerms.getEventBus();
        eventBus.subscribe(plugin, NodeMutateEvent.class, this::onNodeMutateEvent);
    }

    public void onNodeMutateEvent(@NotNull final NodeMutateEvent event) {
        if (!event.isUser()) {
            return;
        }

        final User user = (User) event.getTarget();
        final Player player = Bukkit.getPlayer(user.getUniqueId());
        if (player == null) {
            return;
        }

        TablistStyle.remove(player);
        TablistStyle.refresh(player);
    }

}
