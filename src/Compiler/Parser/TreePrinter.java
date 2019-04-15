package Compiler.Parser;

import Compiler.Parser.GeneratedFiles.Node;
import Compiler.Parser.GeneratedFiles.SimpleNode;

public class TreePrinter {
    private static int suffix;


    public static String createDotOutput(Node root) {
        //while (reduceTree(root));
        suffix = 0;
        ((SimpleNode) root).setName();
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
            sb.append(addValue(child.jjtGetValue().toString()));
        }
        sb.append("]\n");
    }

    private static String addValue(String value) {
        if (value.contains("."))
            return value.replace(".", "_");
        switch (value) {
            case "<":
                return "LESS";
            case ">":
                return "GREATER";
            case "<=":
                return "LESSOREQUAL";
            case ">=":
                return "GREATEROREQUAL";
            case "==":
                return "EQUALS";
            case "!=":
                return "NOTEQUALS";
            case "*":
                return "MUL";
            case "/":
                return "DIV";
            case "+":
                return "PLUS";
            case "-":
                return "MINUS";
            default:
                return value;
        }
    }

    private static void appendEdge(StringBuilder sb, SimpleNode root, SimpleNode child) {
        sb.append("  ");
        sb.append(root.getName());
        sb.append(" -> ");
        sb.append(child.getName());
        sb.append("\n");
    }

}
