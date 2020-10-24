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

//            int i = 0;
//            while (input.contains(Character.toString(c)))
//            {
//                if (input.charAt(i) == c)
//                {
//                    input = input.substring(0, i) + input.substring(i + 1);
//                }
//                i++;
//            }

            for (int i = 0; i < weight; i++)
            {
                input = input.replace(Character.toString(c), "");
            }

//            try{
//                input = input.replaceAll(Character.toString(c), "");
//            }
//            catch (PatternSyntaxException e)
//            {
//                input = input.substring(1);
//            }

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
            subTree1.setIsLeft(true);
            BinaryTree<Character> subTree2 = queue.poll();

            BinaryTree<Character> rootTree = new BinaryTree<>(null, subTree1, subTree2);

            subTree1.setParentTree(rootTree);
            subTree2.setParentTree(rootTree);

            rootTree.setWeight(subTree1.getWeight() + subTree2.getWeight());

            queue.add(rootTree);
        }

        huffmanTree = queue.poll();
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
                if (current.getLeftSubtree().isLeaf())
                {
                    decodedMessage += current.getLeftSubtree().getData();
                    current = huffmanTree;
                }
                else
                {
                    current = current.getLeftSubtree();
                }
//                current = current.getLeftSubtree();
            }
            else if (codedMessage.charAt(i) == '1')
            {
                if (current.getRightSubtree().isLeaf())
                {
                    decodedMessage += current.getRightSubtree().getData();
                    current = huffmanTree;
                }
                else
                {
                    current = current.getRightSubtree();
                }
//                current = current.getRightSubtree();
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

            encodedMessage += recursiveEncode(huffmanTree, c);
        }
        return encodedMessage;
    }

    public String recursiveEncode(BinaryTree<Character> current, char c)
    {
        String encodedMessage ="";
        if (current.isLeaf() && current.getData() == c)
        {
            while (current.getParentTree() != null)
            {
                if (current.getIsLeft())
                {
                    encodedMessage = 0 + encodedMessage;
                }
                else
                {
                    encodedMessage = 1 + encodedMessage;
                }
                current = current.getParentTree();
            }
            return encodedMessage;
        }
        else
        {
            if (current.getLeftSubtree() != null && encodedMessage == "")
            {
                encodedMessage = recursiveEncode(current.getLeftSubtree(), c);
            }
            if (current.getRightSubtree() != null && encodedMessage == "")
            {
                encodedMessage = recursiveEncode(current.getRightSubtree(), c);
            }
        }

        return encodedMessage;
    }
}
