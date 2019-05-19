package Compiler.CodeGeneration.JavaFileBuilder;

import java.util.List;

public class StringOperator {

    public static String explode(List<String> list, String separator) {
        if(list.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str);
            sb.append(separator);
        }
        return sb.toString().substring(0, sb.length() - separator.length());
    }
    
}
