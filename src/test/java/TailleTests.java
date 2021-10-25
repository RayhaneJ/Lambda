import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.List.of;

public class TailleTests {
    private Paire<Integer, Double> paire1
            = new Paire<Integer, Double>(180, 70.0);;

    private Paire<Integer, Double> paire2
            = new Paire<Integer, Double>(180, 70.0);;

    @Test
    public void taillePetitTest(){
        Predicate<Integer> p = x -> x < 100;
        Boolean res = p.test(paire1.fst);
        Assert.assertFalse(res);
    }

    @Test
    public void tailleGrandeTest(){
        Predicate<Integer> p = x -> x > 200;
        Boolean res = p.test(paire1.fst);
        Assert.assertFalse(res);
    }

    @Test
    public void tailleIncorrecteTest(){
        Predicate<Integer> p1 = x -> x < 100;
        Predicate<Integer> p2 = x -> x > 200;
        Predicate<Integer> p3 = p1.or(p2);
        Boolean res = p3.test(paire1.fst);
        Assert.assertFalse(res);
    }

    @Test
    public void tailleCorrecteTest(){
        Predicate<Integer> p1 = x -> x < 100;
        Predicate<Integer> p2 = x -> x > 200;
        Predicate<Integer> p3 = p1.or(p2);
        Predicate<Integer> p4 = p3.negate();
        Boolean res = p4.test(paire1.fst);
        Assert.assertTrue(res);
    }

    @Test
    public void poidsLourdTest(){
        Predicate<Double> p1 = x -> x > 150.0;
        Boolean res = p1.test(paire1.snd);
        Assert.assertFalse(res);
    }

    @Test
    public void poidsCorrectTest(){
        Predicate<Double> p1 = x -> x > 150.0;
        Predicate<Double> p2 = p1.negate();
        Boolean res = p2.test(paire1.snd);
        Assert.assertTrue(res);
    }

    @Test
    public void accesAutoriseTest(){
        Predicate<Integer> p1 = x -> x < 100;
        Predicate<Integer> p2 = x -> x > 200;
        Predicate<Integer> p3 = p1.or(p2);
        Predicate<Integer> p4 = p3.negate();

        Predicate<Double> p5 = x -> x > 150.0;
        Predicate<Double> p6 = p5.negate();

        Boolean res1 = p4.test(paire1.fst);
        Boolean res2 = p6.test(paire1.snd);
        Assert.assertTrue(res1);
        Assert.assertTrue(res2);
    }

    @Test
    public void filtragePedricatTest(){
        Predicate<Integer> p1 = x -> x < 100;
        Predicate<Integer> p2 = x -> x > 200;
        Predicate<Integer> p3 = p1.or(p2);

        Predicate<Double> p4 = x -> x > 150.0;
        Predicate<Double> p5 = p4.negate();

        filtragePredicat(p3, new ArrayList<>(List.of(paire1.fst, paire2.fst)));
    }

    private <T> List<T> filtragePredicat(Predicate<T> p, ArrayList<T> t){
        ArrayList<T> list = new ArrayList<>();
        t.forEach(x -> { if(p.test(x)) { list.add(x); }});

        return list;
    }
}
