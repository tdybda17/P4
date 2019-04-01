package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;

public class MaxQueueTypeDescriptor extends PriorityQueueTypeDescriptor{
    private TypeDescriptor elementType;

    public MaxQueueTypeDescriptor(TypeDescriptor elementType) {
        super(elementType);
        this.elementType = elementType;
        this.addMethods();
    }

    private void addMethods(){
        addMethod(maximum());
        addMethod(extractMax());
        addMethod(decreaseKey());
    }

    private Method maximum(){
        return new Method("maximum", elementType, new ArrayList<>());
    }

    private Method extractMax(){
        return new Method("extractMax", elementType, new ArrayList<>());
    }

    private Method decreaseKey(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);
        parameters.add(new IntegerTypeDescriptor());
        return new Method("decreaseKey", new BooleanTypeDescriptor(), parameters);
    }

    @Override
    public String getTypeName() {
        return "MaxQueue";
    }
}
