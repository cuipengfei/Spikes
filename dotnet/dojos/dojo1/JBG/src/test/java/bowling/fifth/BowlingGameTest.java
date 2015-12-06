package bowling.fifth;

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
        game.play(1, 9);
        game.play(1, 2);
        game.play(10, 0);
        game.play(1, 2);
        game.play(10, 0);
        game.play(10, 0);
        game.play(10, 0);
        game.play(1, 2);
        game.play(1, 9, 2);
        int total = game.countTotalScore();
        //3+11+3+13+3+30+21+13+3+12

        //then
        assertThat(total, is(112));
    }

    @Test
    public void shouldCountTotalScoreOfAPerfectGame() throws Exception {
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
        int total = game.countTotalScore();
        //30 * 10 = 300

        //then
        assertThat(total, is(300));
    }
}
