package edu.miracosta.cs113;

public class HuffmanTreeDriver {
    public static void main(String[] args) {
        HuffmanTree huffmanTree = new HuffmanTree("Hello");
//        System.out.println(huffmanTree.encode("Hello"));
//        System.out.println(huffmanTree.decode(huffmanTree.encode("Hello")));
        System.out.println(huffmanTree.decode("1101110010"));
    }
}
