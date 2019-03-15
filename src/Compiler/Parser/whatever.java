package Compiler.Parser;

public class whatever {
	

		

	public static void printTree(Node root, int lvl) {
		StringBuilder sb = new StringBuilder();
		   for (int i = 0; i < lvl; i++)
		       sb.append(" ");

		   
		   if (root instanceof SimpleNode) {
			   String value = "";
			   if (((SimpleNode) root).jjtGetValue() != null)
			       value = "[" + ((SimpleNode) root).jjtGetValue().toString() + "]";
			   System.out.println(((SimpleNode) root).toString(sb.toString()) + value);
			}

		   for (int i = 0; i < root.jjtGetNumChildren(); i++) {
		       printTree(root.jjtGetChild(i), lvl + 1);
		   }
		}
		
	public static String createDotOutput(Node root) {
		   StringBuilder sb = new StringBuilder("diGraph {\n");
		   sb.append(printTree(root, ""));
		   sb.append("}");

		   return sb.toString();
		}

	public static String printTree(Node root, String dotString) {
		   StringBuilder sb = new StringBuilder(dotString);

		   for (int i = 0; i < root.jjtGetNumChildren(); i++) {
		       Node child = root.jjtGetChild(i);
		       if (child.jjtGetNumChildren() == 1)
		           continue;
		      
		       else if (child.jjtGetNumChildren() == 0 && child instanceof SimpleNode && ((SimpleNode) child).jjtGetValue() != null) {
		           sb.append("  ");
		           sb.append(root);
		           sb.append(" -> ");
		           sb.append("\"");
		           sb.append(child);
		           sb.append(" [");
		           sb.append(((SimpleNode) child).jjtGetValue());
		           sb.append("]");
		           sb.append("\"\n");
		       }
		   }

		   for (int i = 0; i < root.jjtGetNumChildren(); i++) {
		       sb.append(printTree(root.jjtGetChild(i), ""));
		   }

		   return sb.toString();
		}




}
