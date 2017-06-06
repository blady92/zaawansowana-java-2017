package ships.controller;

public final class JSONUtils {

    private JSONUtils() {
    }

    /**
     * Checks if string is valid json
     * @param json
     * @return
     */
    public static boolean checkValidity(String json) {
        int opens = 0;
        int closes = 0;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                opens++;
            }
            if (c == '}') {
                closes++;
            }
        }
        return opens == closes;
    }

}
