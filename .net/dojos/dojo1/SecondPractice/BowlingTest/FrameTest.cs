using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using BowlingSecondPractice;

namespace BowlingTest
{
    [TestClass]
    public class FrameTest
    {
        [TestMethod]
        public void FrameShouldCountScoreOfItsOwn()
        {
            //given
            var frame=new Frame(3,2);

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(5,score);
        }

        [TestMethod]
        public void FrameShouldCountBonusOfNextBallWhenThereIsSpare()
        {
            //given
            var frame=new Frame(3,7);
            var nextFrame=new Frame(3,3);
            frame.NextFrame = nextFrame;

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(13,score);
        }
    }
}
