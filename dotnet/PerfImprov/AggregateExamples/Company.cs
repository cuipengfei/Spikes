namespace AggregateExamples
{
    internal class Company
    {
        public decimal EvaluatedMarketValue { get; set; }

        public Company Merge(Company that)
        {
            RedTape(); //繁文缛节
            EvaluatedMarketValue += that.EvaluatedMarketValue;
            return this;
        }

        private void RedTape()
        {
            var justToWasteTime = EvaluatedMarketValue/100;
            justToWasteTime *= 100;
            justToWasteTime += 100;
            justToWasteTime -= 100;
        }
    }
}