using Bowling;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BowlingTest
{
    [TestClass]
    public class LastFrameTest
    {
        [TestMethod]
        public void LastFrameShouldJustAddThreeTries()
        {
            //given
            var lastFrame = new LastFrame {FirstTry = 2, SecondTry = 4, ThirdTry = 6};

            //when
            var score = lastFrame.Score;

            //then
            Assert.AreEqual(12, score);
        }
    }
}