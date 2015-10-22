namespace AggregateExamples
{
    internal class Company
    {
        public decimal EvaluatedMarketValue { get; set; }

        public Company Merge(Company that)
        {
            var justToWasteTime = EvaluatedMarketValue/100;
            justToWasteTime *= 100;
            justToWasteTime += 100;
            justToWasteTime -= 100;

            EvaluatedMarketValue += that.EvaluatedMarketValue;
            return this;
        }
    }
}