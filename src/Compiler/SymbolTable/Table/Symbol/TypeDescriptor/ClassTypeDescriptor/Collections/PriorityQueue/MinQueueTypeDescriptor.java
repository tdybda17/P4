package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.*;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;

public class MinQueueTypeDescriptor extends PriorityQueueTypeDescriptor {
    public MinQueueTypeDescriptor(TypeDescriptor elementType) {
        super(elementType);
        this.addMethods();
    }

    private void addMethods(){
        addMethod(minimum());
        addMethod(extractMin());
        addMethod(decreaseKey());
    }

    private Method minimum(){
        return new Method("minimum", getElementType(), new ArrayList<>());
    }

    private Method extractMin(){
        return new Method("extractMin", getElementType(), new ArrayList<>());
    }

    private Method decreaseKey(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(getElementType());
        parameters.add(new RealTypeDescriptor());
        return new Method("decreaseKey", new BooleanTypeDescriptor(), parameters);
    }

    @Override
    public String getTypeName() {
        return "MinQueue";
    }
}
