using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using BowlingSecondPractice;

namespace BowlingTest
{
    [TestClass]
    public class LastFrameTest
    {
        [TestMethod]
        public void LastFrameShouldSimplyAddThreeBallsAsScore()
        {
            //given
            var lastFrame = new LastFrame(1,2,3);

            //when
            int score=lastFrame.Score;

            //then
            Assert.AreEqual(6,score);
        }
    }
}
