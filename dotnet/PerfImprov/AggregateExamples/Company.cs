namespace AggregateExamples
{
    internal class Company
    {
        public decimal EvaluatedMarketValue { get; set; }

        public Company Merge(Company that)
        {
            EvaluatedMarketValue += that.EvaluatedMarketValue;
            return this;
        }
    }
}