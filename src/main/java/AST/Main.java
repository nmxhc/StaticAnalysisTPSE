package AST;

public class Main {
    public static void main(String[] args){
        Package analysedPackage = Util.loadPackage("src/test/sources");

        for (ClassDeclaration c : analysedPackage.getClasses()){
            System.out.println(c.getName());
        }


    }

}