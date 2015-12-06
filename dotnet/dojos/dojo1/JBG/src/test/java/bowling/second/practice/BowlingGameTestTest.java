package bowling.second.practice;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BowlingGameTestTest {
    @Test
    public void shouldCountScoreOf10Frames() throws Exception {
        //given
        BowlingGame game = new BowlingGame();

        //when
        game.play(2, 3); //5
        game.play(2, 8); //12
        game.play(2, 7); //9
        game.play(3, 6); //9
        game.play(10, 0); //20
        game.play(5, 5); //20
        game.play(10, 0); //30
        game.play(10, 0); //22
        game.play(10, 0); //20
        game.play(2, 8, 5); //15

        int total = game.totalScore();
        //then
        assertThat(total, is(162));
    }

    @Test
    public void shouldCountScoreOfPerfectGame() throws Exception {
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

        int total = game.totalScore();
        //then
        assertThat(total, is(300));
    }
}
