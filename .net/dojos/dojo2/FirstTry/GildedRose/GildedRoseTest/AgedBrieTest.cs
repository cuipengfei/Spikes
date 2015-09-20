using System;
using System.Linq;
using GildedRose;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class AgedBrieTest:TestBase
    {
        [TestMethod]
        public void AgedBrieShouldIncreaseWhenSellInDaysPass()
        {
            //given
            var program = GetProgram();

            //when pass 2 days
            PassNDays(program,2);

            //then increase 2
            Assert.AreEqual(AgedBrie(program).Quality,2);
            Assert.AreEqual(AgedBrie(program).SellIn,0);

            //when pass 5 more days
            PassNDays(program,5);

            //then increase 5 more
            Assert.AreEqual(AgedBrie(program).Quality,12);
        }

        [TestMethod]
        public void AgedBrieShouldNotIncreaseToMoreThan50()
        {
            //given
            var program = GetProgram();

            //when pass 26 days
            PassNDays(program,26);

            //then increase 50
            Assert.AreEqual(AgedBrie(program).Quality,50);

            //when pass 5 more days
            PassNDays(program,5);

            //then increase no more
            Assert.AreEqual(AgedBrie(program).Quality,50);
        }
    }
}
