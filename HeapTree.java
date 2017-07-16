import java.util.Stack;

/**
 * Created by dullam on 7/13/17.
 */
public class HeapTree {
    /* Each of Node in Tree represented and it is pointing back to their respective parents*/
    private class Node<T> {
        Node left;
        Node right;
        Node parent;
        T data;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

    }

    /* Heap Tree Properties */
    /* Heap Size */

    public Node<Integer> root = null;

    public boolean isEmpty() {
        return this.root == null;
    }

    public int getSize(){
        return size(this.root);
    }

    /* Heap size is number of nodes in the tree */
    private int size(Node node) {
        if(node == null) {return 0;}
        return 1+size(node.left)+size(node.right);
    }

    /* This methodreturns the stack of respective booleans for the bit code of a given number */
    /* Example 8 = 1000   would give false, false,false,true  */
    private Stack<Boolean> binaryInStack(int n){
        Stack<Boolean> stack = new Stack<>();
        while (n!=0) {
            if(n%2 == 0)
                stack.push(false);
            else
                stack.push(true);
            n=n/2;
        }
        return stack;
    }

    /* This method returns last node of the heap
    ( Obviuosly it is left most farthest in the tree as heap tree is complete binary tree */
    /* Will find last node , when deleting a node.  */

    private Node findLastNode() {
        /* find number of nodes in the heap */
        int  size= this.getSize();
       /* to find the last node */
        Stack<Boolean> stack = this.binaryInStack(size);
       /* pop out the first node from the stock as we dont need first bit indication*/
        if(stack.size() == 0){
            return null;
        }
        Node node = this.root;
        stack.pop();

        while(stack.size()!=0) {
            if(stack.pop()) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return node;
    }


    private void insertIntoHeapTree(int data) {
        /* find number of nodes in the heap */
       int  size= this.getSize();
       /* to find where to insert the node, increase size by 1 */
       /* When inserting , we need place to insert the node, so increase heap size by 1 */
       Stack<Boolean> stack = this.binaryInStack(size+1);
       /* pop out the first node from the stock as we dont need first bit indication*/
       stack.pop();
       Node node=null;
       Node newlyInsertedNode=null;

       if(stack.size()==0) {
           node = new Node(data);
           this.root= node;
           this.root.parent=null;
           return;
       } else {
           node = this.root;
       }
       while(stack.size() > 1) {
           if(stack.pop()){
               node = node.right;
           }else {
               node = node.left;
           }
       }
       if(stack.pop()) {
           node.right = new Node(data);
           node.right.parent=node;
           newlyInsertedNode = node.right;

       }else {
           node.left = new Node(data);
           node.left.parent=node;
           newlyInsertedNode = node.left;
       }
       /*Now bubble up the node and maintain heap property */
       heapify(newlyInsertedNode);
    }

    private void heapify(Node node) {
        while(node.parent != null) {
           if((int)node.data > (int)node.parent.data) {
               swapNodeData(node,node.parent);
           }
           node = node.parent;
        }
    }

    private void swapNodeData(Node node1,Node node2) {
        int temp;
        temp = (int)node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }

   public void print(Node node) {
        if(node != null) {
            System.out.print(node.data);
        }
        if(node.left != null) {
            print(node.left);

        }
        if(node.right != null) {
           print(node.right);
        }
        System.out.println("\n");
   }

    public void inorder(Node node) {
        if(node != null) {
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
    }
    public void preorder(Node node) {
        if(node != null) {
            System.out.print(node.data + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    public void postorder(Node node) {
        if(node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.data + " ");
        }
    }

    /* This method is used to convert the partial heap to complete heap */
    private void reArrangeToHeap(Node node) {
        if(node.left != null && node.right !=null) {
            if((int)node.data < (int)node.left.data && (int)node.data < (int)node.right.data) {
                /* IF smaller than both childs */
                if((int)node.left.data > (int)node.right.data) {
                    /* left child is greater than parent */
                    this.swapNodeData(node.left,node);
                    this.reArrangeToHeap(node.left);
                } else {
                    /* right child is greater than parent */
                    this.swapNodeData(node.right,node);
                    this.reArrangeToHeap(node.right);
                }
            } else if((int)node.data < (int)node.left.data && (int)node.data > (int)node.right.data) {
                    /* left child is greater than parent and right child is smaller */
                System.out.println("Yes I am here");
                this.swapNodeData(node.left,node);
                this.reArrangeToHeap(node.left);

            } else if((int)node.data > (int)node.left.data && (int)node.data < (int)node.right.data) {
                    /* right child is greater than parent */
                this.swapNodeData(node.right,node);
                this.reArrangeToHeap(node.right);

            }
        } else if (node.left != null && node.right == null) {
            /* Only Left Child Exists*/
            if((int)node.data < (int)node.left.data) {
                this.swapNodeData(node.left,node);
            }
        }

    }

    /* Swap the root node with last node*/
    /* Remove that last node*/
    /* Re Arrange the Heap */

    private int getMax(Node lastNode) {
        if(this.root == null) return -1;
        //else
        Node node=this.root;
        int max = this.root.data;
        this.swapNodeData(this.root,lastNode);
        Node lastNodeParent = lastNode.parent;
        /*which means it is root */
        if(lastNodeParent == null) {
            lastNode=null;
            this.root =null;
            return max;
        }
        if(lastNodeParent.left == lastNode) {
            lastNodeParent.left = null;
        } else{
            lastNodeParent.right = null;
        }
        this.reArrangeToHeap(this.root);
        return max;
    }

    public static void main(String[] args) {
        System.out.println("********* Heap Tree Implementation **********");
        int[] a = {64,1,32,4,9,6,7,8,29,78,1234};
        HeapTree bt = new HeapTree();
           for(int i :a) {
               bt.insertIntoHeapTree(i);
           }
        System.out.println(" In order ");
        bt.inorder(bt.root);
        System.out.println("\n Pre order ");
        bt.preorder(bt.root);
        System.out.println("\n Post order ");
        bt.postorder(bt.root);

        System.out.println("\n *** After Heap Sort *** ");
        Node lastNode;
        while(bt.getSize() !=0) {
             lastNode = bt.findLastNode();
             System.out.print(" " + bt.getMax(lastNode));
        }
    }
}
