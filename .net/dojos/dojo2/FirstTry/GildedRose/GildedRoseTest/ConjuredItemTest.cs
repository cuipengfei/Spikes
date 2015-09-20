using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace GildedRoseTest
{
    [TestClass]
    public class ConjuredItemTest : TestBase
    {
        [TestMethod]
        public void ConjuredItemShouldDecreaseAsNormalItem()
        {
            //given
            var program = GetProgram();

            //when pass 1 days
            PassNDays(program, 1);

            //then decrease 2
            Assert.AreEqual(5, ManaCake(program).Quality);

            //when pass 5 more days
            PassNDays(program, 5);

            //then decrease to 0
            Assert.AreEqual(0, ManaCake(program).Quality);

            //when pass one more day
            PassNDays(program, 1);

            //then not less than 0
            Assert.AreEqual(0, ManaCake(program).Quality);
        }
    }
}