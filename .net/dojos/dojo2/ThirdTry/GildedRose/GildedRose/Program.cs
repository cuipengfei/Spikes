using System;
using System.Collections.Generic;

namespace GildedRose
{
    public class Program
    {
        public IList<Item> Items;

        private static void Main(string[] args)
        {
            Console.WriteLine("OMGHAI!");

            var app = InitApp();

            app.UpdateQuality();

            Console.ReadKey();
        }

        public static Program InitApp()
        {
            var app = new Program
            {
                Items = new List<Item>
                {
                    new Item {Name = "+5 Dexterity Vest", SellIn = 10, Quality = 20},
                    new Item {Name = "Aged Brie", SellIn = 2, Quality = 0},
                    new Item {Name = "Elixir of the Mongoose", SellIn = 5, Quality = 7},
                    new Item {Name = "Sulfuras, Hand of Ragnaros", SellIn = 0, Quality = 80},
                    new Item
                    {
                        Name = "Backstage passes to a TAFKAL80ETC concert",
                        SellIn = 15,
                        Quality = 20
                    },
                    new Item {Name = "Conjured Mana Cake", SellIn = 3, Quality = 6}
                }
            };
            return app;
        }

        public void UpdateQuality()
        {
            foreach (Item item in Items)
            {
                if (!IsValueAdding(item) && !IsTimeLimited(item))
                {
                    if (item.Quality > 0)
                    {
                        if (!IsLegendary(item))
                        {
                            DecreaseOneQuality(item);
                        }
                    }
                }
                else
                {
                    if (item.Quality < 50)
                    {
                        IncreaseOneQuality(item);

                        if (IsTimeLimited(item))
                        {
                            if (item.SellIn < 11)
                            {
                                if (item.Quality < 50)
                                {
                                    IncreaseOneQuality(item);
                                }
                            }

                            if (item.SellIn < 6)
                            {
                                if (item.Quality < 50)
                                {
                                    IncreaseOneQuality(item);
                                }
                            }
                        }
                    }
                }

                if (!IsLegendary(item))
                {
                    DecreaseOneDay(item);
                }

                if (item.SellIn < 0)
                {
                    if (!IsValueAdding(item))
                    {
                        if (!IsTimeLimited(item))
                        {
                            if (item.Quality > 0)
                            {
                                if (!IsLegendary(item))
                                {
                                    DecreaseOneQuality(item);
                                }
                            }
                        }
                        else
                        {
                            ClearQuality(item);
                        }
                    }
                    else
                    {
                        if (item.Quality < 50)
                        {
                            IncreaseOneQuality(item);
                        }
                    }
                }
            }
        }

        private static bool IsLegendary(Item item)
        {
            return item.Name == "Sulfuras, Hand of Ragnaros";
        }

        private static bool IsTimeLimited(Item item)
        {
            return item.Name == "Backstage passes to a TAFKAL80ETC concert";
        }

        private static bool IsValueAdding(Item item)
        {
            return item.Name == "Aged Brie";
        }

        private static void ClearQuality(Item item)
        {
            item.Quality = item.Quality - item.Quality;
        }

        private static void DecreaseOneDay(Item item)
        {
            item.SellIn = item.SellIn - 1;
        }

        private static void IncreaseOneQuality(Item item)
        {
            item.Quality = item.Quality + 1;
        }

        private static void DecreaseOneQuality(Item item)
        {
            item.Quality = item.Quality - 1;
        }
    }

    public class Item
    {
        public string Name { get; set; }

        public int SellIn { get; set; }

        public int Quality { get; set; }
    }
}