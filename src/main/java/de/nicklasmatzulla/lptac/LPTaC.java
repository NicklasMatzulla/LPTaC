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

import de.nicklasmatzulla.lptac.config.MessagesConfiguration;
import de.nicklasmatzulla.lptac.listener.*;
import de.nicklasmatzulla.lptac.util.LuckPermsUtil;
import de.nicklasmatzulla.lptac.util.Metrics;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NotNullFieldNotInitialized")
public class LPTaC extends JavaPlugin {

    @Getter
    @NotNull
    private static LPTaC instance;

    @Override
    public void onEnable() {
        LPTaC.instance = this;
        new MessagesConfiguration(this);
        new LuckPermsUtil();
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new AsyncChatListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerHideEntityListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerShowEntityListener(), this);
        new NodeMutateListener(this);
        new Metrics(this, 20466);
    }

    @Override
    public void onDisable() {
        TablistStyle.unregisterTeams();
    }
}
