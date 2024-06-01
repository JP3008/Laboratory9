package domain;

import util.Utility;
import org.junit.jupiter.api.Test;

class BSTTest {




    @Test
    public void testing() throws TreeException {
        BST bst = new BST();

        BTree btreeNums = new BTree();


        for (int i = 0; i < 30; i++) {
            int ramval =Utility.getRandom(500);
            if (i==0){
                btreeNums.add(ramval);
            }

            if (btreeNums.contains(ramval) && i!=0){
                i--;
            }else{
                btreeNums.add(ramval);
            }
        }
        bst.add(btreeNums);


        String[] alpha = {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };

        BST BSTAlpha = new BST();

          for (int i = 0; i < 27 ; i++) {
            BSTAlpha.add(alpha[i].toUpperCase());
        }

          bst.add(BSTAlpha);

        BTree bTreeNames = new BTree();

        String[] names = {"Carlito", "Gabrielito", "Manolito", "Rafaelito", "Samuelito",
                "Anita", "Marianita", "Laurita", "Camilita", "Rosita"};


        for (int i = 0; i <10 ; i++) {
            bTreeNames.add(names[i]);
        }

        bst.add(bTreeNames);

        System.out.println("Original BST content:\n"+bst.toString()+"\n");
        System.out.println("--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--");
        System.out.println("Size of tree:"+bst.size()+"\n");
        System.out.println("--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--");

        System.out.println("Minimal value of main tree"+bst.min()+"\n");
        System.out.println("Maximal value of main tree"+bst.max()+"\n");


        System.out.println("Contains test:\n");

        for (int i = 0; i <5 ; i++) {
            int ramval =Utility.getRandom(500);
            if (btreeNums.contains(ramval)){
                System.out.println("The value ["+ramval+"] exists in BTreeNums! :)");

            }else{
                System.out.println("The value does not ["+ramval+"] exist in BTreeNums! :( ");
            }

        }

        for (int i = 0; i < 5; i++) {
            int ramval = Utility.getRandom(26);
            if (BSTAlpha.contains(alpha[ramval])) {
                System.out.println("Letter "+alpha[ramval].toUpperCase() + " exists in BSTAlpha! :)");
            } else {
                System.out.println("Letter "+alpha[ramval].toUpperCase() + " doesnt exist in BSTAlpha! :(");
            }

        }

        String[] otherNames = {"Carlito", "Gabrielito", "Manolito", "Rafaelito", "Samuelito",
                "Pablito", "Danielito", "Miguelito", "Emilito", "Marcelito",
                "Anita", "Marianita", "Laurita", "Camilita", "Rosita",
                "Lupita", "Violeta", "Isabelita", "Sarita", "Antonita"};

        for (int i = 0; i < 5; i++) {
            int ramval = Utility.getRandom(19);
            if (bTreeNames.contains(otherNames[ramval])) {
                System.out.println("Name "+otherNames[ramval]+" exists in BTreeNames! :)");
            } else {
                System.out.println("Name "+otherNames[ramval]+" doesnt exist in BTreeNames! :(");
            }

        }


        for (int i = 0; i < 10; i++) {
            int ramval =Utility.getRandom(20);
            if (i==0){
                bst.add(ramval);
            }

            if (bst.contains(ramval) && i!=0){
                i--;
            }else{
                bst.add(ramval);
            }
        }

        System.out.println("Is the main tree balanced? "+(bst.isBalanced()?"It is! :)": "It is not :("));
        System.out.println("\n removing binary trees");
        bst.remove(btreeNums);
        bst.remove(bTreeNames);
        bst.remove(BSTAlpha);
        for (int i = 0; i < 2; i++) {
            int ramval =Utility.getRandom(19);

            if (bst.contains(ramval)){
                System.out.println("Removing number "+ramval);
                bst.remove(ramval);
            }else{
                i--;
            }
        }
        System.out.println("+\nModified tree:");
        System.out.println("--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--");
        System.out.println(bst.toString());
        System.out.println("--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--o--");
        System.out.println("Is the main tree balanced? "+(bst.isBalanced()?"It is! :)": "It is not :("));
        System.out.println(" ");

        System.out.println("\nPreorder of the items with each of their height");
        System.out.println(bst.getListHeight());
    }
}