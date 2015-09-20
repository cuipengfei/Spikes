using System;
using System.Linq;
using GildedRose;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class BackStagePassTest:TestBase
    {
        [TestMethod]
        public void BackStagePassShouldIncreaseByOneBefore10DaysSellIn()
        {
            //given
            var program = GetProgram();

            //when pass 5 days
            PassNDays(program,5);

            //then increase one each day
            Assert.AreEqual(25,BackStagePass(program).Quality);
            Assert.AreEqual(10,BackStagePass(program).SellIn);
        }

        [TestMethod]
        public void BackStagePassShouldIncreaseBy2After10DaysSellIn()
        {
            //given
            var program = GetProgram();

            //when pass 5 days
            PassNDays(program,5);

            //then 10 days left
            Assert.AreEqual(10,BackStagePass(program).SellIn);

            //when another 5 days
            PassNDays(program,5);

            //then 10 more quality
            Assert.AreEqual(35,BackStagePass(program).Quality);
        }

        [TestMethod]
        public void BackStagePassShouldIncreaseBy3After5DaysSellIn()
        {
            //given
            var program = GetProgram();

            //when pass 10 days
            PassNDays(program,10);

            //then 5 days left
            Assert.AreEqual(5,BackStagePass(program).SellIn);

            //when pass another 5 days
            PassNDays(program,5);

            //then quality is 50
            Assert.AreEqual(50,BackStagePass(program).Quality);
        }


        [TestMethod]
        public void BackStagePassShouldDropToZeroAfterSellInDaysZeroLeft()
        {
            //given
            var program = GetProgram();

            //when pass 15 days
            PassNDays(program,15);

            //then 0 days left
            Assert.AreEqual(0,BackStagePass(program).SellIn);
            Assert.AreEqual(50, BackStagePass(program).Quality);

            //when pass another 1 day
            PassNDays(program,1);

            //then quality is 50
            Assert.AreEqual(0, BackStagePass(program).Quality);
        }
    }
}
