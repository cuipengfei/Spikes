using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ThirdTry;

namespace ThirdTryTest
{
    [TestClass]
    public class FrameTest
    {
        [TestMethod]
        public void FrameShouldCountItsOwnScoreWithoutBonus()
        {
            //given
            var frame = new Frame(1,2);

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(3,score);
        }

        [TestMethod]
        public void FrameShouldCountItsScoreWithNextBallAsBonusWhenThereIsSpare()
        {
            //given
            var frame = new Frame(4,6);
            var nextFrame=new Frame(2,3);
            frame.Next = nextFrame;

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(12,score);
        }

        [TestMethod]
        public void FrameShouldCountItsScoreWithNext2BallsAsBonusWhenThereIsStrike()
        {
            //given
            var frame = new Frame(10,0);
            var nextFrame=new Frame(2,3);
            frame.Next = nextFrame;

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(15,score);
        }

        [TestMethod]
        public void FrameShouldCountItsScoreWithNext2BallsAsBonusWhenNextFrameIsStrike()
        {
            //given
            var frame = new Frame(10,0);
            var nextFrame=new Frame(10,0);
            var nextFrame2=new Frame(2,3);
            frame.Next = nextFrame;
            nextFrame.Next = nextFrame2;

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(22,score);
        }

        [TestMethod]
        public void FrameShouldCountItsScoreWithNext2BallsAsBonusWhenNextFrameIsLast()
        {
            //given
            var frame = new Frame(10,0);
            var nextFrame=new LastFrame(10,3,4);
            frame.Next = nextFrame;

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(23,score);
        }
    }
}
