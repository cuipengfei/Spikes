package bowling.first.practice;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BowlingGameTestTest {
    @Test
    public void shouldSumUpScoreOfAllFrames() throws Exception {
        BowlingGame game = new BowlingGame();
        game.playOneFrame(1, 2);//3
        game.playOneFrame(1, 9);//11
        game.playOneFrame(1, 2);//3
        game.playOneFrame(10, 0);//23
        game.playOneFrame(10, 0);//15
        game.playOneFrame(3, 2);//5
        game.playOneFrame(3, 7);//11
        game.playOneFrame(1, 2);//3
        game.playOneFrame(10, 0);//13
        game.playOneFrame(1, 2);//3

        assertThat(game.totalScore(), is(90));
    }

    @Test
    public void shouldSumUpScoreOfPerfectGame() throws Exception {
        BowlingGame game = new BowlingGame();
        game.playOneFrame(10, 0);
        game.playOneFrame(10, 0);
        game.playOneFrame(10, 0);
        game.playOneFrame(10, 0);
        game.playOneFrame(10, 0);
        game.playOneFrame(10, 0);
        game.playOneFrame(10, 0);
        game.playOneFrame(10, 0);
        game.playOneFrame(10, 0);
        game.playOneFrame(10, 10, 10);

        assertThat(game.totalScore(), is(300));
    }
}
