package domain;

import util.Utility;
import org.junit.jupiter.api.Test;

class BSTTest {




    @Test
    public void testing() throws TreeException {
        BST bst = new BST();

        BTree btreeNums = new BTree();

        for (int i = 0; i < 100 ; i++) {
            btreeNums.add(Utility.getRandom(500));
        }

        bst.add(btreeNums);

        String[] alpha = {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };

        BST BSTAlpha = new BST();

        for (int i = 0; i < 26 ; i++) {
            BSTAlpha.add(alpha[i]);
        }

        bst.add(BSTAlpha);

        BTree bTreeNames = new BTree();

        String[] names = {"Carlito", "Gabrielito", "Manolito", "Rafaelito", "Samuelito",
                "Pablito", "Danielito", "Miguelito", "Emilito", "Marcelito"
        };

        for (int i = 0; i <10 ; i++) {
            bTreeNames.add(names[i]);
        }

        bst.add(bTreeNames);

        System.out.println("Original BST content:\n"+bst.toString());
        System.out.println("Size of tree:"+bst.size());
        System.out.println("Minimal value of main tree"+bst.min());
        System.out.println("Maximal value of main tree"+bst.max());

        for (int i = 0; i < 5 ; i++) {
            int ramval =Utility.getRandom(500);
            String getLetter = alpha[Utility.getRandom(26)];
            String getName = names[Utility.getRandom(9)];
            System.out.println("Is the number [" + ramval + "] contained in the BTNumbers? " + (btreeNums.contains(ramval) ? "it is! :)" : "it is not :("));
            System.out.println("^v^^v^^v^^v^^v^^v^^v^^v^^v^");
            System.out.println("Is the letter [" + getLetter + "] contained in the BTSletters? " + (btreeNums.contains(getLetter) ? "it is! :)" : "it is not :("));
            System.out.println("^v^^v^^v^^v^^v^^v^^v^^v^^v^");
            System.out.println("Is the name [" + getName + "] contained in the BTNames? " + (btreeNums.contains(getName) ? "it is! :)" : "it is not :("));

        }

        for (int i = 0; i < 10 ; i++) {
            bst.add(Utility.getRandom(20));
        }

        System.out.println("Is the main tree balanced? "+(bst.isBalanced()?"It is! :)": "It is not :("));

        for (int i = 0; i <10 ; i++) {
            int ramval =Utility.getRandom(20);
            if (bst.contains(ramval)){
                bst.remove(ramval);
            }else{
                i--;
            }

        }
        System.out.println(bst.toString());
        System.out.println("Is the main tree balanced? "+(bst.isBalanced()?"It is! :)": "It is not :("));

    }
}