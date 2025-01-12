package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DecodeText {
    public static void decodeFile(String encodedFile, String decodedFile) throws IOException {

        Map<Character, String> codeMap;
        byte[] encodedBytes;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(encodedFile))) {
            int mapSize = dis.readInt();
            codeMap = new HashMap<>();

            for (int i = 0; i < mapSize; i++) {
                char key = dis.readChar();
                String value = dis.readUTF();
                codeMap.put(key, value);
            }
            int encodedLength = dis.readInt();
            encodedBytes = new byte[encodedLength];
            dis.readFully(encodedBytes);
        }

        Map<String, Character> reverseCodeMap = new HashMap<>();
        for (var entry : codeMap.entrySet()) {
            reverseCodeMap.put(entry.getValue(), entry.getKey());
        }

        StringBuilder encodedText = new StringBuilder();
        for (byte b : encodedBytes) {
            for (int i = 7; i >= 0; i--) {
                encodedText.append((b >> i) & 1);
            }
        }

        StringBuilder decodedText = new StringBuilder();
        String temp = "";
        for (char bit : encodedText.toString().toCharArray()) {
            temp += bit;
            if (reverseCodeMap.containsKey(temp)) {
                decodedText.append(reverseCodeMap.get(temp));
                temp = "";
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(decodedFile))) {
            writer.write(decodedText.toString());
        }
    }
}
