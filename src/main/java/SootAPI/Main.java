package SootAPI;

public class Main {
    public static void main(String[] args){
        Package analysedPackage = Util.loadPackage("src/test/sources");

        for (Class c : analysedPackage.getClasses()){
            System.out.println(c.getName());
        }


    }

}