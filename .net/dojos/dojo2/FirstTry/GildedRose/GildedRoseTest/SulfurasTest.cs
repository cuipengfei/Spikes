using System;
using System.Linq;
using GildedRose;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class SulfurasTest: TestBase
    {
        [TestMethod]
        public void SulfurasShouldNeverChange()
        {
            //given
            var program = GetProgram();

            //when pass 2 days
            PassNDays(program,2);

            //then not change
            Assert.AreEqual(80,Sulfras(program).Quality);

            //when pass whatever days for 10 times
            for (int i = 0; i < 10; i++)
            {
                Random random=new Random();
                int days=random.Next(1, 20);
                PassNDays(program,days);
                //then never change
                Assert.AreEqual(80,Sulfras(program).Quality);
            }
        }
    }
}
