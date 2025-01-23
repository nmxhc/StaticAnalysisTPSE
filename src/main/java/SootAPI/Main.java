package SootAPI;

public class Main {
    public static void main(String[] args){
        AnalysedPackage analysedPackage = Util.loadPackage("src/test/sources");

        for (AnalysedClass c : analysedPackage.getClasses()){
            System.out.println(c.getName());
        }
    }
}