using System.Collections.Generic;
using System.Linq;
using GildedRose;

internal static class UpdatersWarehouse
{
    private static readonly List<ItemUpdater> _updaters = new List<ItemUpdater>
    {
        new ConjuredItemUpdater(),
        new NormalItemUpdater(),
        new TimeLimitedItemUpdater(),
        new ValueAddingItemUpdater()
    };

    internal static ItemUpdater GetUpdater(Item item)
    {
        return _updaters.FirstOrDefault(updater => updater.CanUpdate(item));
    }
}