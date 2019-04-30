package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;

public class MaxQueueTypeDescriptor extends PriorityQueueTypeDescriptor{
    public MaxQueueTypeDescriptor(TypeDescriptor elementType) {
        super(elementType);
        this.addMethods();
    }

    private void addMethods(){
        addMethod(maximum());
        addMethod(extractMax());
        addMethod(increaseKey());
    }

    private Method maximum(){
        return new Method("maximum", getElementType(), new ArrayList<>());
    }

    private Method extractMax(){
        return new Method("extractMax", getElementType(), new ArrayList<>());
    }

    private Method increaseKey(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(getElementType());
        parameters.add(new RealTypeDescriptor());
        return new Method("increaseKey", new BooleanTypeDescriptor(), parameters);
    }

    @Override
    public String getTypeName() {
        return "MaxQueue";
    }
}
