import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MicrosystemTest_1 {
    private Microsystem microsystem;

    @Before
    public void setUp() {
        this.microsystem = new MicrosystemImpl();

        Computer computer1 = new Computer(2, Brand.ACER, 1120, 15.6, "grey");
        Computer computer = new Computer(1, Brand.DELL, 2300, 15.6, "grey");
        Computer computer2 = new Computer(5, Brand.HP, 2400, 13.6, "red");

        this.microsystem.createComputer(computer);
        this.microsystem.createComputer(computer1);
        this.microsystem.createComputer(computer2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void remove_with_number_will_remove_all() {
        microsystem.remove(1);
        microsystem.removeWithBrand(Brand.DELL);
    }

    @Test
    public void remove_with_brand_will_remove_all() {
        Computer computer22 = new Computer(45, Brand.DELL, 2300, 15.6, "grey");
        microsystem.createComputer(computer22);
        microsystem.removeWithBrand(Brand.DELL);
        assertEquals(2, microsystem.count());
    }

    @Test
    public void upgradeRam(){
        microsystem.upgradeRam(128, 1);
        assertEquals(128, microsystem.getComputer(1).getRAM());

        int expectedRam = 0;

        Iterable<Computer> allFromBrand = this.microsystem.getAllFromBrand(Brand.DELL);

        for (Computer computerByBrand : allFromBrand) {
            expectedRam = computerByBrand.getRAM();
        }
        assertEquals(128, expectedRam);
    }

    @Test
    public void test_returnByRange(){
        List<Computer> inRangePrice = (List<Computer>) microsystem.getInRangePrice(1000, 2350);
    }

    @Test
    public void test_returnByColor(){
        Iterable<Computer> allWithColor = microsystem.getAllWithColor("grey");
    }

    @Test
    public void test_returnByScreenSize(){
        Iterable<Computer> allWithColor = microsystem.getAllWithScreenSize(15.6);
    }
}