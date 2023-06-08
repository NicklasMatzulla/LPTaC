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

package de.nicklasmatzulla.lptac;

import de.nicklasmatzulla.lptac.util.LuckPermsUtil;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

public class TablistStyle {

    @SuppressWarnings("DataFlowIssue")
    public static void refresh(@NotNull final Player player) {
        final LuckPermsUtil luckPermsUtil = LuckPermsUtil.getInstance();
        final MiniMessage miniMessage = MiniMessage.miniMessage();

        final User permissionUser = luckPermsUtil.getUser(player.getUniqueId());
        final Group parentGroup = luckPermsUtil.getParentGroup(permissionUser);
        final String name = parentGroup.getName();
        final String prefix = luckPermsUtil.getPrefix(parentGroup);
        final NamedTextColor color = luckPermsUtil.getColor(parentGroup);
        final int weight = luckPermsUtil.getWeight(parentGroup);
        final String teamName = (100 - weight) + name;

        if (prefix == null || color == null) {
            return;
        }

        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
            team.prefix(miniMessage.deserialize(prefix));
            team.color(color);
        }

        player.setScoreboard(scoreboard);
        team.addPlayer(player);
    }

    public static void remove(@NotNull final Player player) {
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        final Team team = scoreboard.getPlayerTeam(player);
        if (team != null) {
            team.removePlayer(player);
        }
    }

    public static void unregisterTeams() {
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (final Team team : scoreboard.getTeams()) {
            team.unregister();
        }
    }

}
