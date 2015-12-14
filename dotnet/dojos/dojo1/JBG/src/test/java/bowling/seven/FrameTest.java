package bowling.seven;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FrameTest {
    @Test
    public void shouldCountScoreWithoutBonus() throws Exception {
        //given
        Frame frame = new Frame(3, 4);

        //when
        int score = frame.countScore();

        //when
        assertThat(score, is(7));
    }

    @Test
    public void shouldCountScoreWithSpareBonus() throws Exception {
        //given
        Frame spareFrame = new Frame(3, 7);
        Frame nextFrame = new Frame(1, 2);
        spareFrame.setNext(nextFrame);

        //when
        int score = spareFrame.countScore();

        //when
        assertThat(score, is(11));
    }

    @Test
    public void shouldCountScoreWithStrikeBonus() throws Exception {
        //given
        Frame strikeFrame = new Frame(10, 0);
        Frame nextFrame = new Frame(1, 2);
        strikeFrame.setNext(nextFrame);

        //when
        int score = strikeFrame.countScore();

        //when
        assertThat(score, is(13));
    }

    @Test
    public void shouldCountScoreWith2StrikesBonus() throws Exception {
        //given
        Frame firstStrikeFrame = new Frame(10, 0);
        Frame secondStrikeFrame = new Frame(10, 0);
        Frame thirdFrame = new Frame(1, 2);
        firstStrikeFrame.setNext(secondStrikeFrame);
        secondStrikeFrame.setNext(thirdFrame);

        //when
        int score = firstStrikeFrame.countScore();

        //when
        assertThat(score, is(21));
    }
}
