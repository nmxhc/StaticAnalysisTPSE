package SootAPI;

public class Main {
    public static void main(String[] args){
        AnalysedPackage analysedPackage = Util.loadPackage("basicTests");
        System.out.println("error:");

        for (AnalysedClass c : analysedPackage.classes){
            System.out.println(c.name);
        }
    }
}
