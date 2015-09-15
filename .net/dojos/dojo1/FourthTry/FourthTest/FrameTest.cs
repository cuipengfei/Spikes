using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using FourthTry;

namespace FourthTest
{
    [TestClass]
    public class FrameTest
    {
        [TestMethod]
        public void FrameShouldCountScoreWhenThereIsNoBonus()
        {
            //given
            var frame = new Frame(2,3);

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(5,score);
        }

        [TestMethod]
        public void FrameShouldIncludeNextOneBallAsBonusWhenThereIsSpare()
        {
            //given
            var frame = new Frame(2,8);
            var frame2=new Frame(3,4);
            frame.Next = frame2;

            //when
            int score=frame.Score;

            //then
            Assert.AreEqual(13,score);
        }
    }
}
