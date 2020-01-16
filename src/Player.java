import java.util.ArrayList;

public class Player {
    private ArrayList<Item> inventory = new ArrayList<Item>();

    private String playerName;

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
       inventory.remove(item);
    }

    public int getInventorySize() {
        return inventory.size();
    }

}
