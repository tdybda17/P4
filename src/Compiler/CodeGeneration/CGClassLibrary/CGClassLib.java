package Compiler.CodeGeneration.CGClassLibrary;

import Compiler.CodeGeneration.CGClassLibrary.Classes.*;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

import java.util.ArrayList;
import java.util.List;

public class CGClassLib {

    private List<ClassFile> library;

    public CGClassLib() {
        this.library = new ArrayList<>();
        this.library.add(new CollectionInterfaceFile());
        this.library.add(new ColorEnumFile());
        this.library.add(new DiGraphClassFile());
        this.library.add(new DirectedEdgeClassFile());
        this.library.add(new EdgeAbstractClassFile());
        this.library.add(new EdgeMaxComparatorClassFile());
        this.library.add(new EdgeMinComparatorClassFile());
        this.library.add(new GraphClassFile());
        this.library.add(new MainClassFile());
        this.library.add(new MaxQueueClassFile());
        this.library.add(new MinQueueClassFile());
        this.library.add(new QueueClassFile());
        this.library.add(new UndirectedEdgeClassFile());
        this.library.add(new VertexClassFile());
        this.library.add(new VertexMaxComparatorClassFile());
        this.library.add(new VertexMinComparatorClassFile());
    }

    public IJavaFileBuilder getBuilder(String identifier) {
        for (ClassFile file : library) {
            if(file.getClassName().equals(identifier))
                return file.getBuilder();
        }
        throw new NoSuchClassException("No class with identifier: " + identifier);
    }

    public List<ClassFile> getLibrary() {
        return library;
    }
}
