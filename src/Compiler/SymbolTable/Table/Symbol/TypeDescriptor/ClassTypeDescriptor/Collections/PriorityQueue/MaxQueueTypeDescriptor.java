package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.RealTypeDescriptor;
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
        addMethod(increaseKey());
    }

    private Method maximum(){
        return new Method("maximum", elementType, new ArrayList<>());
    }

    private Method extractMax(){
        return new Method("extractMax", elementType, new ArrayList<>());
    }

    private Method increaseKey(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);
        parameters.add(new RealTypeDescriptor());
        return new Method("increaseKey", new BooleanTypeDescriptor(), parameters);
    }

    @Override
    public String getTypeName() {
        return "MaxQueue";
    }
}
