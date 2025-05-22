package com.fiap.br.challenger.application.service.utils;

public class NameFormatter {
    public static String formatName(String name) {
        if (name == null || name.isEmpty()) return name;

        String[] words = name.toLowerCase().split("\\s+");
        String[] prepositions = {"da", "de", "do", "das", "dos"};
        StringBuilder formattedName = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            boolean isPreposition = java.util.Arrays.asList(prepositions).contains(word);

            if (i == 0 || !isPreposition) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1));
            } else {
                formattedName.append(word);
            }

            if (i < words.length - 1) {
                formattedName.append(" ");
            }
        }

        return formattedName.toString();
    }
}
