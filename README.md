# ![Logo][logo_path] NoChangeTheGame
This is a client side modification that restores the feel of 1.8.9 combat on Hypixel in modern Minecraft versions.
_Please note that the modification is designed for use only on Hypixel at this time._


## 🌟 Feature Highlights
_(All features can be disabled using the config menu (requires Mod Menu) or the `nochangethegame.toml` config file)_

### ⚔️ Sword Blocking
> Right clicking while holding a sword will block, just as it does in 1.8.9.
> (This feature is automatically disabled for certain Hypixel SkyBlock)
> ![sword_comparison_small][sword_comparison]

### 👣 Sneaking Height & Interpolation 
> Sneaking height is restored to that of 1.8.9. The smooth sneaking animation is disabled too.
> ![sneak_comparison_small][sneak_comparison]

### 🏊 Swimming and Crawling
> All swimming and crawling animations are disabled.  
> _\[TODO: Add visual comparison]_

## 📋 Other Features
- Removes the blocking delay caused by high latency.
- Hides offhand hotbar slot and disable the swap hand hotkey.
- Hides held shields.
- Disables interpolation for head movements.
- Disables the enderpearl cooldown.
- Disables automatic sneaking when in low height areas.
- Hides portal background textures when switching dimensions/worlds.  
  (Use with a resource pack to restore the dirt background)

---
## ❓ Questions?
Will this modification work on Forge/NeoForge?
> Not right now, but maybe in the future.

Will this modification work on Lunar?
> Lunar appears to be incompatible with Fabric modifications written in Kotlin.
> I do not personally use Lunar so it is unlikely that a fix will be implemented into this modification.

Can I use this on other servers and/or single player?
> By default, the modification will prevent you from doing so. While this safety feature can be disabled in the config, it is not recommended.
> I'm happy to discuss requests to support other servers in issues.

Where can I submit bug reports or make feature requests?
> Please create an issue [here](https://github.com/CecerMCProjects/NoChangeTheGame/issues/new). Just make sure to have a quick check to see if someone else posted the same thing already.

---
## ⚙️ Server Information
The NoChangeTheGame modification provides a simple plugin message based protocol for servers.

### `nochangethegame:announce` (Serverbound)
> Upon connecting to a server, the modification will announce itself with this packet.
> | Type   | Value                                                |
> |--------|------------------------------------------------------|
> | Short  | The length (in bytes) of the version string          |
> | String | The UTF-8 encoded version string of the modification |

### `nochangethegame:config_override` (Clientbound)
> All modification settings can be overridden by servers with this packet.
> | Type | Value                                                   |
> |------|---------------------------------------------------------|
> | NBT  | An NBT compound tag containing each setting to override<br>(Any settings not specified in the NBT compound will not be overridden) |
>
> | Setting Key                              | Default Value | Vanilla Value |
> |------------------------------------------|---------------|---------------|
> | sneakHeight.eyeHeight                    | 1.54          | 1.27          |
> | sneakHeight.hitboxHeight                 | 1.8           | 1.5           |
> | movementInterpolation.disableForSneaking | true          | false         |
> | movementInterpolation.disableForHeads    | true          | false         |
> | swordBlocking.hideShields                | true          | false         |
> | swordBlocking.hideOffhandSlot            | true          | false         |
> | swordBlocking.animateSword               | true          | false         |
> | swordBlocking.preventCombinedAnimation   | true          | false         |
> | swordBlocking.fakeShield                 | true          | false         |
> | itemCooldowns.disableEnderpearlCooldown  | true          | false         |
> | poses.disableCrouchToFit                 | true          | false         |
> | poses.disableCrawlToFit                  | true          | false         |
> | poses.disableSwimming                    | true          | false         |
> | worldLoadingBackgrounds.disableNether    | true          | false         |
> | worldLoadingBackgrounds.disableEnd       | true          | false         |
> | dangerZone.disableOnNonHypixelServers    | true          | N/A           |
> | dangerZone.disableOnHypixelSMP           | true          | N/A           |
>
> The sword blocking animation can also be disabled on a per-itemstack basis by setting a `nochangethegame:unblockable` boolean tag to `true` on the custom data component of the item stack.
>   - Setting this tag to false currently has no effect.

### `nochangethegame:kill_switch` (Clientbound)
- Alternatively, all settings can be overridden to their vanilla values with this packet.
    | Type    | Value                                                   |
    |---------|---------------------------------------------------------|
    | Boolean | `true`  (0x01) to activate the kill switch<br> `false` (0x00) to deactivate the kill switch |

### Other information
- The kill switch and all setting overrides are cleared automatically upon disconnection.
- It is not possible to override the kill switch using setting overrides, as the kill switch takes precedence over all settings.

[logo_path]: src/main/resources/assets/nochangethegame/icon.png
[sword_comparison]: https://github.com/user-attachments/assets/c6a4a26a-6c25-4216-9bab-920b64932be3
[sneak_comparison]: https://github.com/user-attachments/assets/3220c3c0-e8e0-4c1b-80c5-9747a94128bd
