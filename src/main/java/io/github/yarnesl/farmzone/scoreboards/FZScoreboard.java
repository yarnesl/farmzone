package io.github.yarnesl.farmzone.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import io.github.yarnesl.farmzone.FZPlayer;

public class FZScoreboard {
    
    public static void createScoreboard(Player player, FZPlayer fzp) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        
        @SuppressWarnings("deprecation")
        Objective obj = board.registerNewObjective("FZPlayerBoard", "dummy", ChatColor.translateAlternateColorCodes('$', "$f<< $d$lFarmzone $f>>"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        Score score = obj.getScore(ChatColor.BLUE + "=-=-=-=-=-=-=-=-=-=-=-=");
        score.setScore(3);
        
        String str1 = ChatColor.DARK_GREEN + "Username: " + ChatColor.GREEN + player.getName();
        Score score1 = obj.getScore(str1);
        score1.setScore(2);
        String str2 = ChatColor.DARK_GREEN + "Rank: " + ChatColor.GREEN + fzp.getRank();
        Score score2 = obj.getScore(str2);
        score2.setScore(1);
        String str3 = ChatColor.DARK_GREEN + "Level: " + ChatColor.GREEN + fzp.getLevel();
        Score score3 = obj.getScore(str3);
        score3.setScore(0);
        String str4 = ChatColor.DARK_GREEN + "Money: " + ChatColor.GREEN + fzp.getCoins();
        Score score4 = obj.getScore(str4);
        score4.setScore(-1);
        String str5 = ChatColor.DARK_GREEN + "Exp: " + ChatColor.GREEN + fzp.getExp();
        Score score5 = obj.getScore(str5);
        score5.setScore(-2);
        
        /* Save the strings put into the scoreboard so we can update them later */
        fzp.addScoreboardEntry("username", str1);
        fzp.addScoreboardEntry("rank", str2);
        fzp.addScoreboardEntry("level", str3);
        fzp.addScoreboardEntry("coins", str4);
        fzp.addScoreboardEntry("exp", str5);
        
        player.setScoreboard(board);
    }
    
    public static void updateLevel(FZPlayer fzp) {
        Scoreboard board = fzp.getPlayer().getScoreboard();
        board.getEntries().forEach((str) -> {
            if (str.equals(fzp.getScoreboardEntry("level"))) {
                board.resetScores(fzp.getScoreboardEntry("level"));
            }
        });
        Objective obj = board.getObjective("FZPlayerBoard");
        String sc_str = ChatColor.DARK_GREEN + "Level: " + ChatColor.GREEN + fzp.getLevel();
        Score score = obj.getScore(sc_str);
        score.setScore(0);
        
        fzp.updateScoreboardEntry("level", sc_str);
        fzp.getPlayer().setScoreboard(board);
    }
    
    public static void updateExp(FZPlayer fzp) {
        Scoreboard board = fzp.getPlayer().getScoreboard();
        board.getEntries().forEach((str) -> {
            if (str.equals(fzp.getScoreboardEntry("exp"))) {
                board.resetScores(fzp.getScoreboardEntry("exp"));
            }
        });
        Objective obj = board.getObjective("FZPlayerBoard");
        String sc_str = ChatColor.DARK_GREEN + "Exp: " + ChatColor.GREEN + fzp.getExp();
        Score score = obj.getScore(sc_str);
        score.setScore(-2);
        
        fzp.updateScoreboardEntry("exp", sc_str);
        fzp.getPlayer().setScoreboard(board);
    }

}
