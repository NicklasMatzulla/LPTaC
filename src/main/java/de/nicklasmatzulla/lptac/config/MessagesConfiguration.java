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

package de.nicklasmatzulla.lptac.config;

import de.nicklasmatzulla.lptac.LPTaC;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public class MessagesConfiguration {

    @SuppressWarnings("NotNullFieldNotInitialized")
    @Getter
    @NotNull
    private static MessagesConfiguration instance;

    private final LPTaC plugin;

    @Getter
    private FileConfiguration configuration;

    @Nullable
    public final String CHAT_FORMAT;

    public MessagesConfiguration(@NotNull final LPTaC plugin) {
        MessagesConfiguration.instance = this;
        this.plugin = plugin;
        load();

        this.CHAT_FORMAT = this.configuration.getString("chat.format");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void load() {
        final File configurationFile = new File(this.plugin.getDataFolder(), "messages.yml");
        if (!configurationFile.exists()) {
            configurationFile.getParentFile().mkdirs();
            this.plugin.saveResource("messages.yml", false);
        }

        this.configuration = new YamlConfiguration();
        try {
            this.configuration.load(configurationFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
