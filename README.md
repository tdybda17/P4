# EduGraph Compiler

In order to run the compiler, run the main method in "src/Compiler/Parser/GeneratedFiles/TestParser.java". 

This will compile the EduGraph program located at "src/Compiler/Parser/test".


When compilation has completed successfully, a file will be created at "src/GeneratedCode.java" containing all the generated Java code.

In this file, a Main class should appear. In this Main class, a main method is located.


If the EduGraph program uses the print() function on a graph, a program called dot is needed in order to run the generated Java main method.

The program dot can be acquired by downloading and installing GraphViz from the following website:

For Windows: https://graphviz.gitlab.io/_pages/Download/Download_windows.html

For Mac (not as straightforward but explained under the Mac section on this site): https://graphviz.gitlab.io/download/


When dot has been installed, an environment variable must be specified for dot. 
This variable should be added to the Path variable and should look somewhat like this: "C:\Program Files (x86)\Graphviz2.38\bin"


Now, when running the generated main method, any prints on a graph should result in a PNG file located in folder: 
"src\Compiler\CodeGeneration\PngFileGenerator\PngFiles"
