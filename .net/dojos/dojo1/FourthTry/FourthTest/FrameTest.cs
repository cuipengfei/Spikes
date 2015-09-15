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
    }
}
