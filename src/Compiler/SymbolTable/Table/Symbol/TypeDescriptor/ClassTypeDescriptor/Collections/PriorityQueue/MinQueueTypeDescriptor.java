package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;

public class MinQueueTypeDescriptor extends PriorityQueueTypeDescriptor {
    private TypeDescriptor elementType;

    public MinQueueTypeDescriptor(TypeDescriptor elementType) {
        super(elementType);
        this.elementType = elementType;
        this.addMethods();
    }

    private void addMethods(){
        addMethod(minimum());
        addMethod(extractMin());
        addMethod(increaseKey());
    }

    private Method minimum(){
        return new Method("minimum", elementType, new ArrayList<>());
    }

    private Method extractMin(){
        return new Method("extractMin", elementType, new ArrayList<>());
    }

    private Method increaseKey(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);
        parameters.add(new IntegerTypeDescriptor());
        return new Method("increaseKey", new BooleanTypeDescriptor(), parameters);
    }

    @Override
    public String getTypeName() {
        return "MinQueue";
    }
}
