package com.comphenix.protocol.utility;

import com.comphenix.protocol.PacketType;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.comphenix.protocol.PacketType.*;

public class GlowstoneUtil {
    private static Boolean glowstone = null;
    private static boolean injectedPacketType = false;
    private static Map<Protocol, Map<Sender, Map<Class<?>, PacketType>>> mapping;

    public static boolean isGlowstoneServer() {
        if (glowstone != null) {
            return glowstone;
        }
        try {
            Class.forName("net.glowstone.GlowServer");
            glowstone = true;
        } catch (ClassNotFoundException ex) {
            glowstone = false;
        }
        return glowstone;
    }

    public static void injectPacketType() throws ClassNotFoundException {
        if (!isGlowstoneServer()) {
            return;
        }
        if (injectedPacketType) {
            return;
        }
        injectedPacketType = true;
        mapping = Maps.newHashMap();
        for (Protocol protocol : Protocol.values()) {
            mapping.put(protocol, Maps.newHashMap());
            mapping.get(protocol).put(Sender.CLIENT, Maps.newHashMap());
            mapping.get(protocol).put(Sender.SERVER, Maps.newHashMap());
        }

        // Handshake CLIENT
        bindPacketType(Handshake.Client.SET_PROTOCOL, "Handshake", "");
        // Handshake SERVER
        // <None>

        // Status SERVER
        bindPacketType(Status.Server.SERVER_INFO, "StatusResponse", "");
        bindPacketType(Status.Server.PONG, "StatusPing", "");
        // Status CLIENT
        bindPacketType(Status.Client.START, "StatusRequest", "");
        bindPacketType(Status.Client.PING, "StatusPing", "");

        // Login SERVER
        bindPacketType(Login.Server.DISCONNECT, "net.glowstone.net.message.KickMessage");
        bindPacketType(Login.Server.ENCRYPTION_BEGIN, "EncryptionKeyRequest", "");
        bindPacketType(Login.Server.SUCCESS, "LoginSuccess", "");
        bindPacketType(Login.Server.SET_COMPRESSION, "net.glowstone.net.message.SetCompressionMessage");
        // Login CLIENT
        bindPacketType(Login.Client.START, "LoginStart", "");
        bindPacketType(Login.Client.ENCRYPTION_BEGIN, "EncryptionKeyResponse", "");

        // Play SERVER
        bindPacketType(Play.Server.SPAWN_ENTITY, "SpawnObject", "entity");
        bindPacketType(Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB, "SpawnXpOrb", "entity");
        bindPacketType(Play.Server.SPAWN_ENTITY_WEATHER, "SpawnLightningStrike", "entity");
        bindPacketType(Play.Server.SPAWN_ENTITY_LIVING, "SpawnMob", "entity");
        bindPacketType(Play.Server.SPAWN_ENTITY_PAINTING, "SpawnPainting", "entity");
        bindPacketType(Play.Server.NAMED_ENTITY_SPAWN, "SpawnPlayer", "entity");
        bindPacketType(Play.Server.ANIMATION, "AnimateEntity", "entity");
        bindPacketType(Play.Server.STATISTIC, "Statistic", "game");
        bindPacketType(Play.Server.BLOCK_BREAK_ANIMATION, "BlockBreakAnimation", "game");
        bindPacketType(Play.Server.TILE_ENTITY_DATA, "UpdateBlockEntity", "game");
        bindPacketType(Play.Server.BLOCK_ACTION, "BlockAction", "game");
        bindPacketType(Play.Server.BLOCK_CHANGE, "BlockChange", "game");
        bindPacketType(Play.Server.BOSS, "BossBar", "player");
        bindPacketType(Play.Server.SERVER_DIFFICULTY, "ServerDifficulty", "player");
        bindPacketType(Play.Server.TAB_COMPLETE, "TabCompleteResponse", "player");
        bindPacketType(Play.Server.CHAT, "Chat", "game");
        bindPacketType(Play.Server.MULTI_BLOCK_CHANGE, "MultiBlockChange", "game");
        bindPacketType(Play.Server.TRANSACTION, "Transaction", "inv");
        bindPacketType(Play.Server.CLOSE_WINDOW, "CloseWindow", "inv");
        bindPacketType(Play.Server.OPEN_WINDOW, "OpenWindow", "inv");
        bindPacketType(Play.Server.WINDOW_ITEMS, "SetWindowContents", "inv");
        bindPacketType(Play.Server.WINDOW_DATA, "WindowProperty", "inv");
        bindPacketType(Play.Server.SET_SLOT, "SetWindowSlot", "inv");
        bindPacketType(Play.Server.SET_COOLDOWN, "SetCooldown", "entity");
        bindPacketType(Play.Server.CUSTOM_PAYLOAD, "Plugin", "game");
        bindPacketType(Play.Server.CUSTOM_SOUND_EFFECT, "NamedSoundEffect", "game");
        bindPacketType(Play.Server.KICK_DISCONNECT, "net.glowstone.net.message.KickMessage");
        bindPacketType(Play.Server.ENTITY_STATUS, "EntityStatus", "entity");
        bindPacketType(Play.Server.EXPLOSION, "Explosion", "game");
        bindPacketType(Play.Server.UNLOAD_CHUNK, "UnloadChunk", "game");
        bindPacketType(Play.Server.GAME_STATE_CHANGE, "StateChange", "game");
        bindPacketType(Play.Server.KEEP_ALIVE, "Ping", "game");
        bindPacketType(Play.Server.MAP_CHUNK, "ChunkData", "game");
        bindPacketType(Play.Server.WORLD_EVENT, "PlayEffect", "game");
        bindPacketType(Play.Server.WORLD_PARTICLES, "PlayParticle", "game");
        bindPacketType(Play.Server.LOGIN, "JoinGame", "game");
        bindPacketType(Play.Server.MAP, "MapData", "game");
        // bindPacketType(Play.Server.ENTITY, "Entity", "");
        bindPacketType(Play.Server.REL_ENTITY_MOVE, "RelativeEntityPosition", "entity");
        bindPacketType(Play.Server.REL_ENTITY_MOVE_LOOK, "RelativeEntityPositionRotation", "entity");
        bindPacketType(Play.Server.ENTITY_LOOK, "EntityRotation", "entity");
        bindPacketType(Play.Server.VEHICLE_MOVE, "VehicleMove", "entity");
        bindPacketType(Play.Server.OPEN_SIGN_EDITOR, "SignEditor", "game");
        bindPacketType(Play.Server.AUTO_RECIPE, "CraftRecipeResponse", "game");
        bindPacketType(Play.Server.ABILITIES, "PlayerAbilities", "player");
        bindPacketType(Play.Server.COMBAT_EVENT, "CombatEvent", "player");
        bindPacketType(Play.Server.PLAYER_INFO, "UserListItem", "game");
        bindPacketType(Play.Server.POSITION, "PositionRotation", "game");
        bindPacketType(Play.Server.BED, "UseBed", "player");
        bindPacketType(Play.Server.RECIPES, "UnlockRecipes", "game");
        bindPacketType(Play.Server.ENTITY_DESTROY, "DestroyEntities", "entity");
        bindPacketType(Play.Server.REMOVE_ENTITY_EFFECT, "EntityRemoveEffect", "entity");
        bindPacketType(Play.Server.RESOURCE_PACK_SEND, "ResourcePackSend", "player");
        bindPacketType(Play.Server.RESPAWN, "Respawn", "game");
        bindPacketType(Play.Server.ENTITY_HEAD_ROTATION, "EntityHeadRotation", "entity");
        // bindPacketType(Play.Server.SELECT_ADVANCEMENT_TAB, "SelectAdvancementTab", "");
        bindPacketType(Play.Server.WORLD_BORDER, "WorldBorder", "game");
        bindPacketType(Play.Server.CAMERA, "Camera", "player");
        bindPacketType(Play.Server.HELD_ITEM_SLOT, "HeldItem", "inv");
        bindPacketType(Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE, "ScoreboardDisplay", "scoreboard");
        bindPacketType(Play.Server.ENTITY_METADATA, "EntityMetadata", "entity");
        bindPacketType(Play.Server.ATTACH_ENTITY, "AttachEntity", "entity");
        bindPacketType(Play.Server.ENTITY_VELOCITY, "EntityVelocity", "entity");
        bindPacketType(Play.Server.ENTITY_EQUIPMENT, "EntityEquipment", "entity");
        bindPacketType(Play.Server.EXPERIENCE, "Experience", "game");
        bindPacketType(Play.Server.UPDATE_HEALTH, "Health", "game");
        bindPacketType(Play.Server.SCOREBOARD_OBJECTIVE, "ScoreboardObjective", "scoreboard");
        bindPacketType(Play.Server.MOUNT, "SetPassenger", "entity");
        bindPacketType(Play.Server.SCOREBOARD_TEAM, "ScoreboardTeam", "scoreboard");
        bindPacketType(Play.Server.SCOREBOARD_SCORE, "ScoreboardScore", "scoreboard");
        bindPacketType(Play.Server.SPAWN_POSITION, "SpawnPosition", "game");
        bindPacketType(Play.Server.UPDATE_TIME, "Time", "game");
        bindPacketType(Play.Server.TITLE, "Title", "game");
        bindPacketType(Play.Server.NAMED_SOUND_EFFECT, "SoundEffect", "game");
        bindPacketType(Play.Server.PLAYER_LIST_HEADER_FOOTER, "UserListHeaderFooter", "game");
        bindPacketType(Play.Server.COLLECT, "CollectItem", "entity");
        bindPacketType(Play.Server.ENTITY_TELEPORT, "EntityTeleport", "entity");
        bindPacketType(Play.Server.ADVANCEMENTS, "Advancements", "player");
        bindPacketType(Play.Server.UPDATE_ATTRIBUTES, "EntityProperty", "entity");
        bindPacketType(Play.Server.ENTITY_EFFECT, "EntityEffect", "entity");
        // Play CLIENT
        bindPacketType(Play.Client.TELEPORT_ACCEPT, "TeleportConfirm", "player");
        bindPacketType(Play.Client.TAB_COMPLETE, "TabComplete", "player");
        bindPacketType(Play.Client.CHAT, "IncomingChat", "game");
        bindPacketType(Play.Client.CLIENT_COMMAND, "ClientStatus", "player");
        bindPacketType(Play.Client.SETTINGS, "ClientSettings", "game");
        bindPacketType(Play.Client.TRANSACTION, "Transaction", "inv");
        bindPacketType(Play.Client.ENCHANT_ITEM, "EnchantItem", "inv");
        bindPacketType(Play.Client.WINDOW_CLICK, "WindowClick", "inv");
        bindPacketType(Play.Client.CLOSE_WINDOW, "CloseWindow", "inv");
        bindPacketType(Play.Client.CUSTOM_PAYLOAD, "Plugin", "game");
        bindPacketType(Play.Client.USE_ENTITY, "InteractEntity", "player");
        bindPacketType(Play.Client.KEEP_ALIVE, "Ping", "game");
        bindPacketType(Play.Client.FLYING, "PlayerUpdate", "player");
        bindPacketType(Play.Client.POSITION, "PlayerPosition", "player");
        bindPacketType(Play.Client.POSITION_LOOK, "PlayerPositionLook", "player");
        bindPacketType(Play.Client.LOOK, "PlayerLook", "player");
        bindPacketType(Play.Client.VEHICLE_MOVE, "VehicleMove", "entity");
        bindPacketType(Play.Client.BOAT_MOVE, "SteerBoat", "player");
        bindPacketType(Play.Client.AUTO_RECIPE, "CraftRecipeRequest", "game");
        bindPacketType(Play.Client.ABILITIES, "PlayerAbilities", "player");
        bindPacketType(Play.Client.BLOCK_DIG, "Digging", "player");
        bindPacketType(Play.Client.ENTITY_ACTION, "PlayerAction", "player");
        bindPacketType(Play.Client.STEER_VEHICLE, "SteerVehicle", "player");
        bindPacketType(Play.Client.RECIPE_DISPLAYED, "CraftingBookData", "game");
        bindPacketType(Play.Client.RESOURCE_PACK_STATUS, "ResourcePackStatus", "player");
        bindPacketType(Play.Client.ADVANCEMENTS, "AdvancementTab", "player");
        bindPacketType(Play.Client.HELD_ITEM_SLOT, "HeldItem", "inv");
        bindPacketType(Play.Client.SET_CREATIVE_SLOT, "CreativeItem", "inv");
        bindPacketType(Play.Client.UPDATE_SIGN, "UpdateSign", "game");
        bindPacketType(Play.Client.ARM_ANIMATION, "PlayerSwingArm", "player");
        bindPacketType(Play.Client.SPECTATE, "Spectate", "player");
        bindPacketType(Play.Client.BLOCK_PLACE, "BlockPlacement", "player");
        bindPacketType(Play.Client.USE_ITEM, "UseItem", "player");
    }

    private static void bindPacketType(PacketType type, String name, String pkg) throws ClassNotFoundException {
        String className = "net.glowstone.net.message." + type.getProtocol().getGlowstoneName() + "." + (pkg.isEmpty() ? "" : (pkg + ".")) + name + "Message";
        bindPacketType(type, className);
    }

    private static void bindPacketType(PacketType type, String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        type.setGlowstoneClass(clazz);
        mapping.get(type.getProtocol()).get(type.getSender()).put(clazz, type);
    }

    public static Map<Protocol, Map<Sender, Map<Class<?>, PacketType>>> getPacketMapping() {
        return mapping;
    }

    public static Class<?> getEnumProtocolClass() {
        return MinecraftReflection.getClass("net.glowstone.net.protocol.ProtocolType");
    }
}
