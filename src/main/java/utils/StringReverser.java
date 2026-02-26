package utils;

public class StringReverser {
        /**
         * Removes duplicate characters from a string (simple version).
         * @param input The string to process.
         * @return String with duplicates removed.
         */
        public static String removeDuplicatesSimple(String input) {
            if (input == null) return null;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (result.indexOf(String.valueOf(c)) == -1) {
                    result.append(c);
                }
            }
            return result.toString();
        }
    /**
     * Reverses a string without using built-in reverse methods.
     * @param input The string to reverse.
     * @return The reversed string.
     */
    public static String reverse(String input) {
        if (input == null) return null;
        char[] chars = input.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    /**
     * Removes duplicate characters from a string while preserving order.
     * @param input The string to process.
     * @return String with duplicates removed.
     */
    public static String removeDuplicates(String input) {
        if (input == null) return null;
        java.util.Set<Character> seen = new java.util.LinkedHashSet<>();
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (seen.add(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
