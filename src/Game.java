class Game
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    /**
     * Create the game and initialise its internal map.
     */
    Game()
    {
        createRooms();
        parser = new Parser();
        player = new Player();
    }

    private void createRooms()
    {
        Room courtyard, cell_block, bedroom, basement, lab, mountain, cafeteria, supply_closet, break_room, medicine,
                game_room, armory, treasury, cave, arena;
      
        // main floor
        courtyard = new Room("outside the main entrance of the secret facility", false);
        cell_block = new Room("rumor has it ghosts still haunt this cell block", true);
        cell_block.setItem("handcuffs", .5);
        bedroom = new Room("a quiet bedroom", false);
        lab = new Room("in a research lab", true);
        lab.setItem("microscope", 1);
        cafeteria = new Room("in the computing admin office", true);
        cafeteria.setItem("sandwich", .1);
        supply_closet = new Room("in a damp and smelly closet with cleaning supplies", false);
        break_room = new Room("in a break room of some type... hmm...",false);
        //lower level
        basement = new Room("in a basement, it's dark and smelly", false);
        medicine = new Room("in a storage room, there are a lot of useful things here!", true);
        medicine.setItem("painkillers", .2);
        game_room = new Room("in a game room, there is the new hit game wumpus world!", false);
        armory = new Room("there are a lot of weapons here", true);
        armory.setItem("machine gun", 2.5);
        // upper level
        mountain = new Room("on a mountain, you must have been teleported", false);
        treasury = new Room("in a treasury. there has to be something worth money in here!", true);
        treasury.setItem("gold coins", .25);
        cave = new Room("in a dark cave, at least you escape the cold", false);
        arena = new Room("you enter an arena", false);
        // initialise room exits
        // direction set = N - E - S - W - UP - DOWN
        //ground level
        courtyard.setExit("north", break_room);
        courtyard.setExit("east", cell_block);
        courtyard.setExit("south", lab);
        courtyard.setExit("west", bedroom);

        cell_block.setExit("south", cafeteria);
        cell_block.setExit("west", courtyard);

        bedroom.setExit("north", supply_closet);
        bedroom.setExit("east", courtyard);
        bedroom.setExit("down", basement);

        lab.setExit("north", cell_block);
        lab.setExit("east", cafeteria);
        lab.setExit("up", mountain);

        cafeteria.setExit("north", cafeteria);
        cafeteria.setExit("west", lab);

        supply_closet.setExit("east", break_room);
        supply_closet.setExit("south", bedroom);

        break_room.setExit("south", courtyard);
        break_room.setExit("west", supply_closet);

        //lower level
        basement.setExit("east", medicine);
        basement.setExit("up", bedroom);

        medicine.setExit("east", game_room);
        medicine.setExit("south", armory);
        medicine.setExit("west", basement);

        game_room.setExit("west", medicine);

        arena.setExit("north", medicine);

        //upper level
        mountain.setExit("south", cave);
        mountain.setExit("up", lab);

        cave.setExit("north", mountain);
        cave.setExit("east", arena);
        cave.setExit("west", treasury);

        treasury.setExit("east", cave);

        arena.setExit("west", cave);
        currentRoom = courtyard;  // start game outside
    }
    /**
     *  Main play routine.  Loops until end of play.
     */
    void play()
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }


    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Wumpus World");
        System.out.println("Wumpus World is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        System.out.print(currentRoom.getExitString());
        System.out.println();
    }

    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            if (!currentRoom.getItem_bool()) {
                System.out.println("there is no item in this room");
            }
            else {
                System.out.println(currentRoom.getItemDescription());
            }
        }
        else if (commandWord.equals("grab")) {
            pickupItem();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }


        return wantToQuit;
    }

    // implementations of user commands:

    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the facility.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");
            System.out.println(currentRoom.getExitString());
            System.out.println();
        }
    }

    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    public void pickupItem()
    {
        if (currentRoom.getItem_bool() == true)
        {
            if (player.getInventorySize() < 4)
            {
                player.addItem(currentRoom.getCurrent_item());
                System.out.println("you have picked up " + currentRoom.getItemDescription());
            } else
                {
                System.out.println("inventory full");
            }

        } else {
            System.out.println("there is no item in this room!");
        }
    }
}
