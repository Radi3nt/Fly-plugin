# Contents :

- [About](#About)
- [Installation](#Installation)
- [General](#General)
  - [Commands](#Commands)
  - [Permissions](#Permissions)
- [Config](#Config)
- [Credits](#Credits)
- [Objetives](#Objectives)
- [Help](#Help)


# About

This is a fly plugin for minecraft 1.12 < 1.15.2. It allows to fly in survival gamemode.
All messages are fully configurable, you can manage to toggle you flight ability or just allow the flight ability for the others.
You can also set your flight speed or set it for the other players.

# Installation


## Installation classique

Compatibility: 1.12 - 1.15.2  
To install the plugin, please follow the following steps:

- Download the 1.12, 1.13, 1.14, 1.15 version of Spigot and launch the server once.
> Tip : You must accept the conditions of use of Mojang by modifying the file `eula.txt`
- In the server plugins folder, insert [Fly-plugin.jar](https://github.com/Radi3nt/Fly-plugin/releases).
- Restart your server then change what you want in the config.
- Connect to the server and it's done, you can use the plugin.
  - ⚠️ If you didn't setup a permission plugin, you need to be operator to use the plugin
  - ⚠️ You can install a permission plugin to use permit to players to use the plugin.
  
  
# General

### Update 1.1.0 is a big update ! You need to delete the plugin folder and relaunch your server,and it will recreate on automatically.

### Update 1.1.1 is more stable, correct a lot of bugs, add console command (with colors) and 1.12 compatibility

### Update 1.1.4 is the last and the most stable, with a lot of customisability, and sounds. Also added colors code fore prefix
#### COMING SOON: All config message with colors code.


## Commands

  - `fly [player]` - Allow to fly for yourself or another player
  - `flyspeed <1-9> [player]` - Allow to chage fly speed for yourself or another player
  - `tempfly <time in seconds>[ player]` - Allow to fly for yourself or another player **temporally**
  - `timefly [player]` - Get the time fly left
  - `flyers` - List all the player that can fly
  - `flyreload` - Simply reload the fly-plugin config
  
## Permissions

  - `fly.fly` - Allow to toggle your flight ability
  - `fly.others` - Allow to toggle the flight ability, or the flight speed of the other player (no permission needed for the targeted player)
  - `fly.tempfly` - Allow you to tempfly yourself
  - `fly.tempflyothers` - Allow you to tempfly other persons
  - `fly.timefly` - Get your time fly
  - `fly.timeflyothers` - Get the time fly of other players
  - `fly.flyers` - Allow you to get the list of the flyers
  - `fly.speed` or `fly.speed.[1-9]` - Allow to change your fly speed or that of the other players (no permission needed for the targeted player)
  - `fly.invincible` - Allow to be invincible when flying
  - `fly.damage` - Allow to do damage when flying
  
  - `fly.gamemode` - Allow to keep your flight ability when changing gamemode
  - `fly.join` - Coming soon
  - `fly.respawn` - Allow to keep your flight ability when respawn
  - `fly.changeworld` - Allow to keep your flight ability when changing world
  
  - `fly.reload` - Allow to reload the config
  
# Config

<details>
  <summary>Config</summary>
  
  ``` yaml
prefix: "Fly >"

# fly command
fly-youreself: "You toggled your fly" - message when tou toggle your own fly
fly-someone-player: "Toggled the fly" - message for the person who toggle the fly of another personn
fly-someone-target: "Toggled your fly" - message for the person that his flight ability was toggled
fly-target-message: true - define if the target receive a message when someone toggle his flight ability
fly-player-name-reval: true - define if the name of the personn who toggled the flight ability of someone is revealed




# flight speed command
speed-player-message: "You set you flight speed to" - message when tou change your own fly speed
speed-someone-player: "You set the flight speed of" - message for the person who change the fly speed of another personn
speed-target-namereval: "set your flight speed to" - message for the person that his flight speed was changed (with name reveal)
speed-target: "Set your flight speed to" - message for the person that his flight speed was changed (without name reveal)
speed-target-message: true - define if the target receive a message when someone change his flight speed
speed-player-name-reval: true - define if the name of the person who changed the flight speed of someone is revealed

#tempfly
temp-target-namereveal: "allowed you to fly for"
temp-target: "You can fly for"
temp-left: "of flight left!"

temp-hours: "hours"
temp-minutes: "minutes"
temp-seconds: "seconds"
temp-target-message: true
temp-player-name-reveal: true

temp-sound-high: BLOCK_NOTE_BLOCK_HAT
temp-sound-medium: BLOCK_NOTE_BLOCK_HAT
temp-sound-low: BLOCK_NOTE_BLOCK_HARP
temp-sound-last: BLOCK_NOTE_BLOCK_HARP
temp-sound-no: BLOCK_BELL_USE

#timefly
timefly-nofly-you: "You are not temp-flying !"
timefly-nofly-target: "This player is not tem-flying !"



invalid-player: "This player is invalid !"
no-args: "This command require an argument"
wrong-args: "Wrong argument. See the command usage"
no-permission: "You don't have the permission to use this command !"
no-damage: "You can't hit entities while flying !"
reload-message: "Configuration reloaded"


cooldown-message: "Command on cooldown for"

#I recommend to let this like that
cooldown-time: 5

reload-melody: true
credits-message: true

#DON'T CHANGE IT !
version: 1.1.4
  ```
  
</details>

# Credits

> Hmmmmmmmm not today. Maybe tomorrow ?

# Objectives

#### Plugin

- [X] Pass the 1.1.0 version of the plugin
- [X] Adding command `tempfly`
- [X] Add command `flyers`
- [ ] Add colors code for all config messages
- [ ] No big problem in it
- [ ] Have some contributors
- [ ] Used on a known server

#### Compatibility for all version of Minecraft

  - [x] 1.13 - 1.15
  - [ ] 1.11 - 1.12
  - [ ] 1.9 - 1.10
  - [ ] 1.7 - 1.8


# Help

If you need help, **__do not hesitate__** ask me !  
Report all issues you have, I will correct my code.

**Discord**: (Red_white_200#3502)[]
**Mail**: Radi3nt.plugin@gmail.com
