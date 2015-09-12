namespace Bowling
{
    public class Frame
    {
        public int FirstTry { get; set; }
        public int SecondTry { get; set; }

        public int Score
        {
            get { return CalculateScore(); }
        }


        public Frame Next { get; set; }

        private int CalculateScore()
        {
            int score= FirstTry + SecondTry;
            if (FirstTry == 10)
            {
                score += (Next.FirstTry+Next.SecondTry);
            }
            else if(FirstTry+SecondTry==10)
            {
                score += Next.FirstTry;
            }
            return score;
        }
    }
}