/* 
 * Bibliography
 * http://www.cepis.org/upgrade/files/full-2004-V.pdf
 * A Disquisition on The Performance Behaviour of Binary Search Tree Data Structures
 */
package interiores.business.models.backtracking;

import java.util.ArrayList;

/**
 * AATree represents an AA tree of positions, sorted by the x coordinate in first place,
 * and by the y coordinate in case of tie.
 * @author nil.mamano
 */
public class AATree {

    /**
     * Node prepresents a node from an AA tree.
     */
    public class Node {

        /**
         * The root of the left subtree of the implicit parameter, which might be empty.
         */
        public Node left;
        
        /**
         * The root of the right subtree of the implicit parameter, which might be empty.
         */
        public Node right;
        
        /**
         * The parent node of the implicit parameter. 
         */
        public Node parent;
        
        /**
         * The value of the node: a discrete position in the room.
         */
        public Position info;
        
        /**
         * For balancing mechanisms to maintain height.
         */
        public int level;

        /**
         * Default constructor; the node is not part of any tree when created.
         * @param position node value.
         */
        public Node(Position position) {
            left = right = parent = null;
            level = 1;
            info = position;
        }

        @Override
        public String toString() {
            return info.toString();
        }
    }
    
    /**
     * The root node of the tree.
     */
    protected Node root;
    
    /**
     * Nodes for delete operations.
     */
    private Node last, deleted;

    
    /**
     * Add a new element with position "position" into the tree.
     *
     * @param position the content of the new node.
     */
    public void insert(Position position) {
        root = insert(position, this.root);
    }

    
    /**
     * Recursive method to insert a value into the tree.
     * Pre: there is no node in the implicit parameter with the same position as x.
     * @param r The root node of the subtree where the new node will be inserted.
     * @param x The value to be inserted.
     * @return the new root
     */
    private Node insert(Position x, Node r) {

        if (r == null)
            r = new Node(x);
        
        else if (x.isSmaller(r.info))
            r.left = insert(x, r.left);
        
        else
            r.right = insert(x, r.right);
        
        //balancing
        r = skew(r);
        r = split(r);
        return r;
    }


    /**
     * Removes a node from the tree, if it is existent.
     * 
     * @param position the content of the node to be deleted.
     */
    public void remove(Position position) {
        root = remove(position, root);
    }
       
      
     /**
     * Recursive method to delete a node from the tree, if present.
     * @param x The value of the node to be deleted.
     * @param r The root node of the subtree where the node will be deleted, if present.
     * @return the new root.
     * @throws ItemNotFoundException if x is not found.
     */

    //------------------------------------------------
    //DONE BY NIL UP TO THIS POINT. REST IS COPY PASTE
    //------------------------------------------------
    
    //this function needs work
    private Node remove(Position x, Node r) {
        if (r != null) {
            // Search the node to be deleted and set "last" and "deleted"
            last = r;
            if (x < r.info) r.left = remove(x, r.left);
            else {
                deleted = r;
                r.right = remove(x, r.right);
            }

            // If r is a leaf, if x is present, we remove it
            if (r == last) {
                if (deleted == null || x != deleted.info); //not found
                deleted.info = r.info;
                r = r.right;
            }

            // Otherwise, we are not at the bottom; rebalance
            else if (r.left.level < r.level - 1 || r.right.level < r.level - 1) {
                if (r.right.level > --r.level) r.right.level = r.level;
                r = skew(r);
                r.right = skew(r.right);
                r.right.right = skew(r.right.right);
                r = split(r);
                r.right = split(r.right);
            }
        }
        return r;
    }        
        
         
     /**
     * Balancing operation: if the node x has a left
     * horizontal link, a right rotation is performed to transform it into a
     * right horizontal link instead.
     * 
     * @param node The node that will be rebalanced.
     */
    private Node skew(Node x) {
        boolean needsSkew = x != null && x.left != null && x.level == x.left.level;
        if (needsSkew) {
            Node aux = x.left;
            x.left = aux.right;
            aux.right = x;
            return aux;
        }
        else return x;
    }
    
     /**
     * Balancing operation: if the node x has a left
     * horizontal link, a right rotation is performed to transform it into a
     * right horizontal link instead.
     * 
     * @param node The node that will be rebalanced.
     */
    private Node split(Node x) {
        boolean needsSplit = x != null && x.right != null && x.right.right != null &&
                             x.level == x.right.right.level;
        if (needsSplit) {
            Node aux = x.right;
            x.right = aux.left;
            aux.left = x;
            ++aux.level;
            return aux;
        }
        else return x;
    }
  
    /**
     * *************************** Helper Functions ***********************************
     */
    /**
     * Returns the successor of a given node in the tree (search recursivly).
     *
     * @param q The predecessor.
     * @return The successor of node q.
     */
    public Node successor(Node q) {
        if (q.right != null) {
            Node r = q.right;
            while (r.left != null) {
                r = r.left;
            }
            return r;
        } else {
            Node p = q.parent;
            while (p != null && q == p.right) {
                q = p;
                p = q.parent;
            }
            return p;
        }
    }

    /**
     * Calculating the "height" of a node.
     *
     * @param cur
     * @return The height of a node (-1, if node is not existent eg. NULL).
     */
    private int height(Node cur) {
        if (cur == null) {
            return -1;
        }
        if (cur.left == null && cur.right == null) {
            return 0;
        } else if (cur.left == null) {
            return 1 + height(cur.right);
        } else if (cur.right == null) {
            return 1 + height(cur.left);
        } else {
            return 1 + maximum(height(cur.left), height(cur.right));
        }
    }

    /**
     * Return the maximum of two integers.
     */
    private int maximum(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * Only for debugging purposes. Gives all information about a node.
     *
     * @param n The node to write information about.
     */
    public void debug(Node n) {
        Position l = 0;
        Position r = 0;
        Position p = 0;
        if (n.left != null) {
            l = n.left.info;
        }
        if (n.right != null) {
            r = n.right.info;
        }
        if (n.parent != null) {
            p = n.parent.info;
        }

        System.out.println("Left: " + l + " Key: " + n + " Right: " + r + " Parent: " + p + " Balance: " + n.level);

        if (n.left != null) {
            debug(n.left);
        }
        if (n.right != null) {
            debug(n.right);
        }
    }

    /**
     * Calculates the Inorder traversal of this tree.
     *
     * @return A Array-List of the tree in inorder traversal.
     */
    final protected ArrayList<Node> inorder() {
        ArrayList<Node> ret = new ArrayList<Node>();
        inorder(root, ret);
        return ret;
    }

    /**
     * Function to calculate inorder recursivly.
     *
     * @param n The current node.
     * @param io The list to save the inorder traversal.
     */
    final protected void inorder(Node n, ArrayList<Node> io) {
        if (n == null) {
            return;
        }
        inorder(n.left, io);
        io.add(n);
        inorder(n.right, io);
    }
}
