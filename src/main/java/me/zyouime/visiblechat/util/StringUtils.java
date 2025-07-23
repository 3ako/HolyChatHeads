package me.zyouime.visiblechat.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<String> chunkedMessage(String input, int size) {
        ArrayList<String> lines = new ArrayList<>();
        if (!input.contains(" ")) {
            return StringUtils.chunkString(input, size);
        }
        boolean firstWord = true;
        StringBuilder builder = new StringBuilder();
        for (String word : input.split(" ")) {
            if (builder.length() + word.length() > size) {
                lines.add(builder.toString());
                builder.setLength(0);
                firstWord = true;
            }
            if (!firstWord) {
                builder.append(' ');
            } else {
                firstWord = false;
            }
            builder.append(word);
        }
        lines.add(builder.toString());
        return lines;
    }

    private static List<String> chunkString(String input, int size) {
        ArrayList<String> chunks = new ArrayList<>();
        for (int i = 0; i < input.length(); i += size) {
            chunks.add(input.substring(i, Math.min(input.length(), i + size)));
        }
        return chunks;
    }
}
