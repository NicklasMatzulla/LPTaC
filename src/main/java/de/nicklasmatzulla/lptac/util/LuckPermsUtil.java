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

package de.nicklasmatzulla.lptac.util;

import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LuckPermsUtil {

    @SuppressWarnings("NotNullFieldNotInitialized")
    @Getter
    @NotNull
    private static LuckPermsUtil instance;

    @NotNull
    private final LuckPerms luckPerms = LuckPermsProvider.get();

    public LuckPermsUtil() {
        LuckPermsUtil.instance = this;
    }

    public @Nullable User getUser(@NotNull final UUID uniqueId) {
        return this.luckPerms.getUserManager().getUser(uniqueId);
    }

    public @Nullable Group getParentGroup(@NotNull final User permissionUser) {
        return this.luckPerms.getGroupManager().getGroup(permissionUser.getPrimaryGroup());
    }

    public int getWeight(@NotNull final Group permissionGroup) {
        return permissionGroup.getWeight().orElse(0);
    }

    public @Nullable String getPrefix(@NotNull final Group permissionGroup) {
        return permissionGroup.getCachedData().getMetaData().getPrefix();
    }

    public @Nullable NamedTextColor getColor(@NotNull final Group permissionGroup) {
        final String colorName = permissionGroup.getCachedData().getMetaData().getMetaValue("color");
        if (colorName == null) {
            return null;
        }
        return NamedTextColor.NAMES.value(colorName.toLowerCase());
    }

}
