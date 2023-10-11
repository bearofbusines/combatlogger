package org.bear.application.data;


import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class InCombat {
    private static final ArrayList<Tetruple<UUID, Integer, Player, BossBar>> inCombat = new ArrayList<Tetruple<UUID, Integer, Player, BossBar>>();
    public static boolean isInCombat(UUID uuid){
        for (Tetruple<UUID, Integer, Player, BossBar> eachEntry: inCombat) {
            if(eachEntry.x.equals(uuid))
                return true;
        }
        return false;
    }

    public static boolean removeFromCombat(UUID uuid){
        boolean succsess = false;
        for(int i = 0; i<inCombat.size();i++){
            if (inCombat.get(i).x.equals(uuid)){
                inCombat.remove(i--);
                succsess = true;
            }
        }
        return succsess;
    }

    public static void putInCombat(UUID uuid, int time, Player otherPlayer, BossBar bossBar){
        inCombat.add(new Tetruple<UUID, Integer, Player, BossBar>(uuid, time, otherPlayer, bossBar));
    }
    
    public static int getIndex(UUID uuid){
        for(int i = 0; i < inCombat.size(); i++){
            if (inCombat.get(i).x.equals(uuid)){
                return i;
            }
        }
        System.out.println("Player with UUID: " + uuid.toString() + " was not in Combat but was checked anyway Tell bear to fix his code");
        assert true;
        return -1;
    }
    public static Player getOtherPlayer(int index){
        return inCombat.get(index).z;
    }

    public static int getTime(UUID uuid){
        return inCombat.get(getIndex(uuid)).y;
    }
}
