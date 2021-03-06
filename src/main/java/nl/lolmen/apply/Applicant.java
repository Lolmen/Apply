package nl.lolmen.apply;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Applicant {

    private Player p;
    private Main plugin;
    //The variables
    private String goodat;
    private String banned;
    private String name;
    private String country;
    private String age;
    private todo next;

    //Used
    public enum todo {

        GOODAT, BANNED, NAME, COUNTRY, AGE
    }
    String[] list = {ChatColor.RED + "Thank you" + ChatColor.WHITE + " for applying on the server!",
        "There are a few things we'd like to know",
        "You can just type them in chat. " + ChatColor.RED + "No-one will see it ;)",
        "First, we want to know " + ChatColor.RED + "what you are good at " + ChatColor.WHITE + "(Just type it in chat)"};
    String[] rules = {ChatColor.RED + "[1]" + ChatColor.WHITE + "Don't grief.",
        ChatColor.RED + "[2]" + ChatColor.WHITE + "Don't steal.",
        ChatColor.RED + "[3]" + ChatColor.WHITE + "Don't use bad words.",
        ChatColor.RED + "[4]" + ChatColor.WHITE + "No flying/Hacking of any kind allowed",
        ChatColor.RED + "[5]" + ChatColor.WHITE + "Minimap is allowed though.",
        ChatColor.RED + "[6]" + ChatColor.WHITE + "Don't ask for MOD, Admin or OP.",
        ChatColor.RED + "/rules" + ChatColor.WHITE + " gives a short description",
        ChatColor.RED + "Thank you " + ChatColor.WHITE + "for applying! A Moderator or Admin will now look at it"};

    public Applicant(Main m, Player p) {
        this.p = p;
        this.plugin = m;
        this.start();
    }

    private void start() {
        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("apply.check")) {
                onlinePlayer.sendMessage(ChatColor.RED + this.p.getName() + " has started the application");
            }
        }
        for (int i = 0; i < list.length; i++) {
            final int done = i;
            int time = i * 40 + 5;
            this.plugin.getServer().getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {

                public void run() {
                    p.sendMessage(list[done]);
                }
            }, time);
        }
        setNext(todo.GOODAT);
        this.plugin.getMySQL().executeQuery("INSERT INTO " + this.plugin.getSettings().getTable() + " (player) values ('" + p.getName() + "')");
    }

    public void sendRules() {
        for (int i = 0; i < rules.length; i++) {
            final int done = i;
            int time = i * 40 + 30;
            this.plugin.getServer().getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {

                public void run() {
                    p.sendMessage(rules[done]);
                }
            }, time);
        }
    }

    public String getGoodat() {
        return goodat;
    }

    public void setGoodat(String goodat) {
        this.goodat = goodat;
        this.plugin.getMySQL().executeQuery("UPDATE " + this.plugin.getSettings().getTable() + " SET goodat='" + goodat.replace("\'", "\\\'") + "' WHERE player='" + this.p.getName() + "'");
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.plugin.getMySQL().executeQuery("UPDATE " + this.plugin.getSettings().getTable() + " SET banned='" + banned.replace("\'", "\\\'") + "' WHERE player='" + this.p.getName() + "'");
        this.banned = banned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.plugin.getMySQL().executeQuery("UPDATE " + this.plugin.getSettings().getTable() + " SET name='" + name.replace("\'", "\\\'") + "' WHERE player='" + this.p.getName() + "'");
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.plugin.getMySQL().executeQuery("UPDATE " + this.plugin.getSettings().getTable() + " SET country='" + country.replace("\'", "\\\'") + "' WHERE player='" + this.p.getName() + "'");
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.plugin.getMySQL().executeQuery("UPDATE " + this.plugin.getSettings().getTable() + " SET age='" + age.replace("\'", "\\\'") + "' WHERE player='" + this.p.getName() + "'");
        this.age = age;
    }

    public todo getNext() {
        return next;
    }

    public void setNext(todo next) {
        this.next = next;
    }

    public void save() {
        for (Player p : this.plugin.getServer().getOnlinePlayers()) {
            if (p.hasPermission("apply.check")) {
                p.sendMessage(ChatColor.RED + this.p.getName() + ChatColor.WHITE + " finished the  " + ChatColor.GREEN + "application process.");
            }
        }
    }
}
