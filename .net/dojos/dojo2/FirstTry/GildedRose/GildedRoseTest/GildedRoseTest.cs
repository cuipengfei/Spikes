using System;
using System.Linq;
using GildedRose;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class GildedRoseTest
    {
        [TestMethod]
        public void NormalItemsShouldDecreaseOneEachDay()
        {
            //given
            Program program=Program.InitApp();

            //when one day pass
            PassNDays(program,1);

            //then decrease one
            Assert.AreEqual(Vest(program).Quality,19);

            //when another 5 days pass
            PassNDays(program,5);

            //then decrease five more
            Assert.AreEqual(Vest(program).Quality,14);
        }

        [TestMethod]
        public void NormalItemsShouldDecreaseTwoEachDayAfterSellInDays()
        {
            //given
            Program program=Program.InitApp();

            //when 10 days pass
            PassNDays(program,10);

            //then sell in days are over
            Assert.AreEqual(Vest(program).Quality,10);
            Assert.AreEqual(Vest(program).SellIn,0);

            //when pass another day
            PassNDays(program,1);

            //then decrease 2
            Assert.AreEqual(Vest(program).Quality,8);
        }

        [TestMethod]
        public void NormalItemsShouldNotDecreaseAnymoreAfterItsZero()
        {
            //given
            Program program=Program.InitApp();

            //when 15 days pass
            PassNDays(program,15);

            //then quality is zero
            Assert.AreEqual(Vest(program).Quality,0);

            //when another day pass
            PassNDays(program,1);

            //then quality can not decrease below 0
            Assert.AreEqual(Vest(program).Quality,0);
        }

        private static void PassNDays(Program program,int n)
        {
            for (int i = 0; i < n; i++)
            {
                program.UpdateQuality();
            }
        }

        private Item Vest(Program program)
        {
            return program.Items.FirstOrDefault(i=>i.Name== "+5 Dexterity Vest");
        }
    }
}
