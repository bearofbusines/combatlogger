package org.bear.combatlogger.data;

import org.bear.combatlogger.snipets.Thruple;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.*;

public class InCombat {
    //private static final ArrayList<Tetruple<UUID, Integer, Player, BossBar>> inCombat = new ArrayList<Tetruple<UUID, Integer, Player, BossBar>>();
    private static final HashMap<UUID, Thruple<ArrayList<Integer>, Player, BossBar>> inCombat = new HashMap<>();
    public static boolean isInCombat(UUID uuid){
        return inCombat.get(uuid) != null;
    }

    public static boolean removeFromCombat(UUID uuid){
        return null != inCombat.remove(uuid);
    }

    public static void putInCombat(UUID uuid, ArrayList<Integer> runnables, Player otherPlayer, BossBar bossBar){
        inCombat.put(uuid, new Thruple<>(runnables, otherPlayer, bossBar));
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
        return inCombat.get(uuid).y;
    }

    public static ArrayList<Integer> getTaskIds(UUID uuid){
        if( inCombat.get(uuid) == null)
            return new ArrayList<>();
        return inCombat.get(uuid).x;
    }

    public static BossBar getBossBar(UUID uuid) {
        return inCombat.get(uuid).z;
    }

    public static void setRunnables(UUID uuid,ArrayList<Integer> runnables) {
        inCombat.get(uuid).x = runnables;
    }

    public static void setBossbar(UUID uuid, BossBar bossBar) {
        inCombat.get(uuid).z = bossBar;
    }
}
