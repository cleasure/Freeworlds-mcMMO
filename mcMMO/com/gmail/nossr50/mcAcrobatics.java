package com.gmail.nossr50;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class mcAcrobatics {
	private static volatile mcAcrobatics instance;
	public static mcAcrobatics getInstance() {
    	if (instance == null) {
    	instance = new mcAcrobatics();
    	}
    	return instance;
    	}
	public void acrobaticsCheck(Player player, EntityDamageEvent event, Location loc, int xx, int y, int z){   
         if(player != null && mcPermissions.getInstance().acrobatics(player)){   
               int chance = mcUsers.getProfile(player).getAcrobaticsInt() / mcLoadProperties.maxAcrobaticsLvl
               if(chance > mcLoadProperties.maxAcrobaticsChance){
                    chance = mcLoadProperties.maxAcrobaticsChance
               }
               if(Math.random() * 100 <= chance){   
                    event.setCancelled(true);   
                    player.sendMessage("**ROLLED**");   
                    return;   
               }   
          }
		if(player != null && mcUsers.getProfile(player).getAcrobaticsInt() >= mcLoadProperties.maxAcrobaticsLvl
                                && player.getHealth() - event.getDamage() <= 0)
			return;
		if(!mcConfig.getInstance().isBlockWatched(loc.getWorld().getBlockAt(xx, y, z)) 
				&& mcPermissions.getInstance().acrobatics(player)){
		mcUsers.getProfile(player).addAcrobaticsGather(event.getDamage() * 3);
		if(player != null && mcUsers.getProfile(player).getAcrobaticsGatherInt() >= mcUsers.getProfile(player).getXpToLevel("acrobatics")){
			int skillups = 0;
			while(mcUsers.getProfile(player).getAcrobaticsGatherInt() >= mcUsers.getProfile(player).getXpToLevel("acrobatics")){
				skillups++;
				mcUsers.getProfile(player).removeAcrobaticsGather(mcUsers.getProfile(player).getXpToLevel("acrobatics"));
				mcUsers.getProfile(player).skillUpAcrobatics(1);
			}
			player.sendMessage(ChatColor.YELLOW+"Acrobatics skill increased by "+skillups+"."+" Total ("+mcUsers.getProfile(player).getAcrobatics()+")");	
		}
		mcConfig.getInstance().addBlockWatch(loc.getWorld().getBlockAt(xx, y, z));
		if(player.getHealth() - event.getDamage() <= 0){
			if(mcUsers.getProfile(player).isDead())
    			return;
			mcUsers.getProfile(player).setDead(true);
		}
		}
    }
	
}
