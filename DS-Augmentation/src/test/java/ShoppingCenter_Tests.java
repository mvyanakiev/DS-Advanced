import org.junit.Assert;
import org.junit.Test;

public class ShoppingCenter_Tests {
    ShoppingCentre shoppingCentre = new ShoppingCentre();
    StringBuilder builder = new StringBuilder();

    @Test
    public void zeroTest() {


        String command = "AddProduct";
        String[] params = new String[]{"name", "320", "producer"};
        Main.executeCommand(shoppingCentre, builder, command, params);

        command = "AddProduct";
        params = new String[]{"name1", "3201", "producer1"};
        Main.executeCommand(shoppingCentre, builder, command, params);

        command = "FindProductsByProducer";
        params = new String[]{"producer"};
        Main.executeCommand(shoppingCentre, builder, command, params);

//        command="FindProductsByProducer";
//        params = new String[]{"producer"};
//        Main.executeCommand(shoppingCentre, builder, command, params);


        String actual = builder.toString();
        String expected = "Product added\n" +
                "Product added\n" +
                "{name;producer;320.00}";


        Assert.assertEquals(expected, actual);


    }


}
