import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Room
{
    private String description;
    private HashMap<String, Room> exits;
    private Item current_item;
    private boolean item;
    //private ArrayList<Item> items = new ArrayList<Item>;

    public Room(String description, boolean item)
    {
        this.description = description;
        exits = new HashMap<>();
        this.item = item;

    }

    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
       return exits.get(direction);
    }

    public void setItem(String n, double w) {
        current_item = new Item(n, w);
    }

    public boolean getItem_bool() {
       return item;
    }
    public Item getCurrent_item() {
        return current_item;
    }

    public String getItemDescription() {
        return current_item.getName();
    }

    public String getDescription()
    {
        return description;
    }
    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
}
