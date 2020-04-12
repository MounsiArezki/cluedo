package client.client.vue.cluedoPlateau.Key;

public enum DirectionKey {

    HORIZONTAL_DOOR('-', new boolean[] {false, true, false, true}),
    VERTICAL_DOOR('|', new boolean[] {true, false, true, false}),
    ALL(' ', new boolean[] {true, true, true, true}),
    CENTER('*', new boolean[] {true, true, true, true});

    private Character key;
    private boolean[] openLocs;

    DirectionKey(Character key, boolean[] openLocs) {
        this.openLocs = openLocs;
        this.key = key;
    }

    public Character getKey() {
        return key;
    }


    public boolean[] getOpenLocs() {
        return openLocs;
    }


    public boolean isOpen(int loc) {
        return openLocs[loc];
    }


    public static DirectionKey getDirection(char key) {
        for (DirectionKey direction : DirectionKey.values())
            if(direction.getKey() == key)
                return direction;
        return null;
    }

}
