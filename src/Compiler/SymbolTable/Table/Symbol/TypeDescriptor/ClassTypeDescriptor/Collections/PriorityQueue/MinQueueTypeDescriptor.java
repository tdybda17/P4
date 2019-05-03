package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
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

    @Override
    public void setElementType(TypeDescriptor elementType) {
        super.setElementType(elementType);
        this.addMethods();
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
