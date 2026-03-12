package com.narxoz.rpg.composite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();
    public PartyComposite(String name) {
        this.name = name;
    }
    public void add(CombatNode node) {
        children.add(node);
    }
    public void remove(CombatNode node) {
        children.remove(node);
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getHealth() {
        int total = 0;
        for (CombatNode node : children) {
            total += node.getHealth();
        }
        return total;
    }
    @Override
    public int getAttackPower() {
        int total = 0;
        for (CombatNode node : children) {
            if (node.isAlive()) {
                total += node.getAttackPower();
            }
        }
        return total;
    }
    @Override
    public void takeDamage(int amount) {
        List<CombatNode> alive = getAliveChildren();
        if (alive.isEmpty()) {
            return;
        }
        int split = amount / alive.size();
        for (CombatNode node : alive) {
            node.takeDamage(split);
        }
    }
    @Override
    public boolean isAlive() {
        for (CombatNode node : children) {
            if (node.isAlive()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }
    @Override
    public void printTree(String indent) {
        System.out.println(indent + "+ " + name +
                " [HP=" + getHealth() +
                ", ATK=" + getAttackPower() + "]");
        for (CombatNode node : children) {
            node.printTree(indent + "   ");
        }
    }
    private List<CombatNode> getAliveChildren() {
        List<CombatNode> alive = new ArrayList<>();
        for (CombatNode node : children) {
            if (node.isAlive()) {
                alive.add(node);
            }
        }
        return alive;
    }
}
