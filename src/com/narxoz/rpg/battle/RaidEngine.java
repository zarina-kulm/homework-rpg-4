package com.narxoz.rpg.battle;
import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;
public class RaidEngine {
    private Random random = new Random(1L);
    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }
    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        RaidResult result = new RaidResult();
        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            result.setWinner("Invalid input");
            result.addLine("Battle cannot start due to invalid inputs.");
            return result;
        }
        if (!teamA.isAlive() || !teamB.isAlive()) {
            result.setWinner("No battle");
            result.addLine("One of the teams is already defeated.");
            return result;
        }
        int round = 1;
        int maxRounds = 100;
        result.addLine("Raid started: " + teamA.getName() + " vs " + teamB.getName());
        while (teamA.isAlive() && teamB.isAlive() && round <= maxRounds) {
            result.addLine("Round " + round);
            if (teamA.isAlive()) {
                boolean critA = random.nextInt(100) < 10;
                result.addLine("Team A casts " + teamASkill.getSkillName());
                teamASkill.cast(teamB);
                if (critA) {
                    result.addLine("Critical strike by Team A!");
                }
            }
            if (teamB.isAlive()) {
                boolean critB = random.nextInt(100) < 10;
                result.addLine("Team B casts " + teamBSkill.getSkillName());
                teamBSkill.cast(teamA);
                if (critB) {
                    result.addLine("Critical strike by Team B!");
                }
            }
            round++;
        }
        result.setRounds(round - 1);
        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
        }
        result.addLine("Raid finished. Winner: " + result.getWinner());
        return result;
    }
}
