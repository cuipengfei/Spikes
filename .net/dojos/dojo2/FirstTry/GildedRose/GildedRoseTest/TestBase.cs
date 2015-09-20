using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using GildedRose;

namespace GildedRoseTest
{
    public class TestBase
    {
        protected static void PassNDays(Program program,int n)
        {
            for (int i = 0; i < n; i++)
            {
                program.UpdateQuality();
            }
        }

        protected Item Vest(Program program)
        {
            return program.Items.FirstOrDefault(i=>i.Name== "+5 Dexterity Vest");
        }

        protected static Program GetProgram()
        {
            Program program = Program.InitApp();
            return program;
        }

        protected Item AgedBrie(Program program)
        {
            return program.Items.FirstOrDefault(i => i.Name == "Aged Brie");
        }
    }
}
