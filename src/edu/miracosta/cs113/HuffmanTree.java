package edu.miracosta.cs113;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.regex.PatternSyntaxException;

public class HuffmanTree implements HuffmanInterface
{
    private BinaryTree<Character> huffmanTree;

    // Will take in a String and build a HuffmanTree using the BinaryTree class
    // Will iterate through the string and count the occurance of each character
    // Will store a node with weight and character into a Priority Queue
    // Will use the Priority Queue to construct the Huffman Tree
    public HuffmanTree(String input)
    {
        PriorityQueue<BinaryTree<Character>> queue = new PriorityQueue<>(new Comparator<BinaryTree<Character>>() {
            @Override
            public int compare(BinaryTree<Character> o1, BinaryTree<Character> o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });

        while (!input.isEmpty())
        {
            char c = input.charAt(0);
//            if (c == '\\')
//            {
//                c += input.charAt(1);
//            }

            BinaryTree<Character> subTree = new BinaryTree<Character>(c, null, null);

            int weight = 0;
            for (int j = 0; j < input.length(); j++)
            {
                if (input.charAt(j) == c)
                {
                    weight++;
                }
            }

            subTree.setWeight(weight);

            try{
                input = input.replaceAll(Character.toString(c), "");
            }
            catch (PatternSyntaxException e)
            {
                input = "";
            }

            queue.add(subTree);
        }

//        while (!queue.isEmpty())
//        {
//            BinaryTree<Character> element = queue.poll();
//            System.out.println("Element: " + element + " Weight: " + element.getWeight());
//        }

        // Builds Huffman Tree using Priority Queue
        while (queue.size() > 1)
        {
            BinaryTree<Character> subTree1 = queue.poll();
            BinaryTree<Character> subTree2 = queue.poll();

            BinaryTree<Character> rootTree = new BinaryTree<>(null, subTree1, subTree2);

            rootTree.setWeight(subTree1.getWeight() + subTree2.getWeight());

            queue.add(rootTree);
        }

        huffmanTree = queue.poll();
//        System.out.println(huffmanTree.toString());
    }

    // Will take in a string and decode the message using our constructed
    @Override
    public String decode(String codedMessage) {
        String decodedMessage = "";
        BinaryTree<Character> current = huffmanTree;
        for (int i = 0; i < codedMessage.length(); i++)
        {
            if (codedMessage.charAt(i) == '0')
            {
                if (current.isLeaf())
                {
                    decodedMessage += current.getData();
                    current = huffmanTree;
                }
                else
                {
                    current = current.getLeftSubtree();
                }
            }
            else if (codedMessage.charAt(i) == '1')
            {
                if (current.isLeaf())
                {
                    decodedMessage += current.getData();
                    current = huffmanTree;
                }
                else
                {
                    current = current.getRightSubtree();
                }
            }
        }
        return decodedMessage;
    }

    @Override
    public String encode(String message) {
        String encodedMessage = "";
        for (int i = 0; i < message.length();  i++)
        {
            char c = message.charAt(i);

            encodedMessage += recursiveEncode(huffmanTree, encodedMessage, c);
        }
        return encodedMessage;
    }

    public String recursiveEncode(BinaryTree<Character> current, String encodedMessage, char c)
    {
        if (current.isLeaf() && current.getData() == c)
        {
            return encodedMessage;
        }
        else
        {
            if (current.getLeftSubtree() != null)
            {
                recursiveEncode(current.getLeftSubtree(), encodedMessage + 0, c);
            }
            if (current.getRightSubtree() != null)
            {
                encodedMessage = recursiveEncode(current.getRightSubtree(), encodedMessage + 1, c);
            }
        }

        return encodedMessage;
    }
}
