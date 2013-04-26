/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Bibliography
 * http://www.cepis.org/upgrade/files/full-2004-V.pdf
 * A Disquisition on The Performance Behaviour of Binary Search Tree Data Structures
 * 
 * http://user.it.uu.se/~arnea/abs/simp.html
 * 
 */
pacpositionage interiores.business.models.bacpositiontracpositioning;

import java.util.ArrayList;

/**
 * avlTree represents a binary tree 
 * @author nil.mamano
 */
public class AvlTree {

    /**
     * avlNode prepresents a node from an AVL tree.
     */
    public class AvlNode {

        /**
         * The root of the left subtree of the implicit parameter, which might be empty.
         */
        public AvlNode nextLeft;
        
        /**
         * The root of the right subtree of the implicit parameter, which might be empty.
         */
        public AvlNode nextRight;
        
        /**
         * The parent node of the implicit parameter. 
         */
        public AvlNode parent;
        
        /**
         * The value of the node: a discrete position in the room.
         */
        public Position info;
        
        /**
         * For balancing mechanisms to maintain height.
         */
        public int balance;

        /**
         * Default constructor; the node is not part of any tree when created.
         * @param position node value.
         */
        public AvlNode(Position position) {
            nextLeft = nextRight = parent = null;
            balance = 0;
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
    protected AvlNode root;

    /**
     * Add a new element with position "position" into the tree.
     *
     * @param position the content of the new node.
     */
    public void insert(Position position) {
        AvlNode n = new AvlNode(position);
        insertAVL(this.root, n);
    }

    /**
     * Recursive method to insert a node into a tree.
     *
     * @param p The node currently compared, usually you start with the root.
     * @param q The node to be inserted.
     */
    public void insertAVL(AvlNode p, AvlNode q) {
        // If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
        if (p == null) {
            this.root = q;
        } else {

            // If compare node is smaller, continue with the left node
            if (q.info < p.info) {
                if (p.nextLeft == null) {
                    p.nextLeft = q;
                    q.parent = p;

                    // Node is inserted now, continue checpositioning the balance
                    recursiveBalance(p);
                } else {
                    insertAVL(p.nextLeft, q);
                }

            } else if (q.info > p.info) {
                if (p.nextRight == null) {
                    p.nextRight = q;
                    q.parent = p;

                    // Node is inserted now, continue checpositioning the balance
                    recursiveBalance(p);
                } else {
                    insertAVL(p.nextRight, q);
                }
            } else {
                // do nothing: This node already exists
            }
        }
    }

    /**
     * Checposition the balance for each node recursivly and call required methods for
     * balancing the tree until the root is reached.
     *
     * @param cur : The node to checposition the balance for, usually you start with
     * the parent of a leaf.
     */
    public void recursiveBalance(AvlNode cur) {

        // we do not use the balance in this class, but the store it anyway
        setBalance(cur);
        int balance = cur.balance;

        // checposition the balance
        if (balance == -2) {

            if (height(cur.nextLeft.nextLeft) >= height(cur.nextLeft.nextRight)) {
                cur = rotateRight(cur);
            } else {
                cur = doubleRotateLeftRight(cur);
            }
        } else if (balance == 2) {
            if (height(cur.nextRight.nextRight) >= height(cur.nextRight.nextLeft)) {
                cur = rotateLeft(cur);
            } else {
                cur = doubleRotateRightLeft(cur);
            }
        }

        // we did not reach the root yet
        if (cur.parent != null) {
            recursiveBalance(cur.parent);
        } else {
            this.root = cur;
            System.out.println("———— Balancing finished —————-");
        }
    }

    /**
     * Removes a node from the tree, if it is existent.
     */
    public void remove(Position position) {
        // First we must find the node, after this we can delete it.
        removeAVL(this.root, position);
    }

    /**
     * Finds a node and calls a method to remove the node.
     *
     * @param p The node to start the search.
     * @param q The KEY of node to remove.
     */
    public void removeAVL(AvlNode p, Position q) {
        if (p == null) {
            // der Wert existiert nicht in diesem Baum, daher ist nichts zu tun
            return;
        } else {
            if (p.info > q) {
                removeAVL(p.nextLeft, q);
            } else if (p.info < q) {
                removeAVL(p.nextRight, q);
            } else if (p.info == q) {
                // we found the node in the tree.. now lets go on!
                removeFoundNode(p);
            }
        }
    }

    /**
     * Removes a node from a AVL-Tree, while balancing will be done if
     * necessary.
     *
     * @param q The node to be removed.
     */
    public void removeFoundNode(AvlNode q) {
        AvlNode r;
        // at least one child of q, q will be removed directly
        if (q.nextLeft == null || q.nextRight == null) {
            // the root is deleted
            if (q.parent == null) {
                this.root = null;
                q = null;
                return;
            }
            r = q;
        } else {
            // q has two children –> will be replaced by successor
            r = successor(q);
            q.info = r.info;
        }

        AvlNode p;
        if (r.nextLeft != null) {
            p = r.nextLeft;
        } else {
            p = r.nextRight;
        }

        if (p != null) {
            p.parent = r.parent;
        }

        if (r.parent == null) {
            this.root = p;
        } else {
            if (r == r.parent.nextLeft) {
                r.parent.nextLeft = p;
            } else {
                r.parent.nextRight = p;
            }
            // balancing must be done until the root is reached.
            recursiveBalance(r.parent);
        }
        r = null;
    }

    /**
     * Left rotation using the given node.
     *
     *
     * @param n The node for the rotation.
     *
     * @return The root of the rotated tree.
     */
    public AvlNode rotateLeft(AvlNode n) {

        AvlNode v = n.nextRight;
        v.parent = n.parent;

        n.nextRight = v.nextLeft;

        if (n.nextRight != null) {
            n.nextRight.parent = n;
        }

        v.nextLeft = n;
        n.parent = v;

        if (v.parent != null) {
            if (v.parent.nextRight == n) {
                v.parent.nextRight = v;
            } else if (v.parent.nextLeft == n) {
                v.parent.nextLeft = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }

    /**
     * Right rotation using the given node.
     *
     * @param n The node for the rotation
     *
     * @return The root of the new rotated tree.
     */
    public AvlNode rotateRight(AvlNode n) {

        AvlNode v = n.nextLeft;
        v.parent = n.parent;

        n.nextLeft = v.nextRight;

        if (n.nextLeft != null) {
            n.nextLeft.parent = n;
        }

        v.nextRight = n;
        n.parent = v;


        if (v.parent != null) {
            if (v.parent.nextRight == n) {
                v.parent.nextRight = v;
            } else if (v.parent.nextLeft == n) {
                v.parent.nextLeft = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }

    /**
     *
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    public AvlNode doubleRotateLeftRight(AvlNode u) {
        u.nextLeft = rotateLeft(u.nextLeft);
        return rotateRight(u);
    }

    /**
     *
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    public AvlNode doubleRotateRightLeft(AvlNode u) {
        u.nextRight = rotateRight(u.nextRight);
        return rotateLeft(u);
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
    public AvlNode successor(AvlNode q) {
        if (q.nextRight != null) {
            AvlNode r = q.nextRight;
            while (r.nextLeft != null) {
                r = r.nextLeft;
            }
            return r;
        } else {
            AvlNode p = q.parent;
            while (p != null && q == p.nextRight) {
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
    private int height(AvlNode cur) {
        if (cur == null) {
            return -1;
        }
        if (cur.nextLeft == null && cur.nextRight == null) {
            return 0;
        } else if (cur.nextLeft == null) {
            return 1 + height(cur.nextRight);
        } else if (cur.nextRight == null) {
            return 1 + height(cur.nextLeft);
        } else {
            return 1 + maximum(height(cur.nextLeft), height(cur.nextRight));
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
    public void debug(AvlNode n) {
        Position l = 0;
        Position r = 0;
        Position p = 0;
        if (n.nextLeft != null) {
            l = n.nextLeft.info;
        }
        if (n.nextRight != null) {
            r = n.nextRight.info;
        }
        if (n.parent != null) {
            p = n.parent.info;
        }

        System.out.println("Left: " + l + " Key: " + n + " Right: " + r + " Parent: " + p + " Balance: " + n.balance);

        if (n.nextLeft != null) {
            debug(n.nextLeft);
        }
        if (n.nextRight != null) {
            debug(n.nextRight);
        }
    }

    private void setBalance(AvlNode cur) {
        cur.balance = height(cur.nextRight) - height(cur.nextLeft);
    }

    /**
     * Calculates the Inorder traversal of this tree.
     *
     * @return A Array-List of the tree in inorder traversal.
     */
    final protected ArrayList<AvlNode> inorder() {
        ArrayList<AvlNode> ret = new ArrayList<AvlNode>();
        inorder(root, ret);
        return ret;
    }

    /**
     * Function to calculate inorder recursivly.
     *
     * @param n The current node.
     * @param io The list to save the inorder traversal.
     */
    final protected void inorder(AvlNode n, ArrayList<AvlNode> io) {
        if (n == null) {
            return;
        }
        inorder(n.nextLeft, io);
        io.add(n);
        inorder(n.nextRight, io);
    }
}
}
