package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EncodeText {
    private String text;
    private Map<Character, String> dictionary = new HashMap<>();


    public EncodeText(String text){
        this.text = text;
    }


    public void writeToBinFile(String path, String encoded) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(path))) {
            dos.writeInt(dictionary.size());
            for(var entry : dictionary.entrySet()) {
                dos.writeChar(entry.getKey());
                dos.writeUTF(entry.getValue());
            }
            byte[] bytes = convertToBytes(encoded);
            dos.writeInt(bytes.length);
            dos.write(bytes);
        }
    }

    public void writeToTextFile(String path, String encoded) throws IOException {
        System.out.println("Słownik:");
        for (var entry : dictionary.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Slownik:\n");
            for (var entry : dictionary.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }

            writer.write("\nEncoded Text:\n");
            writer.write(encoded);
        }
    }

    private byte[] convertToBytes(String encodedText) {
        int lenght = (encodedText.length() + 7 ) / 8;
        byte[] byteArray = new byte[lenght];
        for(int i = 0; i < encodedText.length(); i++) {
            int byteIndex = i / 8;
            int bitIndex = 7 - (i % 8);
            if(encodedText.charAt(i) == '1') {
                byteArray[byteIndex] |= (byte) (1 << bitIndex);
            }
        }
        return byteArray;
    }

    public String encodeText(){
        buildHuffmanTree();
        StringBuilder encoded = new StringBuilder();
        for(char c : text.toCharArray()) {
            encoded.append(dictionary.get(c));
        }
        return encoded.toString();
    }
    private Map<Character, Integer> getFrequencyMap(){
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }
    private void buildHuffmanTree(){
        PriorityQueueMinHeap<Node> pq = new PriorityQueueMinHeap<>(2047);
        var frequencyMap = getFrequencyMap();

        for(var entry : frequencyMap.entrySet()) {
            pq.enqueue(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.getSize() > 1){
            var left = pq.dequeue();
            var right = pq.dequeue();

            var freq = left.getFrequency() + right.getFrequency();
            System.out.println("Łączenie węzłów: " + left.getCharacter() + " i " + right.getCharacter());
            pq.enqueue(new Node(freq, left, right));
        }
        buildDictionary(pq.dequeue(), "");
    }
    private void buildDictionary(Node node, String code){
        if (node == null) {
            return;
        }

        if(node.getLeft() == null && node.getRight() == null) {
            dictionary.put(node.getCharacter(), code);
            System.out.println("Dodano do słownika: " + node.getCharacter() + " -> " + code);
        }
        buildDictionary(node.getLeft(), code + "0");
        buildDictionary(node.getRight(), code + "1");
    }
}
