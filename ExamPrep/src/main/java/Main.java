public class Main {
    public static void main(String[] args) {

        Microsystem microsystem = new MicrosystemImpl();

        Computer computer1 = new Computer(2, Brand.ACER, 1120, 15.6, "grey");
        Computer computer11 = new Computer(33, Brand.ACER, 900, 15.6, "grey");
        Computer computer22 = new Computer(21, Brand.ACER, 900, 15.6, "grey");
        Computer computer = new Computer(1, Brand.DELL, 2300, 15.6, "grey");
        Computer computer2 = new Computer(5, Brand.HP, 2400, 13.6, "red");

        microsystem.createComputer(computer);
        microsystem.createComputer(computer2);

        microsystem.createComputer(computer11);
        microsystem.createComputer(computer22);
        microsystem.createComputer(computer1);

        microsystem.upgradeRam(16, 1);

        Iterable<Computer> allFromBrand = microsystem.getAllFromBrand(Brand.ACER);
        System.out.println("hoi");




    }
}
