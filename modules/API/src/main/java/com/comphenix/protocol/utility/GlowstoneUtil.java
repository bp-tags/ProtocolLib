package com.comphenix.protocol.utility;

import com.comphenix.protocol.PacketType;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.comphenix.protocol.PacketType.*;
import static com.comphenix.protocol.PacketType.Play.Server.*;

public class GlowstoneUtil {
    private static Boolean glowstone = null;
    private static boolean injectedPacketType = false;
    private static Map<Class<?>, PacketType> mapping;

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
        mapping = Maps.newConcurrentMap();

        // Handshake CLIENT
        bind(Handshake.Client.SET_PROTOCOL, "Handshake", "");

        // Play SERVER
        bind(SPAWN_ENTITY, "SpawnObject", "entity");
        bind(SPAWN_ENTITY_EXPERIENCE_ORB, "SpawnXpOrb", "entity");
        bind(SPAWN_ENTITY_WEATHER, "SpawnLightningStrike", "entity");
        bind(SPAWN_ENTITY_LIVING, "SpawnMob", "entity");
        bind(SPAWN_ENTITY_PAINTING, "SpawnPainting", "entity");
        bind(NAMED_ENTITY_SPAWN, "SpawnPlayer", "entity");
        bind(ANIMATION, "AnimateEntity", "entity");
        bind(STATISTIC, "Statistic", "game");
        bind(BLOCK_BREAK_ANIMATION, "BlockBreakAnimation", "game");
        bind(TILE_ENTITY_DATA, "UpdateBlockEntity", "game");
        bind(BLOCK_ACTION, "BlockAction", "game");
        bind(BLOCK_CHANGE, "BlockChange", "game");
        bind(BOSS, "BossBar", "player");
        bind(SERVER_DIFFICULTY, "ServerDifficulty", "player");
        bind(TAB_COMPLETE, "TabCompleteResponse", "player");
        bind(CHAT, "Chat", "game");
        bind(MULTI_BLOCK_CHANGE, "MultiBlockChange", "game");
        bind(TRANSACTION, "Transaction", "inv");
        bind(CLOSE_WINDOW, "CloseWindow", "inv");
        bind(OPEN_WINDOW, "OpenWindow", "inv");
        bind(WINDOW_ITEMS, "SetWindowContents", "inv");
        bind(WINDOW_DATA, "WindowProperty", "inv");
        bind(SET_SLOT, "SetWindowSlot", "inv");
        bind(SET_COOLDOWN, "SetCooldown", "entity");
        bind(CUSTOM_PAYLOAD, "Plugin", "game");
        bind(CUSTOM_SOUND_EFFECT, "NamedSoundEffect", "game");
        bind(KICK_DISCONNECT, "net.glowstone.net.message.KickMessage");
        bind(ENTITY_STATUS, "EntityStatus", "entity");
        bind(EXPLOSION, "Explosion", "game");
        bind(UNLOAD_CHUNK, "UnloadChunk", "game");
        bind(GAME_STATE_CHANGE, "StateChange", "game");
        bind(KEEP_ALIVE, "Ping", "game");
        bind(MAP_CHUNK, "ChunkData", "game");
        bind(WORLD_EVENT, "PlayEffect", "game");
        bind(WORLD_PARTICLES, "PlayParticle", "game");
        bind(LOGIN, "JoinGame", "game");
        bind(MAP, "MapData", "game");
        // bind(ENTITY, "Entity", "");
        bind(REL_ENTITY_MOVE, "RelativeEntityPosition", "entity");
        bind(REL_ENTITY_MOVE_LOOK, "RelativeEntityPositionRotation", "entity");
        bind(ENTITY_LOOK, "EntityRotation", "entity");
        bind(VEHICLE_MOVE, "VehicleMove", "entity");
        bind(OPEN_SIGN_EDITOR, "SignEditor", "game");
        bind(AUTO_RECIPE, "CraftRecipeResponse", "game");
        bind(ABILITIES, "PlayerAbilities", "player");
        bind(COMBAT_EVENT, "CombatEvent", "player");
        bind(PLAYER_INFO, "UserListItem", "game");
        bind(POSITION, "PositionRotation", "game");
        bind(BED, "UseBed", "player");
        bind(RECIPES, "UnlockRecipes", "game");
        bind(ENTITY_DESTROY, "DestroyEntities", "entity");
        bind(REMOVE_ENTITY_EFFECT, "EntityRemoveEffect", "entity");
        bind(RESOURCE_PACK_SEND, "ResourcePackSend", "player");
        bind(RESPAWN, "Respawn", "game");
        bind(ENTITY_HEAD_ROTATION, "EntityHeadRotation", "entity");
        // bind(SELECT_ADVANCEMENT_TAB, "SelectAdvancementTab", "");
        bind(WORLD_BORDER, "WorldBorder", "game");
        bind(CAMERA, "Camera", "player");
        bind(HELD_ITEM_SLOT, "HeldItem", "inv");
        bind(SCOREBOARD_DISPLAY_OBJECTIVE, "ScoreboardDisplay", "scoreboard");
        bind(ENTITY_METADATA, "EntityMetadata", "entity");
        bind(ATTACH_ENTITY, "AttachEntity", "entity");
        bind(ENTITY_VELOCITY, "EntityVelocity", "entity");
        bind(ENTITY_EQUIPMENT, "EntityEquipment", "entity");
        bind(EXPERIENCE, "Experience", "game");
        bind(UPDATE_HEALTH, "Health", "game");
        bind(SCOREBOARD_OBJECTIVE, "ScoreboardObjective", "scoreboard");
        bind(MOUNT, "SetPassenger", "entity");
        bind(SCOREBOARD_TEAM, "ScoreboardTeam", "scoreboard");
        bind(SCOREBOARD_SCORE, "ScoreboardScore", "scoreboard");
        bind(SPAWN_POSITION, "SpawnPosition", "game");
        bind(UPDATE_TIME, "Time", "game");
        bind(TITLE, "Title", "game");
        bind(NAMED_SOUND_EFFECT, "SoundEffect", "game");
        bind(PLAYER_LIST_HEADER_FOOTER, "UserListHeaderFooter", "game");
        bind(COLLECT, "CollectItem", "entity");
        bind(ENTITY_TELEPORT, "EntityTeleport", "entity");
        bind(ADVANCEMENTS, "Advancements", "player");
        bind(UPDATE_ATTRIBUTES, "EntityProperty", "entity");
        bind(ENTITY_EFFECT, "EntityEffect", "entity");
    }

    private static void bind(PacketType type, String name, String pkg) throws ClassNotFoundException {
        String className = "net.glowstone.net.message." + type.getProtocol().getGlowstoneName() + "." + (pkg.isEmpty() ? "" : (pkg + ".")) + name + "Message";
        bind(type, className);
    }

    private static void bind(PacketType type, String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        type.setGlowstoneClass(clazz);
        mapping.put(clazz, type);
    }

    public static Map<Class<?>, PacketType> getPacketMapping() {
        return mapping;
    }
}
