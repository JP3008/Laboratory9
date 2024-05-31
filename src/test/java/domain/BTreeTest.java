package domain;

import util.Utility;
import org.junit.jupiter.api.Test;

class BTreeTest {


    @Test
    public void testing() throws TreeException {

        BTree avl= new BTree();

        for (int i = 0; i < 30 ; i++) {
            avl.add(Utility.getRandom(99));
        }

        System.out.println("Main AVL content:\n"+avl.toString());
        System.out.println("Size of AVL:"+avl.size());
        BTreeNode aux = null;
        //System.out.println("Is the main tree balanced? "+(avl.isBalanced()?"It is! :)": "It is not :("));

        for (int i = 0; i <10 ; i++) {
            int ramval =Utility.getRandom(99);
            if (avl.contains(ramval)){
                avl.remove(ramval);
            }else{
                i--;
            }
        }

        System.out.println("Modified AVL content:\n"+avl.toString());
        System.out.println("Size of AVL:"+avl.size());
       // System.out.println("Is the main tree balanced? "+(avl.isBalanced()?"It is! :)": "It is not :("));



    }

    }
