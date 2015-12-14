package bowling.seven;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BowlingGameTest {
    @Test
    public void shouldCountTotalScoreOf10Frames() throws Exception {
        //given
        BowlingGame game = new BowlingGame();

        //when
        game.play(1, 3);
        game.play(1, 9);
        game.play(5, 5);
        game.play(1, 2);
        game.play(10, 0);
        game.play(1, 8);
        game.play(4, 3);
        game.play(3, 4);
        game.play(6, 2);
        game.play(1, 9, 3);
        //4 +15 +11 +3 +19 +9 +7 +7 +8 +13=96

        int total = game.countTotal();

        //then
        assertThat(total, is(96));
    }

    @Test
    public void shouldCountTotalScoreOfPerfectGame() throws Exception {
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

        int total = game.countTotal();

        //then
        assertThat(total, is(300));
    }
}
