package bowling.fourth;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BowlingGameTest {
    @Test
    public void shouldCountTotalScore() throws Exception {
        //given
        BowlingGame game = new BowlingGame();

        //when
        game.play(1, 2);
        game.play(10, 0);
        game.play(1, 2);
        game.play(1, 9);
        game.play(1, 2);
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 0);
        game.play(1, 2);
        game.play(1, 9, 3);

        //3 + 13 + 3 + 11 + 3 + 30 + 21 + 13 + 3 + 13
        int total = game.countTotalScore();
        //then
        assertThat(total, is(113));
    }

    @Test
    public void shouldCountPerfectScore() throws Exception {
        //given
        BowlingGame game = new BowlingGame();

        //when
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 10, 10);

        //30 * 10 = 300
        int total = game.countTotalScore();
        //then
        assertThat(total, is(300));
    }
}
