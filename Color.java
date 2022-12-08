public enum Color {
    GREEN(0), RED(1), WHITE(2), YELLOW(3), ORANGE(4), BLUE(5), None(6);

    private final int value;
    private Color(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public static Color getColor(int value){
        for (Color e: Color.values()) {
            if(e.getValue() == value)
                return e;
        }
        return Color.None;
    }
}
