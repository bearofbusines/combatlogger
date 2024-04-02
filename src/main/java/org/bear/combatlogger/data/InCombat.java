package org.bear.combatlogger.data;

import org.bear.combatlogger.snippets.Thruple;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.*;

public class InCombat {
    //private static final ArrayList<Tetruple<UUID, Integer, Player, BossBar>> inCombat = new ArrayList<Tetruple<UUID, Integer, Player, BossBar>>();
    private static final HashMap<String, Thruple<UUID, Player, BossBar>> inCombat = new HashMap<>();

    public static boolean isInCombat(UUID uuid){
        return inCombat.get(uuid.toString()) != null;
    }

    public static boolean removeFromCombat(UUID uuid){
        InCombat.getBossBar(uuid).removeAll();
        return null != inCombat.remove(uuid.toString());
    }

    public static void putInCombat(UUID uuid, UUID combatId, Player otherPlayer, BossBar bossBar){
        inCombat.put(uuid.toString(), new Thruple<>(combatId, otherPlayer, bossBar));
    }
    
//    public static int getIndex(UUID uuid){
//        for(int i = 0; i < inCombat.size(); i++){
//            if (inCombat.get(i).x.equals(uuid)){
//                return i;
//            }
//        }
//        System.out.println("Player with UUID: " + uuid.toString() + " was not in Combat but was checked anyway Tell bear to fix his code");
//        assert true;
//        return -1;
//    }
    public static Player getOtherPlayer(UUID uuid){
        return inCombat.get(uuid.toString()).y;
    }

    public static UUID getCombatId(UUID uuid){
        return inCombat.get(uuid.toString()).x;
    }

    public static BossBar getBossBar(UUID uuid) {
        return inCombat.get(uuid.toString()).z;
    }

    public static void setCombatId(UUID uuid,UUID combatId) {
        inCombat.get(uuid.toString()).x = combatId;
    }

    public static void setBossbar(UUID uuid, BossBar bossBar) {
        inCombat.get(uuid.toString()).z = bossBar;
    }
}
