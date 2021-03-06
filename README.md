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

This is a fly plugin for minecraft 1.12 < 1.15.2. It allows to fly in survival gamemode. All messages are fully configurable, you can manage to toggle you flight ability or just allow the flight ability for the others. You can also set your flight speed or set it for the other players. There is also a /tempfly that allow player to temp-fly **-** Console commands. **THERE IS ALSO A GUI !**

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

### Update 1.2.0 is the new update, with more precision and a awsome GUI !

### Update 1.2.3a is a pre-release. **This a preview of the 1.3.0 update !**



## Commands

  - `fly [player]` - Allow to fly for yourself or another player
  - `flyspeed <1-9> [player]` - Allow to chage fly speed for yourself or another player
  - `tempfly <time in seconds>[ player]` - Allow to fly for yourself or another player **temporally**
  - `timefly [player]` - Get the time fly left
  - `flyalert` Toggle your tempfly notifications (And for admins to toggle dust)
  - `flyers` - List all the player that can fly
  - `flygui` - Open you the fly GUI !
  - `flyupdate` - Update check the plugin
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
  
  - `fly.gui` - Allow opening the gui (default perm to use)
  
  - `fly.admin` - Admin Stuff (Dust)
  
  - `fly.gamemode` - Allow to keep your flight ability when changing gamemode
  - `fly.join` - Allow players to keep their flight ability when reconnecting
  - `fly.respawn` - Allow to keep your flight ability when respawn
  - `fly.changeworld` - Allow to keep your flight ability when changing world
  
  - `fly.reload` - Allow to reload the config
  
# Config

<details>
  <summary>Config</summary>
  
  ``` yaml
#  ______  _         ______  _                _
#  |  ___|| |        | ___ \| |              (_)
#  | |_   | | _   _  | |_/ /| | _   _   __ _  _  _ __
#  |  _|  | || | | | |  __/ | || | | | / _` || || '_ \
#  | |    | || |_| | | |    | || |_| || (_| || || | | |
#  \_|    |_| \__, | \_|    |_| \__,_| \__, ||_||_| |_|
#              __/ |                    __/ |
#             |___/                    |___/
#
#-----------------------
# Developer: Radi3nt
#-----------------------
#
# //ca// = color code accepted
# //ce// = color code forbidden
# non-specified = color code forbidden


#//ca//
prefix: "&6&lFly >"

#Fly
#//ca//
fly-yourself: "You toggled your fly %state%"
fly-someone-player: "Toggled the fly %state% for &b%target%"
fly-someone-target: "Toggled your fly %state% by &b%player%"
fly-target-message: true




#Flight speed command
#//ca//
speed-player-message: "You set your flight speed to &b&l%speed%"
speed-someone-player: "You set the flight speed of &2&l%target%&r to &b&l%speed%"
speed-target: "Set your flight speed to &b&l%speed%&r by &2&l%player%"
speed-target-message: true


#tempfly
#//ca//
tempfly-message: "%target% can fly for &b&l%hours%&r hours, &b&l%minutes%&r minutes and &b&l%seconds%&r seconds"
tempfly-target: "%player% allowed you to fly for &b&l%hours%&r hours, &b&l%minutes%&r minutes and &b&l%seconds%&r seconds"
tempfly-player: "You can fly for &b&l%hours%&r hours, &b&l%minutes%&r minutes and &b&l%seconds%&r seconds"

tempfly-target-message: true

timefly-high: "&b&l%hours%&r hours, &b&l%minutes%&r minutes, and &b&l%seconds%&r seconds of flight left"
timefly-medium: "&b&l%minutes%&6 minutes, and &b&l%seconds%&6 seconds of flight left"
timefly-low: "&4&l%seconds%&c seconds of flight left"

tempfly-timeleft: "&cTime left:"
tempfly-notimeleft: "&c&lNo time left!"

temp-sound-high: BLOCK_NOTE_BLOCK_HAT
temp-sound-medium: BLOCK_NOTE_BLOCK_HAT
temp-sound-low: BLOCK_NOTE_BLOCK_HARP
temp-sound-last: BLOCK_NOTE_BLOCK_HARP
temp-sound-no: BLOCK_BELL_USE








#timefly
#//ce//
timefly-nofly-you: "You are not temp-flying!"
timefly-nofly-target: "This player is not tem-flying!"


#//ca//
timefly-structure: "&b&l%hours%&r hours, &b&l%minutes%&r minutes, and &b&l%seconds%&r seconds of flight left"





#Flyers
#//ca//
flyers-noone: "No one has the permission to fly!"





#Flyalert
#//ca//
flyalert-chat: "&cChat %state%"
flyalert-title: "&cTitle %state%"
flyalert-sounds: "&cSounds %state%"
flyalert-all: "&cAll %state%"




#All of this is in RED so //ce//
invalid-player: "This player is invalid!"
no-args: "This command require an argument"
wrong-args: "Wrong argument. See the command usage"
no-permission: "You don't have the permission to use this command!"
no-damage: "You can't hit entities while flying!"
reload-message: "Configuration reloaded"


#//ca//
e-state: "&2&lenabled"
d-state: "&c&ldisabled"
on-state: "&2&lon"
off-state: "&c&loff"




#//ca//
cooldown-message: "&cCommand on cooldown for &b&l%timeleft%&c seconds"
#I recommend to let this like that
cooldown-time: 5

reload-melody: true
credits-message: true

#DON'T CHANGE IT !
version: 1.2.3g
  ```
  
</details>

# Credits

> Hmmmmmmmm not today. Maybe tomorrow ?

# Objectives

#### Plugin

- [X] Pass the 1.1.0 version of the plugin
- [X] Adding command `tempfly`
- [X] Add command `flyers`
- [X] Add colors code for all config messages
- [ ] Finish config messages
- [X] Add a bossbar
- [ ] No big problem in it
- [ ] Have some contributors
- [ ] Used on a known server

#### Compatibility for all version of Minecraft

  - [x] 1.13 - 1.15
  - [x] 1.12
  - [ ] 1.11
  - [ ] 1.10
  - [ ] 1.9 
  - [ ] 1.8
  - [ ] 1.7


# Help

If you need help, **__do not hesitate__** ask me !  
Report all issues you have, I will correct my code.

**Discord**: Red_white_200#3502  
**Mail**: Radi3nt.plugin@gmail.com
