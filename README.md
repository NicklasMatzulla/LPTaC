# LPTaC (LuckPerms Tablist and Chat)
LPTaC is a plugin for Paper that allows you to set up a sorted tablist and chat. LPTaC is based on the LuckPerms permission system, see the setup guide for more information.

# Setup LPTaC
LPTaC is based on the LuckPerms permission system, so it is necessary to [install LuckPerms](https://luckperms.net) on your server beforehand.
1. [Download the latest version of LPTaC](https://github.com/NicklasMatzulla/LPTaC/releases/)
2. Copy the plugin into the plugins folder of your server and start your server

LPTaC reads the prefix and the nametag color of the player's parent group. For this to work the following steps are necessary for each group:
- Set the prefix for the group. (`/lp group default meta setprefix "<#87ceeb>Player <dark_gray>|"`)
- Set the nametag color for the group. (`/lp group default meta set color "blue"`)
- Optionally set the group weight to sort the tablist. As higher the value is, as higher is the rank in the tablist. (`/lp group default setweight 10`)

> **Note:**
> The prefix can contain normal Minecraft colors or HEX colors. The nametag color can only contain the normal Minecraft colors.
