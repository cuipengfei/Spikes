using GildedRose;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class NormalItemTest : TestBase
    {
        [TestMethod]
        public void NormalItemsShouldDecreaseOneEachDay()
        {
            //given
            var program = GetProgram();

            //when one day pass
            PassNDays(program, 1);

            //then decrease one
            Assert.AreEqual(19, Vest(program).Quality);

            //when another 5 days pass
            PassNDays(program, 5);

            //then decrease five more
            Assert.AreEqual(14, Vest(program).Quality);
        }

        [TestMethod]
        public void NormalItemsShouldDecreaseTwoEachDayAfterSellInDays()
        {
            //given
            var program = Program.InitApp();

            //when 10 days pass
            PassNDays(program, 10);

            //then sell in days are over
            Assert.AreEqual(10, Vest(program).Quality);
            Assert.AreEqual(0, Vest(program).SellIn);

            //when pass another day
            PassNDays(program, 1);

            //then decrease 2
            Assert.AreEqual(Vest(program).Quality, 8);
        }

        [TestMethod]
        public void NormalItemsShouldNotDecreaseAnymoreAfterItsZero()
        {
            //given
            var program = Program.InitApp();

            //when 15 days pass
            PassNDays(program, 15);

            //then quality is zero
            Assert.AreEqual(0, Vest(program).Quality);

            //when another day pass
            PassNDays(program, 1);

            //then quality can not decrease below 0
            Assert.AreEqual(0, Vest(program).Quality);
        }
    }
}