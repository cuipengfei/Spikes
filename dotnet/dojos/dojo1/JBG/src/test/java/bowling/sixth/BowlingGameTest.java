package bowling.sixth;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BowlingGameTest {
    @Test
    public void shouldCountTotalScore() throws Exception {
        //given
        BowlingGame game = new BowlingGame();

        //when
        game.play(1, 2);
        game.play(3, 4);
        game.play(5, 5);
        game.play(1, 9);
        game.play(10, 0);
        game.play(10, 0);
        game.play(2, 3);
        game.play(0, 0);
        game.play(4, 2);
        game.play(3, 4, 1);
        //3 7 11 20 22 15 5 0 6 8 total: 97
        int total = game.countTotal();

        //then
        assertThat(total, is(97));
    }
}
