package Compiler.Parser;

import Compiler.Parser.GeneratedFiles.Node;
import Compiler.Parser.GeneratedFiles.SimpleNode;

public class whatever {
    private static int suffix;


    public static String createDotOutput(Node root) {
        //while (reduceTree(root));
        suffix = 0;

        return "diGraph {\n"
                + printTree(root)
                + "}";
    }

/*    private static boolean reduceTree(Node root) {
        boolean changed = false;

        for (int i = 0; i < root.jjtGetNumChildren(); i++) {
            Node child = root.jjtGetChild(i);

            if (child instanceof SimpleNode && root instanceof SimpleNode) {
                while (child.jjtGetNumChildren() == 1) {
                    Node grandChild = child.jjtGetChild(0);
                    root.jjtAddChild(grandChild, i);  // overrides child with grandChild in root's children array
                    child = grandChild;
                    changed = true;
                }

                if (child.jjtGetNumChildren() == 0 && ((SimpleNode) child).jjtGetValue() == null) {
                    ((SimpleNode) root).jjtRemoveChild(i);
                    changed = true;
                }
            }
        }

        for (int i = 0; i < root.jjtGetNumChildren(); i++) {
            if (reduceTree(root.jjtGetChild(i)))
                changed = true;
        }

        return changed;
    }*/

    private static String printTree(Node root) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < root.jjtGetNumChildren(); i++) {
            Node child = root.jjtGetChild(i);

            if (child instanceof SimpleNode && root instanceof SimpleNode) {
                ((SimpleNode) child).setName(suffix++);

                appendLabelling(sb, (SimpleNode) child);
                appendEdge(sb, (SimpleNode) root, (SimpleNode) child);

                sb.append(printTree(root.jjtGetChild(i)));
            }
        }

        return sb.toString();
    }

    private static void appendLabelling(StringBuilder sb, SimpleNode child) {
        sb.append("  ");
        sb.append(child.getName());
        sb.append(" [label=");
        sb.append(child);
        if (child.jjtGetValue() != null) {
            sb.append("___");
            sb.append(child.jjtGetValue());
        }
        sb.append("]\n");
    }

    private static void appendEdge(StringBuilder sb, SimpleNode root, SimpleNode child) {
        sb.append("  ");
        sb.append(root.getName());
        sb.append(" -> ");
        sb.append(child.getName());
        sb.append("\n");
    }

}
