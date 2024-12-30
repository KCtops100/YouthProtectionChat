# YouthProtectionChat Plugin

YouthProtectionChat is a Bukkit plugin that helps manage roles for players based on age categories, such as "youth" and "adult." This plugin provides commands for server operators to set roles for players, list players by role, and enforce chat restrictions for adult players when no other adults are online.

## Features

- **Role Management**: Assign players as "youth" or "adult."
- **Chat Restrictions**: Adults cannot chat unless another adult is online.
- **Command List**: 
  - `/setrole <player> <youth|adult>`: Set a player’s role.
  - `/listyouth`: List all youth players online.
  - `/listadults`: List all adult players online.
  - `/listnotassigned`: List players without an assigned role.
- **Tab Completion**: Supports tab completion for the `/setrole` command.

## Installation

1. Download the `YouthProtectionChat` plugin JAR file.
2. Place the JAR file in the `plugins` folder of your Bukkit/Spigot server.
3. Restart your server or reload the plugins.

## Usage

### Commands

- **/setrole `<player>` `<youth|adult>`**  
  Assign a role to a player. Only operators can use this command.

- **/listyouth**  
  List all youth players currently online. Only operators can use this command.

- **/listadults**  
  List all adult players currently online. Only operators can use this command.

- **/listnotassigned**  
  List all players who do not have an assigned role. Only operators can use this command.

### Chat Restrictions

- If a player is set as an **adult**, they can only chat if at least one other adult is online. If there are no other adults online, the chat message will be canceled, and the player will be notified.

## Permissions

- **youthprotectionchat.setrole**: Allows setting roles for players.
- **youthprotectionchat.listyouth**: Allows listing youth players online.
- **youthprotectionchat.listadults**: Allows listing adult players online.
- **youthprotectionchat.listnotassigned**: Allows listing players without assigned roles.

## Configuration

This plugin doesn't require additional configuration. The plugin works out of the box with no setup required.

## Developer Info

- **Plugin Version**: 1.0
- **License**: MIT License
- **Author**: KCtops

## Support

If you encounter any issues or need help, feel free to open an issue on the GitHub repository.

## Changelog

### v1.0
- Initial release.
